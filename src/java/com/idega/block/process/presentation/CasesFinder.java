package com.idega.block.process.presentation;

import java.util.List;

import javax.faces.context.FacesContext;

import com.idega.block.process.business.CasesListColumn;
import com.idega.block.process.business.ProcessConstants;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Script;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.TextInput;
import com.idega.util.PresentationUtil;

public class CasesFinder extends IWBaseComponent {
	
	private String searchTextInputStyle = "textInputForCasesSearchStyleClass";
	
	@Override
	protected void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		PresentationUtil.addStyleSheetToHeader(iwc, iwc.getIWMainApplication().getBundle(ProcessConstants.IW_BUNDLE_IDENTIFIER).getVirtualPathWithFileNameString("style/process.css"));
		
		Layer container = new Layer();
		
		IWResourceBundle iwrb = getIWResourceBundle(iwc, ProcessConstants.IW_BUNDLE_IDENTIFIER);
		
		Table2 casesSearchTable = new Table2();
		casesSearchTable.setStyleClass("casesSearchTableStyle");
		container.add(casesSearchTable);
		TableRow row = casesSearchTable.createRow();
		TableCell2 cell = row.createCell();
		cell.add(new Text(iwrb.getLocalizedString("select_column", "Select column:")));
		
		cell = row.createCell();
		DropdownMenu columnsMenu = new DropdownMenu();
		columnsMenu.setStyleClass(searchTextInputStyle);
		cell.add(columnsMenu);
		addCasesColumnsForSearch(iwc, columnsMenu);
		
		row = casesSearchTable.createRow();
		cell = row.createCell();
		cell.add(new Text(iwrb.getLocalizedString("enter_search_key", "Enter search key:")));
		
		cell = row.createCell();
		TextInput searchText = new TextInput();
		searchText.setStyleClass(searchTextInputStyle);
		cell.add(searchText);
		
		Layer buttons = new Layer();
		buttons.setStyleClass("casesSearchButtonsContainerStyle");
		container.add(buttons);
		GenericButton search = new GenericButton("executeSearch", iwrb.getLocalizedString("search", "Search"));
		search.setOnClick(new StringBuilder("searchForCasesInTable('").append(searchText.getId()).append("', '").append(columnsMenu.getId()).append("');").toString());
		buttons.add(search);
		
		GenericButton clear = new GenericButton("clearSearchTerms", iwrb.getLocalizedString("clear", "Clear"));
		clear.setOnClick(new StringBuilder("clearSearchTerms('").append(searchText.getId()).append("');").toString());
		buttons.add(clear);
		
		Script script = new Script();
		script.addScriptLine("registerSearchWindowActions();");
		container.add(script);
		
		add(container);
	}

	private void addCasesColumnsForSearch(IWContext iwc, DropdownMenu columnsMenu) {
		Object o = iwc.getSessionAttribute(ProcessConstants.getKeyForCasesColumnsAttribute(iwc));
		if (o instanceof List) {
			List<CasesListColumn> columns = (List<CasesListColumn>) o;
			CasesListColumn column = null;
			for (int i = 0; i < columns.size(); i++) {
				column = columns.get(i);
				if (column.getType() != null) {
					columnsMenu.addMenuElement(i, column.getName());
				}
			}
		}
	}
	
	@Override
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[2];
		values[0] = super.saveState(ctx);
		values[1] = searchTextInputStyle;
		return values;
	}

	@Override
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		searchTextInputStyle = (String) values[1];
	}
}