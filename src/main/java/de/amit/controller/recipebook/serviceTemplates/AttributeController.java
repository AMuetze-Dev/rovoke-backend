package de.amit.controller.recipebook.serviceTemplates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import de.amit.controller.LoggerService;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.UpdateObject.UpdateObjectBuilder;
import de.amit.model.recipebook.AttributeObject;

public abstract class AttributeController extends RecipebookController<AttributeObject> {

    public AttributeController(String table) {
        super(table);
    }

    public Response add() {
        return super.add(new UpdateObjectBuilder().buildWithTextValue(0, "name", ""));
    }

    public Response change(UpdateObject updateObject) {
        throw new UnsupportedOperationException("Unimplemented method 'change'");
    }

    public AttributeObject get(int id) throws SQLException {
        return super.get("WHERE id = " + id);
    }

    public List<AttributeObject> getAll() throws SQLException {
        return super.getAll("");
    }

    @Override
    protected Function<ResultSet, AttributeObject> setResultSetFunction() {
        return resultSet -> {
            AttributeObject ao = new AttributeObject();
            try {
                ao.setId(resultSet.getInt("id"));
                ao.setValue(resultSet.getString("name"));
            } catch (SQLException e) {
                LoggerService.severe(Arrays.toString(e.getStackTrace()));
            }
            return ao;
        };
    }
}
