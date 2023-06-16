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
@RequestMapping("/unit")
public class UnitController extends AttributeController {

	public UnitController() {
		super("Units");
	}

	@Override
	@GetMapping("/add")
	public Response add() {
		return super.add();
	}

	@Override
	@PostMapping("/change")
	public Response change(@RequestBody UpdateObject<?> updateObject) {
		return super.change(updateObject);
	}

	@Override
	@GetMapping("/get/{id}")
	public AttributeObject get(@PathVariable int id) throws SQLException {
		return super.get(id);
	}

	@Override
	@GetMapping("/all")
	public List<AttributeObject> getAll() throws SQLException {
		return super.getAll();
	}

	@Override
	@GetMapping("/remove/{id}")
	public Response remove(@PathVariable int id) {
		return super.remove(id);
	}

}