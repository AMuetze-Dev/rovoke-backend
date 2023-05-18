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
import de.amit.model.recipebook.Recipe;
import de.amit.model.recipebook.RecipeIngredient;

@RestController
@RequestMapping("/recipe")
public class RecipeController extends RecipebookController<Recipe> {

	public RecipeController() {
		super("Recipes");
	}

	@GetMapping("/add")
	public Response add(Recipe recipe, RecipeIngredient[] recipeIngredients) {
		final UpdateObject<?>[] uo = new UpdateObject[6];
		uo[0] = new UpdateObject<>("title", recipe.getTitle());
		uo[1] = new UpdateObject<>("difficulty", recipe.getDifficulty());
		uo[2] = new UpdateObject<>("preparation", recipe.getPreparation());
		uo[3] = new UpdateObject<>("cooking", recipe.getCooking());
		uo[4] = new UpdateObject<>("kind_id", recipe.getKind());
		uo[5] = new UpdateObject<>("instructions", new String[] { "" });
		return super.add(uo);
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
