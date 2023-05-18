package de.amit.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerService {

	public static void fine(String msg) {
		log(Level.FINE, msg);
	}

	public static void info(String msg) {
		log(Level.INFO, msg);
	}

	private synchronized static void log(Level level, String msg) {
		final LoggerService handler = new LoggerService();
		handler.logger.log(level, msg + "\n");
		handler.handler.close();
	}

	public static void severe(String msg) {
		log(Level.SEVERE, msg);
	}

	private FileHandler handler;

	private final Logger logger = Logger.getLogger("ServerLogger");

	private LoggerService() {
		final String date = new SimpleDateFormat("yyyy-dd-MM").format(Calendar.getInstance().getTime());
		final Path path = Paths.get(System.getProperty("user.dir"), "logs");
		path.toFile().mkdirs();
		try {
			handler = new FileHandler(path.resolve(date + ".log").toString(), true);
			handler.setFormatter(getFormatter());
			logger.addHandler(handler);
			logger.setLevel(Level.ALL);
		} catch (final IOException e) {
			LoggerService.severe(e.getMessage());
		}
	}

	private Formatter getFormatter() {
		return new Formatter() {

			@Override
			public String format(LogRecord record) {
				return String.format("%6s %8s %s", record.getLevel(),
						new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), record.getMessage());
			}
		};
	}
}
