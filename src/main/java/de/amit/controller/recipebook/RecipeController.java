package de.amit.controller.recipebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.LoggerService;
import de.amit.controller.recipebook.serviceTemplates.RecipebookController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.UpdateObject.UpdateObjectBuilder;
import de.amit.model.recipebook.Recipe;

@RestController
@RequestMapping("/recipe")
public class RecipeController extends RecipebookController<Recipe> {

	public RecipeController() {
		super("Recipes");
	}

	@GetMapping("/add")
	public Response add(Recipe recipe) {
		return super.add(
				new UpdateObject[] { new UpdateObjectBuilder().buildWithTextValue(-1, "title", recipe.getTitle()),
						new UpdateObjectBuilder().buildWithIntValue(-1, "difficulty", recipe.getDifficulty()),
						new UpdateObjectBuilder().buildWithIntValue(-1, "preparation", recipe.getPreparation()),
						new UpdateObjectBuilder().buildWithIntValue(-1, "cooking", recipe.getCooking()),
						new UpdateObjectBuilder().buildWithIntValue(-1, "kind_id", recipe.getKind()),
						new UpdateObjectBuilder().buildWithTextArray(-1, "instructions", new String[] { "" }) });
	}

	@Override
	protected Function<ResultSet, Recipe> setResultSetFunction() {
		return resultSet -> {
			final Recipe recipe = new Recipe();
			try {
				recipe.setId(resultSet.getInt("id"));
				recipe.setTitle(resultSet.getString("title"));
				recipe.setDifficulty(resultSet.getInt("difficulty"));
				recipe.setPreparation(resultSet.getInt("preparation"));
				recipe.setCooking(resultSet.getInt("cooking"));
				recipe.setKind(resultSet.getInt("kind_id"));
				recipe.setInstructions((String[]) resultSet.getArray("instructions").getArray());
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return recipe;
		};
	}

}
