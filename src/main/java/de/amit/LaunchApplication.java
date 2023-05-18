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

@SpringBootApplication
@ComponentScan(basePackages = "de.amit.controller")
@Import(CorsConfig.class)
public class LaunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaunchApplication.class, args);
		new DatabaseController().resetDatabase();

		/* INGREDIENTS */
		final IngredientController ingredients = new IngredientController();
		ingredients
				.add(new UpdateObject[] { new UpdateObject<>("name", "Hühnerbrust"), new UpdateObject<>("protein", 12),
						new UpdateObject<>("carbohydrates", 4), new UpdateObject<>("fat", 49), });
		ingredients.add(new UpdateObject[] { new UpdateObject<>("name", "Pudding"), new UpdateObject<>("protein", 45),
				new UpdateObject<>("carbohydrates", 2), new UpdateObject<>("fat", 45), });
		ingredients.add(
				new UpdateObject[] { new UpdateObject<>("name", "Kartoffelsalat"), new UpdateObject<>("protein", 46),
						new UpdateObject<>("carbohydrates", 79), new UpdateObject<>("fat", 13), });

		/* UNITS */
		final UnitController units = new UnitController();
		units.add(new UpdateObject<>("name", "g"));
		units.add(new UpdateObject<>("name", "ml"));
		units.add(new UpdateObject<>("name", "Etwas"));

		/* GERICHTARTEN */
		final KindController kinds = new KindController();
		kinds.add(new UpdateObject<>("name", "Herzhaft"));
		kinds.add(new UpdateObject<>("name", "Süß"));
		kinds.add(new UpdateObject<>("name", "Beilage"));
		kinds.add(new UpdateObject<>("name", "a"));
		kinds.add(new UpdateObject<>("name", "b"));
		kinds.add(new UpdateObject<>("name", "c"));
		kinds.add(new UpdateObject<>("name", "d"));
		kinds.add(new UpdateObject<>("name", "e"));
		kinds.add(new UpdateObject<>("name", "f"));
		kinds.add(new UpdateObject<>("name", "g"));
		kinds.add(new UpdateObject<>("name", "h"));
		kinds.add(new UpdateObject<>("name", "i"));
	}
}
