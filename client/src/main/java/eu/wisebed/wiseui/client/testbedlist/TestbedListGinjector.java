package eu.wisebed.wiseui.client.testbedlist;

import com.google.gwt.inject.client.Ginjector;

import eu.wisebed.wiseui.client.testbedlist.presenter.LoginDialogPresenter;
import eu.wisebed.wiseui.client.testbedlist.presenter.TestbedEditPresenter;
import eu.wisebed.wiseui.client.testbedlist.view.LoginDialogView;
import eu.wisebed.wiseui.client.testbedlist.view.TestbedEditView;
import eu.wisebed.wiseui.client.testbedlist.view.TestbedListView;
import eu.wisebed.wiseui.client.util.AuthenticationManager;

public interface TestbedListGinjector extends Ginjector {

	TestbedListActivity getTestbedListActivity();
	
	TestbedListView getTestbedListView();
	
    LoginDialogView getLoginDialogView();
    
    LoginDialogPresenter getLoginDialogPresenter();
    
	TestbedEditPresenter getTestbedEditPresenter();
	
	TestbedEditView getTestbedEditView();

    AuthenticationManager getAuthenticationManager();
}
