package de.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import de.amit.cors.CorsConfig;

@SpringBootApplication
@ComponentScan(basePackages = "de.amit.controller")
@Import(CorsConfig.class)
public class LaunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaunchApplication.class, args);
//		new DatabaseController().resetDatabase();
//
//		/* UNITS */
//		final UnitController units = new UnitController();
//		units.add(new UpdateObject<>("name", "g"));
//		units.add(new UpdateObject<>("name", "ml"));
//		units.add(new UpdateObject<>("name", "Etwas"));
//
//		/* GERICHTARTEN */
//		final KindController kinds = new KindController();
//		kinds.add(new UpdateObject<>("name", "Vorspeise"));
//		kinds.add(new UpdateObject<>("name", "Hauptspeise"));
//		kinds.add(new UpdateObject<>("name", "Dessert"));
//		kinds.add(new UpdateObject<>("name", "Süßspeise"));
//		kinds.add(new UpdateObject<>("name", "Beilage"));
//
//		new ExcelHandler();
	}
}
