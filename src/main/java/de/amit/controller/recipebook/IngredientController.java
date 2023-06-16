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
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.LoggerService;
import de.amit.controller.recipebook.serviceTemplates.RecipebookController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.recipebook.Ingredient;

@RestController
@RequestMapping("/ingredient")
public class IngredientController extends RecipebookController<Ingredient> {

	public IngredientController() {
		super("Ingredients");
	}

	@GetMapping("/add")
	public Response add() {
		final UpdateObject<?>[] uo = new UpdateObject[4];
		uo[0] = new UpdateObject<>("name", "");
		uo[1] = new UpdateObject<>("protein", 0);
		uo[2] = new UpdateObject<>("carbohydrates", 0);
		uo[3] = new UpdateObject<>("fat", 0);
		return super.add(uo);
	}

	@Override
	@PostMapping("/change")
	public Response change(@RequestBody UpdateObject<?> updateObject) {
		return super.change(updateObject);
	}

	@GetMapping("/get/{id}")
	public Ingredient get(@PathVariable int id) throws SQLException {
		return super.get("WHERE id = " + id);
	}

	@GetMapping("/all")
	public List<Ingredient> getAll() throws SQLException {
		return super.getAll("");
	}

	@Override
	@GetMapping("/remove/{id}")
	public Response remove(@PathVariable int id) {
		return super.remove(id);
	}

	@Override
	protected Function<ResultSet, Ingredient> setResultSetFunction() {
		return resultSet -> {
			final Ingredient ingredient = new Ingredient();
			try {
				ingredient.setId(resultSet.getInt("id"));
				ingredient.setName(resultSet.getString("name"));
				ingredient.setProtein(resultSet.getDouble("protein"));
				ingredient.setCarbohydrates(resultSet.getDouble("carbohydrates"));
				ingredient.setFat(resultSet.getDouble("fat"));
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return ingredient;
		};
	}

}
