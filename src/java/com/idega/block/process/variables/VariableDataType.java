package com.idega.block.process.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.idega.util.CoreConstants;
import com.idega.util.StringUtil;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.3 $
 *
 *          Last modified: $Date: 2009/01/14 04:33:36 $ by $Author: civilis $
 */
public enum VariableDataType {

	DATE {
		@Override
		public String toString() {
			return date;
		}
	},
	STRING {
		@Override
		public String toString() {
			return string;
		}
	},
	LIST {
		@Override
		public String toString() {
			return list;
		}
	},
	FILE {
		@Override
		public String toString() {
			return file;
		}
	},
	FILES {
		@Override
		public String toString() {
			return files;
		}
	},
	OBJLIST {
		@Override
		public String toString() {
			return objectList;
		}
	},
	UNSPECIFIED {
		@Override
		public String toString() {
			return unspecified;
		}
	},
	SYSTEM {
		@Override
		public String toString() {
			return system;
		}
	},
	LONG {
		@Override
		public String toString() {
			return longType;
		}
	},
	DOUBLE {
		@Override
		public String toString() {
			return doubleType;
		}
	},
	ENUM {
		@Override
		public String toString() {
			return enumType;
		}
	};

	private static final String date = "date",
								string = "string",
								list = "list",
								file = "file",
								files = "files",
								unspecified = CoreConstants.EMPTY,
								objectList = "objlist",
								system = "system",
								longType = "long",
								doubleType = "double",
								enumType = "enum";

	public static Set<String> getAllTypesInStrings() {

		return getAllDataTypesEnumsMappings().keySet();
	}

	final private static Map<String, VariableDataType> allDataTypesEnumsMappings = new HashMap<String, VariableDataType>();

	static {

		for (VariableDataType type : values()) {
			allDataTypesEnumsMappings.put(type.toString(), type);
		}
	}

	private static Map<String, VariableDataType> getAllDataTypesEnumsMappings() {

		return allDataTypesEnumsMappings;
	}

	public static VariableDataType getByStringRepresentation(String type) {

		return getAllDataTypesEnumsMappings().get(type);
	}

	public static final VariableDataType getVariableType(String variableName) {
		if (StringUtil.isEmpty(variableName)) {
			return null;
		}

		for (VariableDataType type: VariableDataType.values()) {
			if (variableName.startsWith(type.toString())) {
				return type;
			}
		}
		return null;
	}

	@Override
	public abstract String toString();
}