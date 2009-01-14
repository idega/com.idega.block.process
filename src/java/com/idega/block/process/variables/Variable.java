package com.idega.block.process.variables;

import com.idega.util.CoreConstants;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.5 $
 * 
 *          Last modified: $Date: 2009/01/14 04:45:06 $ by $Author: civilis $
 */
public class Variable {

	private String name;
	private VariableDataType dataType;

	public Variable(String name, VariableDataType dataType) {

		if (name == null)
			throw new NullPointerException("Variable name not provided");

		if (dataType == null)
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

		return new StringBuilder("name: ").append(name).append(", data type: ")
				.append(dataType.toString()).toString();
	}

	public String getDefaultStringRepresentation() {

		return getDataType() != VariableDataType.UNSPECIFIED ? new StringBuilder(
				getDataType().toString()).append(CoreConstants.UNDER).append(
				getName()).toString()
				: getName();
	}

	public static Variable parseDefaultStringRepresentation(
			String representation) {

		final String dataTypeStr;

		if (representation.contains(CoreConstants.UNDER)) {

			dataTypeStr = representation.substring(0, representation
					.indexOf(CoreConstants.UNDER));
		} else {
			dataTypeStr = CoreConstants.EMPTY;
		}

		VariableDataType dataType = VariableDataType
				.getByStringRepresentation(dataTypeStr);

		if (dataType == null) {

			StringBuilder supportedDataTypes = new StringBuilder();

			for (String supportedDataType : VariableDataType
					.getAllTypesInStrings()) {

				supportedDataTypes.append(CoreConstants.NEWLINE);
				supportedDataTypes.append(supportedDataType);
			}

			throw new UnsupportedOperationException(
					"Data type not supported of: >" + dataTypeStr
							+ "<. Supported data types: "
							+ supportedDataTypes.toString());
		}

		String variableName;

		if (representation.contains(CoreConstants.UNDER)) {

			variableName = representation.substring(representation
					.indexOf(CoreConstants.UNDER) + 1);
		} else {
			variableName = representation;
		}

		return new Variable(variableName, dataType);
	}

	@Override
	public boolean equals(Object obj) {

		if (super.equals(obj)) {
			return true;
		}

		if (obj instanceof Variable) {

			return getDataType() == ((Variable) obj).getDataType()
					&& getName().equals(((Variable) obj).getName());
		}

		return false;
	}
}