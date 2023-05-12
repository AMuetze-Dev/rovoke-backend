package de.amit.model;

public class UpdateObject {

	enum ObjectType {
		TEXT, INT, NUMERIC, TEXT_ARRAY;
	}

	public static class UpdateObjectBuilder {

		public UpdateObject buildWithIntValue(int id, String column, int intValue) {
			return new UpdateObject(id, column, "", intValue, -1, null, ObjectType.INT);
		}

		public UpdateObject buildWithDoubleValue(int id, String column, double doubleValue) {
			return new UpdateObject(id, column, "", 0, doubleValue, null, ObjectType.NUMERIC);
		}

		public UpdateObject buildWithTextValue(int id, String column, String textValue) {
			return new UpdateObject(id, column, textValue, -1, -1, null, ObjectType.TEXT);
		}

		public UpdateObject buildWithTextArray(int id, String column, String[] stringArray) {
			return new UpdateObject(id, column, "", -1, -1, stringArray, ObjectType.TEXT_ARRAY);
		}
	}

	private final int id;
	private final String column;
	private final String textValue;
	private final String[] textArray;
	private final int intValue;
	private final double doubleValue;
	private final ObjectType objectType;

	private UpdateObject(int id, String column, String textValue, int intValue, double doubleValue,
			String[] textArray, ObjectType objectType) {
		this.id = id;
		this.column = column;
		this.intValue = intValue;
		this.doubleValue = doubleValue;
		this.textValue = textValue;
		this.textArray = textArray;
		this.objectType = objectType;
	}

	public String getColumn() {
		return column;
	}

	public int getID() {
		return id;
	}

	public int getIntValue() {
		return intValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public String[] getTextArray() {
		return textArray;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public boolean isText() {
		return objectType == ObjectType.TEXT;
	}

	public boolean isInt() {
		return objectType == ObjectType.INT;
	}

	public boolean isNumeric() {
		return objectType == ObjectType.NUMERIC;
	}

	public boolean isTextArray() {
		return objectType == ObjectType.TEXT_ARRAY;
	}
}