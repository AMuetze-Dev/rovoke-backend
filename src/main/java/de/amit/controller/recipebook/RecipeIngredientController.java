package de.amit.controller.recipebook;

import java.sql.PreparedStatement;
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

import de.amit.controller.JDBCDriverConnection;
import de.amit.controller.LoggerService;
import de.amit.controller.recipebook.serviceTemplates.RecipebookController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.recipebook.RecipeIngredient;

@RestController
@RequestMapping("/recipeingredients")
public class RecipeIngredientController extends RecipebookController<RecipeIngredient> {

	public RecipeIngredientController() {
		super("Recipe_Ingredients");
	}

	@PostMapping("/add")
	public Response add(@RequestBody RecipeIngredient[] recipeIngredients) {
		int id = -1;
		final String query = "SELECT max(id) FROM " + scheme + ".\"Recipes\";";
		try {
			final PreparedStatement statement = JDBCDriverConnection.getConnection().prepareStatement(query);
			final ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			id = resultSet.getInt("max");
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		if (id == -1)
			return new Response("Rezept konnte nicht geladen werden", false);

		Response response = null;

		for (final RecipeIngredient recipeIngredient : recipeIngredients) {
			final UpdateObject<?>[] uo = new UpdateObject[5];
			uo[0] = new UpdateObject<>("ingredient_id", recipeIngredient.getIngredientID() + 1);
			uo[1] = new UpdateObject<>("category", recipeIngredient.getCategory());
			uo[2] = new UpdateObject<>("amount", recipeIngredient.getAmount());
			uo[3] = new UpdateObject<>("recipe_id", id);
			uo[4] = new UpdateObject<>("unit_id", recipeIngredient.getUnit() + 1);
			response = super.add(uo);
		}
		return response;
	}

	@GetMapping("/get/{recipeID}")
	@ResponseBody
	public List<RecipeIngredient> get(@PathVariable int recipeID) throws SQLException {
		return super.getAll("WHERE recipe_id = " + recipeID);
	}

	@Override
	protected Function<ResultSet, RecipeIngredient> setResultSetFunction() {
		return resultSet -> {
			final RecipeIngredient recipeIngredient = new RecipeIngredient();
			try {
				recipeIngredient.setIngredientID(resultSet.getInt("ingredient_id"));
				recipeIngredient.setCategory(resultSet.getString("category"));
				recipeIngredient.setAmount(resultSet.getDouble("amount"));
				recipeIngredient.setRecipeID(resultSet.getInt("recipe_id"));
				recipeIngredient.setUnit(resultSet.getInt("unit_id"));
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return recipeIngredient;
		};
	}

}
