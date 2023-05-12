package de.amit.controller.recipebook;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.recipebook.serviceTemplates.AttributeController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.recipebook.AttributeObject;

@RestController
@RequestMapping("/kind")
public class KindController extends AttributeController {

    public KindController() {
        super("Kinds");
    }

    @GetMapping("/add")
    public Response add() {
        return super.add();
    }

    @PostMapping("/change")
    public Response change(@RequestBody UpdateObject updateObject) {
        return super.change(updateObject);
    }

    @GetMapping("/{id}")
    public AttributeObject get(@PathVariable int id) throws SQLException {
        return super.get(id);
    }

    @GetMapping("/all")
    public List<AttributeObject> getAll() throws SQLException {
        return super.getAll();
    }
}
