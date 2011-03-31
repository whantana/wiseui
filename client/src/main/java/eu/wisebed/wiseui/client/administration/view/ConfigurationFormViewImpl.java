package eu.wisebed.wiseui.client.administration.view;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionModel;
import com.google.inject.Singleton;

/**
 * ConfigurationFormView implementation.
 * 
 * @author Malte Legenhausen
 */
@Singleton
public class ConfigurationFormViewImpl extends Composite implements ConfigurationFormView {

	private static ConfigurationFormViewImplUiBinder uiBinder = GWT.create(ConfigurationFormViewImplUiBinder.class);

    interface ConfigurationFormViewImplUiBinder extends UiBinder<Widget, ConfigurationFormViewImpl> {
    }

    @UiField
    HTMLPanel contentPanel;
    @UiField
    Element infoElement;
    @UiField
    Label infoLabel;
    @UiField
    TextBox nameTextBox;
    @UiField
    TextBox testbedUrlTextBox;
    @UiField
    TextBox snaaEndpointUrlTextBox;
    @UiField
    TextBox rsEndpointUrlTextBox;
    @UiField
    TextBox sessionManagementEndpointUrlTextBox;
    @UiField
    ListBox isFederatedListBox;
    @UiField
    TextBox urnPrefixTextBox;
    @UiField
    Button addButton;
    @UiField
    CellList<String> urnPrefixList;
    @UiField
    Button removeButton;
    
    private Presenter presenter;
    
    public ConfigurationFormViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
    
    @UiFactory
    protected CellList<String> createCellList() {
    	return new CellList<String>(new TextCell());
    }
    
    @UiHandler("addButton")
    public void handleAddButtonClicked(final ClickEvent event) {
    	presenter.add();
    }
    
    @UiHandler("removeButton")
    public void handleRemoveButtonClicked(final ClickEvent event) {
    	presenter.remove();
    }

	@Override
	public HasText getNameHasText() {
		return nameTextBox;
	}

	@Override
	public HasText getTestbedUrlHasText() {
		return testbedUrlTextBox;
	}
	
	@Override
	public HasText getSnaaEndpointUrlHasText() {
		return snaaEndpointUrlTextBox;
	}

	@Override
	public HasText getRsEndpointUrlHasText() {
		return rsEndpointUrlTextBox;
	}

	@Override
	public HasText getSessionManagementEndpointUrlHasText() {
		return sessionManagementEndpointUrlTextBox;
	}

	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasText getUrnPrefixHasText() {
		return urnPrefixTextBox;
	}

	@Override
	public HasData<String> getUrnPrefixHasData() {
		return urnPrefixList;
	}

	@Override
	public void setUrnPrefixSelectionModel(final SelectionModel<String> selectionModel) {
		urnPrefixList.setSelectionModel(selectionModel);
	}

	@Override
	public void setFederatedItems(final List<String> items) {
		isFederatedListBox.clear();
		for (final String item : items) {
			isFederatedListBox.addItem(item);
		}
	}

	@Override
	public void setFederatedSelectedIndex(final int index) {
		isFederatedListBox.setSelectedIndex(index);
	}

	@Override
	public int getFederatedSelectedIndex() {
		return isFederatedListBox.getSelectedIndex();
	}

	@Override
	public HasEnabled getUrnPrefixRemoveHasEnabled() {
		return removeButton;
	}

	@Override
	public HasText getInfoHasText() {
		return infoLabel;
	}

	@Override
	public void setInfoVisibility(final boolean visibility) {
		if (visibility) {
			if (!infoElement.hasParentElement()) {
				contentPanel.getElement().insertFirst(infoElement);
			}
		} else {
			infoElement.removeFromParent();
		}
	}
}