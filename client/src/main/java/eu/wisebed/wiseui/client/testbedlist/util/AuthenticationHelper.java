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
package eu.wisebed.wiseui.client.testbedlist.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import eu.wisebed.wiseui.api.SNAAServiceAsync;
import eu.wisebed.wiseui.client.testbedselection.common.UrnPrefixInfo;
import eu.wisebed.wiseui.client.testbedselection.common.UrnPrefixInfo.State;
import eu.wisebed.wiseui.shared.dto.SecretAuthenticationKey;

import java.util.Iterator;
import java.util.List;

public class AuthenticationHelper implements AsyncCallback<SecretAuthenticationKey> {

    public interface Callback {

        void onStateChanged(UrnPrefixInfo info, State state);

        void onSuccess(SecretAuthenticationKey result);

        void onFinish();
    }

    private final SNAAServiceAsync authenticationService;

    private String endpointUrl;

    private String username;

    private String password;

    private Iterator<UrnPrefixInfo> iterator;

    private Callback callback;

    private UrnPrefixInfo current;

    private boolean canceled = false;

    @Inject
    public AuthenticationHelper(final SNAAServiceAsync authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void onSuccess(final SecretAuthenticationKey result) {
        callback.onSuccess(result);
        current.setState(State.SUCCESS);
        callback.onStateChanged(current, State.SUCCESS);
        proceedNext();
    }

    @Override
    public void onFailure(final Throwable caught) {
        GWT.log(caught.getMessage());
        current.setState(State.FAILED, caught.getMessage());
        callback.onStateChanged(current, State.FAILED);
        proceedNext();
    }

    private void proceedNext() {
        if (iterator.hasNext() && !canceled) {
            current = iterator.next();
            if (current.isChecked()) {
                current.setState(State.AUTHENTICATE);
                callback.onStateChanged(current, State.AUTHENTICATE);
                authenticationService.authenticate(endpointUrl, current.getUrnPrefix(), username, password, this);
            } else {
                current.setState(State.SKIPPED);
                callback.onStateChanged(current, State.SKIPPED);
                proceedNext();
            }
        } else {
            if (canceled) {
                current.setState(State.CANCELED);
                callback.onStateChanged(current, State.CANCELED);
            }
            callback.onFinish();
        }
    }

    public void cancel() {
        canceled = true;
    }

    public void authenticate(final List<UrnPrefixInfo> list, final String endpointUrl, final String username, final String password, final Callback callback) {
        this.iterator = list.iterator();
        this.endpointUrl = endpointUrl;
        this.username = username;
        this.password = password;
        this.callback = callback;
        proceedNext();
    }
}
