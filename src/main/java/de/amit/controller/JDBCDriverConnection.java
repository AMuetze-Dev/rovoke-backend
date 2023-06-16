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

	public synchronized static <T> void executeAdd(String destScheme, String destTable,
			UpdateObject<?>... updateObjects) throws SQLException {
		String prep = "";
		String query = String.format("INSERT INTO %s.\"%s\"(", destScheme, destTable);
		for (int i = 0; i < updateObjects.length; i++) {
			query += updateObjects[i].getColumn();
			prep += "?";
			if (i + 1 != updateObjects.length) {
				query += ",";
				prep += ",";
			}
		}
		query += ") VALUES (" + prep + ");";

		final Connection con = getConnection();
		final PreparedStatement statement = con.prepareStatement(query);
		for (int i = 0; i < updateObjects.length; i++)
			if (updateObjects[i].isType(String.class))
				statement.setString(i + 1, (String) updateObjects[i].getValue());
			else if (updateObjects[i].isType(Integer.class))
				statement.setInt(i + 1, (int) updateObjects[i].getValue());
			else if (updateObjects[i].isType(Double.class))
				statement.setDouble(i + 1, (double) updateObjects[i].getValue());
			else
				statement.setArray(i + 1, con.createArrayOf("text", (String[]) updateObjects[i].getValue()));

		LoggerService.info(statement.toString());
		statement.executeUpdate();
		con.close();
	}

	public static void executeDelete(String destScheme, String destTable, int id) throws SQLException {
		final String query = String.format("DELETE FROM %s.\"%s\" WHERE id = %d", destScheme, destTable, id);
		final Connection con = getConnection();
		final PreparedStatement statement = con.prepareStatement(query);
		LoggerService.info(statement.toString());
		statement.executeUpdate();
		con.close();
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

	public static void executeUpdate(String destScheme, String destTable, UpdateObject<?> updateObject)
			throws SQLException {
		final String query = String.format("UPDATE %s.\"%s\" SET %s = ? WHERE id = %d", destScheme, destTable,
				updateObject.getColumn(), updateObject.getID());
		final Connection con = getConnection();
		final PreparedStatement statement = con.prepareStatement(query);
		if (updateObject.isType(String.class))
			statement.setString(1, (String) updateObject.getValue());
		else if (updateObject.isType(Integer.class))
			statement.setInt(1, (int) updateObject.getValue());
		else if (updateObject.isType(Double.class))
			statement.setDouble(1, (double) updateObject.getValue());
		else
			statement.setArray(1, con.createArrayOf("text", (String[]) updateObject.getValue()));
		LoggerService.info(statement.toString());
		statement.executeUpdate();
		con.close();
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
