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
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.LoggerService;
import de.amit.controller.recipebook.serviceTemplates.RecipebookController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.UpdateObject.UpdateObjectBuilder;
import de.amit.model.recipebook.Ingredient;

@RestController
public class IngredientController extends RecipebookController<Ingredient>{

    public IngredientController() {
        super("Ingredients");
    }

    @GetMapping("/add")
    public Response add() {
        return super.add(new UpdateObject[] {
            new UpdateObjectBuilder().buildWithTextValue(-1, "name", ""),
            new UpdateObjectBuilder().buildWithDoubleValue(-1, "protein", 0),
            new UpdateObjectBuilder().buildWithDoubleValue(-1, "carbohydrates", 0),
            new UpdateObjectBuilder().buildWithDoubleValue(-1, "fat", 0),
        });
    }

    @PostMapping("/change")
    public Response change(@RequestBody UpdateObject updateObject) {
        return super.change(updateObject);
    }

    @GetMapping("/{id}")
    public Ingredient get(@PathVariable int id) throws SQLException {
        return super.get("WHERE id = " + id);
    }

    @GetMapping("/all")
    public List<Ingredient> getAll() throws SQLException {
        return super.getAll("");
    }

    @Override
    protected Function<ResultSet, Ingredient> setResultSetFunction() {
        return resultSet -> {
            Ingredient ingredient = new Ingredient();
            try {
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setProtein(resultSet.getDouble("protein"));
                ingredient.setCarbohydrates(resultSet.getDouble("carbohydrates"));
                ingredient.setFat(resultSet.getDouble("fat"));
            } catch(SQLException e) {
                LoggerService.severe(Arrays.toString(e.getStackTrace()));
            }
            return ingredient;
        };
    }
    
}
