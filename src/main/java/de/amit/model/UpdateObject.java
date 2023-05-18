package de.amit.model;

public class UpdateObject<T> {

	private final int id;
	private final String column;
	private final T value;

	public UpdateObject(int id, String column, T value) {
		this.id = id;
		this.column = column;
		this.value = value;
	}

	public UpdateObject(String column, T value) {
		this(-1, column, value);
	}

	public String getColumn() {
		return column;
	}

	public int getID() {
		return id;
	}

	public T getValue() {
		return value;
	}

	public <E extends T> E getValue(Class<E> type) {
		if (type.isInstance(value))
			return type.cast(value);
		return null;
	}

	public boolean isType(Class<?> type) {
		return type.isInstance(value);
	}
}