package de.amit.controller.serviceTemplates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import de.amit.controller.JDBCDriverConnection;
import de.amit.controller.LoggerService;
import de.amit.model.Response;
import de.amit.model.UpdateObject;

public abstract class CategoryServiceImpl<T> implements CategoryService<T> {

	private final Function<ResultSet, T> resultSet;
	protected final String scheme;
	protected final String table;

	public CategoryServiceImpl(String scheme, String table) {
		this.resultSet = setResultSetFunction();
		this.scheme = scheme;
		this.table = table;
	}

	@Override
	public Response add(UpdateObject<?>... updateObjects) {
		try {
			JDBCDriverConnection.executeAdd(scheme, table, updateObjects);
			return new Response("Hinzugefügt", true);
		} catch (final SQLException e) {
			e.printStackTrace();
			LoggerService.severe(Arrays.toString(e.getStackTrace()));
			return new Response("Hinzufügen fehlgeschlagen", false);
		}
	}

	@Override
	public Response change(UpdateObject<?> updateObject) {
		try {
			JDBCDriverConnection.executeUpdate(scheme, table, updateObject);
			return new Response("Update erfolgreich", true);
		} catch (final SQLException e) {
			e.printStackTrace();
			LoggerService.severe(Arrays.toString(e.getStackTrace()));
			return new Response("Update fehlgeschlagen", false);
		}
	}

	protected List<T> executeQuery(String query) throws SQLException {
		return JDBCDriverConnection.executeQuery(query, resultSet);
	}

	@Override
	public T get(String whereClause) throws SQLException {
		return executeQuery("SELECT * FROM " + scheme + ".\"" + table + "\" " + whereClause + " ORDER BY id ASC")
				.get(0);
	}

	@Override
	public List<T> getAll(String whereClause) throws SQLException {
		return executeQuery("SELECT * FROM " + scheme + ".\"" + table + "\" " + whereClause + " ORDER BY id ASC");
	}

	@Override
	public Response remove(int id) {
		try {
			JDBCDriverConnection.executeDelete(scheme, table, id);
			return new Response("Entfernen erfolgreich", true);
		} catch (final SQLException e) {
			LoggerService.severe(Arrays.toString(e.getStackTrace()));
			return new Response("Enfernen fehlgeschlagen", false);
		}
	}

	protected abstract Function<ResultSet, T> setResultSetFunction();
}
