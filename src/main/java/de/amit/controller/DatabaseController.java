package de.amit.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.model.Response;

@RestController
@RequestMapping("/database")
public class DatabaseController {

	@GetMapping("/create")
	public Response createDatabase() {
		return executeFile("CreateTables", "Database created");
	}

	@GetMapping("/delete")
	public Response deleteDatabase() {
		return executeFile("DropTables", "Database deleted");
	}

	private Response executeFile(String fileName, String successMessage) {
		final File file = new File(System.getProperty("user.dir") + "\\" + fileName + ".txt");
		if (!file.exists())
			return new Response("File " + fileName + " not found", false);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(" " + line.trim());
				if (line.trim().endsWith(";")) {
					executeUpdate(sb.toString().trim());
					sb = new StringBuilder();
				}
			}
			return new Response(successMessage, true);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return new Response("Can't execute file" + fileName, false);
		}
	}

	private void executeUpdate(String query) throws SQLException {
		LoggerService.info(query);
		final Statement statement = JDBCDriverConnection.getConnection().createStatement();
		statement.executeUpdate(query);
	}

	@GetMapping("/reset")
	public Response resetDatabase() {
		Response response = deleteDatabase();
		LoggerService.info(response.getMessage());
		if (!response.isStatus())
			return response;
		response = createDatabase();
		LoggerService.info(response.getMessage());
		return response;
	}
}
