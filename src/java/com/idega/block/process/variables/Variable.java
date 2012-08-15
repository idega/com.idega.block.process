package com.idega.block.process.variables;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.6 $
 *
 *          Last modified: $Date: 2009/02/16 22:02:39 $ by $Author: donatas $
 */
public class Variable {

	public static final String ACCESS_REQUIRED = "required";
	public static final String ACCESS_READ = "read";
	public static final String ACCESS_WRITE = "write";

	private String name;
	private VariableDataType dataType;
	private Set<String> accesses = Collections.emptySet();

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

	private void setAccesses(HashSet<String> accesses) {
		if (!ListUtil.isEmpty(accesses)) {
			this.accesses = accesses;
		}
	}

	public boolean hasAccess(String access) {
		return accesses.contains(access);
	}

	public Set<String> getAccesses() {
		return accesses;
	}

	@Override
	public String toString() {
		return new StringBuilder("name: ").append(name).append(", data type: ").append(dataType.toString()).toString();
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

	public static Variable parseDefaultStringRepresentationWithAccess(String representation, String accesses) {

		Variable variable = Variable.parseDefaultStringRepresentation(representation);
		if (!StringUtil.isEmpty(accesses)) {
			HashSet<String> accessesSet = new HashSet<String>();
			List<String> accessesList = StringUtil.getValuesFromString(accesses, ",");
			accessesSet.addAll(accessesList);
			variable.setAccesses(accessesSet);
		}
		return variable;
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