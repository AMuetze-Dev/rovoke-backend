package de.amit.controller.dogschool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.controller.LoggerService;
import de.amit.controller.dogschool.serviceTemplates.DogschoolController;
import de.amit.model.Response;
import de.amit.model.UpdateObject;
import de.amit.model.dogschool.Section;

@RestController
@RequestMapping("/dogschool/{courseId}/section")
public class SectionController extends DogschoolController<Section> {

	public SectionController() {
		super("Section");
	}

	@GetMapping("/add")
	public Response add(@PathVariable String courseId) {
		return super.add(
				new UpdateObject[] { new UpdateObject<>("name", ""), new UpdateObject<>("course_id", courseId) });
	}

	@Override
	@PostMapping("/change")
	public Response change(@RequestBody UpdateObject<?> updateObject) {
		return super.change(updateObject);
	}

	@GetMapping("/{uuid}")
	public Section get(@PathVariable String courseId, @PathVariable String uuid) throws SQLException {
		return super.get("WHERE course_id = \"" + courseId + "\" AND id = \"" + uuid + "\"");
	}

	@Override
	@GetMapping("/all")
	public List<Section> getAll(@PathVariable String courseId) throws SQLException {
		return super.getAll("WHERE course_id = \"" + courseId + "\"");
	}

	@Override
	protected Function<ResultSet, Section> setResultSetFunction() {
		return resultSet -> {
			final Section section = new Section();
			try {
				section.setUuid(UUID.fromString(resultSet.getString("id")));
				section.setName(resultSet.getString("name"));
				section.setCourseID(UUID.fromString(resultSet.getString("course_id")));
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return section;
		};
	}

}
