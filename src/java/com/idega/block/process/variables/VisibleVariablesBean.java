package com.idega.block.process.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.util.ArrayUtil;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * 
 * <p>Holding variable names to be shown in presentation logic</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Jun 20, 2014
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service
public class VisibleVariablesBean {

	private Map<String, Collection<String>> variablesByComponentId = null;

	public void setVariables(String componentId, String commaSeparatedVariables) {
		if (!StringUtil.isEmpty(commaSeparatedVariables)) {
			String[] splittedVariables = commaSeparatedVariables.split(CoreConstants.COMMA);
			if (!ArrayUtil.isEmpty(splittedVariables)) {
				setVariables(componentId, Arrays.asList(splittedVariables));
			}
		}
	}

	public void setVariables(String componentId, Collection<String> variables) {
		if (!StringUtil.isEmpty(componentId)) {
			Collection<String> existingVariables = getVariablesByComponentId().get(componentId);
			if (ListUtil.isEmpty(existingVariables)) {
				existingVariables = new ArrayList<String>();
				getVariablesByComponentId().put(componentId, existingVariables);
			} else {
				existingVariables.clear();
			}

			existingVariables.addAll(variables);
		}
	}

	public Map<String, Collection<String>> getVariablesByComponentId() {
		if (this.variablesByComponentId == null) {
			this.variablesByComponentId = new HashMap<String, Collection<String>>();
		}

		return variablesByComponentId;
	}

	public Collection<String> getVariablesByComponentId(String componentId) {
		return getVariablesByComponentId().get(componentId);
	}

	public void setVariablesByComponentId(Map<String, Collection<String>> variablesByComponentId) {
		this.variablesByComponentId = variablesByComponentId;
	}
}
