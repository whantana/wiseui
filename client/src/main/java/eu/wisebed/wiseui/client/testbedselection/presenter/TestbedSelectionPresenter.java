package eu.wisebed.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

import eu.wisebed.wiseui.client.testbedselection.TestbedSelectionPlace;
import eu.wisebed.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import eu.wisebed.wiseui.client.testbedselection.event.ConfigurationSelectedEvent.ConfigurationSelectedHandler;
import eu.wisebed.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import eu.wisebed.wiseui.client.testbedselection.event.ThrowableEvent;
import eu.wisebed.wiseui.client.testbedselection.event.ThrowableEvent.ThrowableHandler;
import eu.wisebed.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import eu.wisebed.wiseui.client.testbedselection.event.WisemlLoadedEvent.WisemlLoadedHandler;
import eu.wisebed.wiseui.client.testbedselection.view.TestbedSelectionView;
import eu.wisebed.wiseui.client.testbedselection.view.TestbedSelectionView.Presenter;
import eu.wisebed.wiseui.widgets.messagebox.MessageBox;
import eu.wisebed.wiseui.shared.dto.TestbedConfiguration;
import eu.wisebed.wiseui.shared.exception.WisemlException;

/**
 * The presenter for the {@link TestbedSelectionView}.
 *
 * @author Malte Legenhausen
 */
public class TestbedSelectionPresenter implements Presenter, ConfigurationSelectedHandler, WisemlLoadedHandler, ThrowableHandler {

    private final EventBus eventBus;
    private final TestbedSelectionView view;
    private PlaceController placeController;
    private TestbedSelectionPlace place;
    private TestbedConfiguration configuration;

    @Inject
    public TestbedSelectionPresenter(final EventBus eventBus,
                                     final PlaceController placeController,
                                     final TestbedSelectionView view) {
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.view = view;
        view.getLoginEnabled().setEnabled(false);
        view.getReloadEnabled().setEnabled(false);
        bind();
    }

    private void bind() {
        eventBus.addHandler(WisemlLoadedEvent.TYPE, this);
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
        eventBus.addHandler(ThrowableEvent.TYPE, this);
    }

    @Override
    public void reload() {
        view.getReloadEnabled().setEnabled(false);
        eventBus.fireEvent(new ConfigurationSelectedEvent(configuration));
    }

    @Override
    public void showLoginDialog() {
        eventBus.fireEventFromSource(new ShowLoginDialogEvent(), this);
    }

    @Override
    public void setPlace(final TestbedSelectionPlace place) {
    	this.place = place;
    	view.setContentSelection(place.getView());
    }

    @Override
    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        view.getReloadEnabled().setEnabled(true);
    }

    @Override
    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        configuration = event.getConfiguration();
        view.getLoginEnabled().setEnabled(true);
    }

    @Override
    public void onThrowable(final ThrowableEvent event) {
        if (event.getThrowable() instanceof WisemlException) {
            final String title = "Unavailable Testbed " + configuration.getName();
            final String message = "The Testbed "
                    + configuration.getName()
                    + " is not available.\n"
                    + event.getThrowable().getMessage();
            MessageBox.error(title, message, event.getThrowable(), null);
        }
    }

    @Override
    public void setContentSelection(final String view) {
	placeController.goTo(new TestbedSelectionPlace(place.getSelection(), view));
    }
}
