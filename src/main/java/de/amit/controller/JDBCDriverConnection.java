package de.amit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import de.amit.model.UpdateObject;

public class JDBCDriverConnection {

	private static final String DB_FILEPATH = "//localhost:5432/postgres?user=root&password=root";

	public static <T> void executeAdd(String destScheme, String destTable, UpdateObject... updateObjects)
			throws SQLException {
		String prep = "";
		String query = String.format("INSERT INTO %s.\"%s\"(", destScheme, destTable);
		for (int i = 0; i < updateObjects.length; i++) {
			query += updateObjects[i].getColumn();
			prep += updateObjects[i].isTextArray() ? "?::text[]" : "?";
			if (i + 1 != updateObjects.length) {
				query += ",";
				prep += ",";
			}
		}
		query += ") VALUES (" + prep + ");";

		final Connection con = getConnection();
		final PreparedStatement statement = con.prepareStatement(query);
		for (int i = 0; i < updateObjects.length; i++)
			if (updateObjects[i].isText())
				statement.setString(i + 1, updateObjects[i].getTextValue());
			else if (updateObjects[i].isInt())
				statement.setInt(i + 1, updateObjects[i].getIntValue());
			else if (updateObjects[i].isNumeric())
				statement.setDouble(i + 1, updateObjects[i].getDoubleValue());
			else
				statement.setArray(i + 1, con.createArrayOf("text", updateObjects[i].getTextArray()));
		LoggerService.info(statement.toString());
		statement.executeUpdate();
	}

	public static <T> List<T> executeQuery(String query, Function<ResultSet, T> rowMapper) throws SQLException {
		LoggerService.info(query);
		final List<T> results = new ArrayList<>();
		try (PreparedStatement statement = getConnection().prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
				results.add(rowMapper.apply(resultSet));
		}
		return results;
	}

	public static void executeUpdate(String destScheme, String destTable, UpdateObject updateObject)
			throws SQLException {
		String query = String.format("UPDATE %s .\"%s\" SET %s = ", destScheme, destTable, updateObject.getColumn());
		query += updateObject.isText() ? "\"" + updateObject.getTextValue() + "\"" : updateObject.getIntValue();
		query += String.format(" WHERE id = %d;", updateObject.getID());
		LoggerService.info(query);
		final PreparedStatement statement = getConnection().prepareStatement(query);
		statement.executeUpdate();
	}

	public static Connection getConnection() throws SQLException {
		LoggerService.info("Verbindung zur Datenbank wird versucht aufzubauen.");
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql:" + DB_FILEPATH);
			LoggerService.fine("Verbindung zur Datenbank wurde aufgebaut.");
		} catch (final ClassNotFoundException e) {
			LoggerService.severe("Verbindung zur Datenbank fehlgeschlagen");
			LoggerService.severe(Arrays.toString(e.getStackTrace()));
		}
		return connection;
	}
}
