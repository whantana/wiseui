/**
 * Copyright (C) 2011 Universität zu Lübeck, Institut für Telematik (ITM),
 *                             Research Academic Computer Technology Institute (RACTI)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.wisebed.wiseui.client.testbedlist.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import eu.wisebed.wiseui.api.SNAAServiceAsync;
import eu.wisebed.wiseui.client.testbedlist.event.LoggedInEvent;
import eu.wisebed.wiseui.client.testbedlist.event.RefreshTestbedListEvent;
import eu.wisebed.wiseui.client.testbedlist.event.ShowLoginDialogEvent;
import eu.wisebed.wiseui.client.testbedlist.event.TestbedSelectedEvent;
import eu.wisebed.wiseui.client.testbedlist.event.ShowLoginDialogEvent.ShowLoginDialogHandler;
import eu.wisebed.wiseui.client.testbedlist.event.TestbedSelectedEvent.ConfigurationSelectedHandler;
import eu.wisebed.wiseui.client.testbedlist.util.AuthenticationHelper;
import eu.wisebed.wiseui.client.testbedlist.util.AuthenticationHelper.Callback;
import eu.wisebed.wiseui.client.testbedlist.view.LoginDialogView;
import eu.wisebed.wiseui.client.testbedlist.view.LoginDialogView.Presenter;
import eu.wisebed.wiseui.client.testbedselection.common.UrnPrefixInfo;
import eu.wisebed.wiseui.client.testbedselection.common.UrnPrefixInfo.State;
import eu.wisebed.wiseui.client.util.AuthenticationManager;
import eu.wisebed.wiseui.shared.dto.SecretAuthenticationKey;
import eu.wisebed.wiseui.shared.dto.TestbedConfiguration;

/**
 * The presenter for the {@link LoginDialogView}.
 *
 * @author Malte Legenhausen
 */
public class LoginDialogPresenter implements Presenter, ConfigurationSelectedHandler, ShowLoginDialogHandler {

    private final EventBus eventBus;

    private final LoginDialogView view;

    private final SNAAServiceAsync authenticationService;

    private final AuthenticationManager authenticationManager;

    private final ListDataProvider<UrnPrefixInfo> dataProvider = new ListDataProvider<UrnPrefixInfo>();

    private TestbedConfiguration configuration;

    private AuthenticationHelper authenticationHelper;

    @Inject
    public LoginDialogPresenter(final EventBus eventBus,
                                final LoginDialogView view,
                                final SNAAServiceAsync authenticationService,
                                final AuthenticationManager authenticationManager) {
        this.eventBus = eventBus;
        this.view = view;
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;

        dataProvider.addDataDisplay(view.getUrnPrefixList());
        bind();
    }

    public void bind() {
        eventBus.addHandler(TestbedSelectedEvent.TYPE, this);
        eventBus.addHandler(ShowLoginDialogEvent.TYPE, this);
    }

    @Override
    public void submit() {
        view.getUsernameEnabled().setEnabled(false);
        view.getPasswordEnabled().setEnabled(false);
        view.getSubmitEnabled().setEnabled(false);

        final String endpointUrl = configuration.getSnaaEndpointUrl();
        final String username = view.getUsernameText().getText();
        final String password = view.getPasswordText().getText();

        authenticationHelper.authenticate(dataProvider.getList(), endpointUrl, username, password, new Callback() {

            private boolean hideAfterComplete = true;

            @Override
            public void onStateChanged(final UrnPrefixInfo info, final State state) {
                dataProvider.refresh();
                if (state.equals(State.FAILED) || state.equals(State.SKIPPED)) {
                    hideAfterComplete = false;
                }
            }

            @Override
            public void onSuccess(final SecretAuthenticationKey result) {
                authenticationManager.addSecretAuthenticationKey(result);
                eventBus.fireEventFromSource(new LoggedInEvent(result), this);
                eventBus.fireEventFromSource(new RefreshTestbedListEvent(), this);
            }

            @Override
            public void onFinish() {
                view.getUsernameEnabled().setEnabled(true);
                view.getPasswordEnabled().setEnabled(true);
                view.getSubmitEnabled().setEnabled(true);
                if (hideAfterComplete) {
                    view.hide();
                }
            }
        });
    }

    @Override
    public void cancel() {
        //authenticationProvider.cancel();
        view.hide();
    }

    @Override
    public void onTestbedSelected(final TestbedSelectedEvent event) {
        configuration = event.getConfiguration();
        dataProvider.getList().clear();
        for (String urnPrefix : configuration.getUrnPrefixList()) {
            dataProvider.getList().add(new UrnPrefixInfo(urnPrefix));
        }
        authenticationHelper = new AuthenticationHelper(authenticationService);
        dataProvider.refresh();
    }

    @Override
    public void onShowLoginDialog(final ShowLoginDialogEvent event) {
        view.show("Login to " + configuration.getName());
    }
}
