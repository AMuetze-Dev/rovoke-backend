package de.amit;

import org.springframework.boot.SpringApplication;

import de.amit.controller.DatabaseController;
import de.amit.controller.recipebook.IngredientController;
import de.amit.controller.recipebook.KindController;
import de.amit.controller.recipebook.UnitController;
import de.amit.model.UpdateObject;
import de.amit.model.UpdateObject.UpdateObjectBuilder;

public class LaunchApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LaunchApplication.class, args);
        new DatabaseController().resetDatabase();

		/* INGREDIENTS */
		final IngredientController ingredients = new IngredientController();
        ingredients.add(new UpdateObject[] {
            new UpdateObjectBuilder().buildWithTextValue(0, "name", "Hühnerbrust"),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "protein", 12),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "carbohydrates", 4),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "fat", 49),
        });
        ingredients.add(new UpdateObject[] {
            new UpdateObjectBuilder().buildWithTextValue(0, "name", "Pudding"),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "protein", 45),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "carbohydrates", 2),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "fat", 45),
        });
        ingredients.add(new UpdateObject[] {
            new UpdateObjectBuilder().buildWithTextValue(0, "name", "Kartoffelsalat"),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "protein", 46),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "carbohydrates", 79),
            new UpdateObjectBuilder().buildWithDoubleValue(0, "fat", 13),
        });

		/* UNITS */
		final UnitController units = new UnitController();
		units.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "g"));
		units.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "ml"));
		units.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "Etwas"));

		/* GERICHTARTEN */
		final KindController kinds = new KindController();
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "Herzhaft"));
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "Süß"));
		kinds.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", "Beilage"));
    }
}
