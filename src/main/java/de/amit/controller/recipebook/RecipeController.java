package de.amit.controller.recipebook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.LoggerService;
import de.amit.controller.recipebook.serviceTemplates.RecipebookController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.recipebook.Recipe;

@RestController
@RequestMapping("/recipe")
public class RecipeController extends RecipebookController<Recipe> {

	public RecipeController() {
		super("Recipes");
	}

	@PostMapping("/add")
	public Response addRecipe(@RequestBody Recipe recipe) {
		final UpdateObject<?>[] uo = new UpdateObject[6];
		uo[0] = new UpdateObject<>("title", recipe.getTitle());
		uo[1] = new UpdateObject<>("difficulty", recipe.getDifficulty());
		uo[2] = new UpdateObject<>("preparation", recipe.getPreparation());
		uo[3] = new UpdateObject<>("cooking", recipe.getCooking());
		uo[4] = new UpdateObject<>("kind_id", recipe.getKind());
		uo[5] = new UpdateObject<>("instructions", recipe.getInstructions());
		return super.add(uo);
	}

	@GetMapping("/all")
	public List<Recipe> getAll() throws SQLException {
		return super.getAll("");
	}

	@GetMapping("/get/{id}")
	@ResponseBody
	public Recipe getRecipe(@PathVariable int id) throws SQLException {
		return super.get("WHERE id = " + id);
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
