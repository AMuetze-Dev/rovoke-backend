package de.amit.controller.serviceTemplates;

import java.sql.SQLException;
import java.util.List;

import de.amit.model.Response;
import de.amit.model.UpdateObject;

public interface CategoryService<T extends Object> {

	public Response add(UpdateObject<?>... updateObjects);

	public Response change(UpdateObject<?> updateObject);

	public T get(String whereClause) throws SQLException;

	public List<T> getAll(String whereClause) throws SQLException;
}
