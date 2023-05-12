package de.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import de.amit.controller.DatabaseController;
import de.amit.controller.recipebook.IngredientController;
import de.amit.controller.recipebook.KindController;
import de.amit.controller.recipebook.UnitController;
import de.amit.cors.CorsConfig;
import de.amit.model.UpdateObject;
import de.amit.model.UpdateObject.UpdateObjectBuilder;

@SpringBootApplication
@ComponentScan(basePackages = "de.amit.controller")
@Import(CorsConfig.class)
public class LaunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaunchApplication.class, args);
		new DatabaseController().resetDatabase();

		/* INGREDIENTS */
		final IngredientController ingredients = new IngredientController();
		ingredients.add(new UpdateObject[] { new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Hühnerbrust"),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "protein", 12),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "carbohydrates", 4),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "fat", 49), });
		ingredients.add(new UpdateObject[] { new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Pudding"),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "protein", 45),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "carbohydrates", 2),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "fat", 45), });
		ingredients.add(new UpdateObject[] { new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Kartoffelsalat"),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "protein", 46),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "carbohydrates", 79),
				new UpdateObjectBuilder().buildWithDoubleValue(-1, "fat", 13), });

		/* UNITS */
		final UnitController units = new UnitController();
		units.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "g"));
		units.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "ml"));
		units.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Etwas"));

		/* GERICHTARTEN */
		final KindController kinds = new KindController();
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Herzhaft"));
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Süß"));
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(-1, "name", "Beilage"));
	}
}
