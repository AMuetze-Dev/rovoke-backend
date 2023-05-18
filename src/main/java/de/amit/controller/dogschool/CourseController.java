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
import de.amit.model.dogschool.Course;

@RestController
@RequestMapping("/dogschool/course")
public class CourseController extends DogschoolController<Course> {

	public CourseController() {
		super("Course");
	}

	@GetMapping("/add")
	public Response add() {
		return super.add(new UpdateObject<>("name", ""));
	}

	@Override
	@PostMapping("/change")
	public Response change(@RequestBody UpdateObject<?> updateObject) {
		return super.change(updateObject);
	}

	@Override
	@GetMapping("/{uuid}")
	public Course get(@PathVariable String uuid) throws SQLException {
		return super.get("WHERE id = " + uuid);
	}

	@GetMapping("/all")
	public List<Course> getAll() throws SQLException {
		return super.getAll("");
	}

	@Override
	protected Function<ResultSet, Course> setResultSetFunction() {
		return resultSet -> {
			final Course course = new Course();
			try {
				course.setUuid(UUID.fromString(resultSet.getString("id")));
				course.setName(resultSet.getString("name"));
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return course;
		};
	}

}
