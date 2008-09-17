package com.idega.block.process.variables;

import com.idega.util.CoreConstants;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.1 $
 *
 * Last modified: $Date: 2008/09/17 13:08:30 $ by $Author: civilis $
 */
public class Variable {

	private String name;
	private VariableDataType dataType;
	
	public Variable(String name, VariableDataType dataType) { 
	
		if(name == null)
			throw new NullPointerException("Variable name not provided");
		
		if(dataType == null)
			throw new NullPointerException("Data type not provided");
		
		this.name = name;
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VariableDataType getDataType() {
		return dataType;
	}

	public void setDataType(VariableDataType dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String toString() {
		
		return new StringBuilder("name: ")
		.append(name)
		.append(", data type: ")
		.append(dataType.toString())
		.toString();
	}
	
	public String getDefaultStringRepresentation() {
		
		return new StringBuilder(getDataType().toString()).append(CoreConstants.UNDER).append(getName()).toString();
	}
	
	public static Variable parseDefaultStringRepresentation(String representation) {
		
		String[] splitted = representation.split(CoreConstants.UNDER);
		
		String dataTypeStr = splitted[0];
		VariableDataType dataType = VariableDataType.getByStringRepresentation(dataTypeStr);
		
		if(dataType == null) {
			
			StringBuilder supportedDataTypes = new StringBuilder();
			
			for (String supportedDataType : VariableDataType.getAllTypesInStrings()) {
				
				supportedDataTypes.append(CoreConstants.NEWLINE);
				supportedDataTypes.append(supportedDataType);				
			}
			
			throw new UnsupportedOperationException("Data type not supported of: >"+dataTypeStr+"<. Supported data types: "+supportedDataTypes.toString());
		}
			
		return new Variable(splitted[splitted.length-1], dataType);
	}
}