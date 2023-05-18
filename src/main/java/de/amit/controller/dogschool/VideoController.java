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
import de.amit.model.dogschool.Video;

@RestController
@RequestMapping("/dogschool/{sectionId}/video")
public class VideoController extends DogschoolController<Video> {

	public VideoController() {
		super("Video");
	}

	@GetMapping("/add")
	public Response add(@PathVariable String sectionId) {
		return super.add(
				new UpdateObject[] { new UpdateObject<>("name", ""), new UpdateObject<>("section_id", sectionId) });
	}

	@Override
	@PostMapping("/change")
	public Response change(@RequestBody UpdateObject<?> updateObject) {
		return super.change(updateObject);
	}

	@GetMapping("/{uuid}")
	public Video get(@PathVariable String sectionId, @PathVariable String uuid) throws SQLException {
		return super.get("WHERE section_id = \"" + sectionId + "\" AND id = \"" + uuid + "\"");
	}

	@Override
	@GetMapping("/all")
	public List<Video> getAll(@PathVariable String courseId) throws SQLException {
		return super.getAll("WHERE course_id = \"" + courseId + "\"");
	}

	@Override
	protected Function<ResultSet, Video> setResultSetFunction() {
		return resultSet -> {
			final Video video = new Video();
			try {
				video.setUuid(UUID.fromString(resultSet.getString("id")));
				video.setName(resultSet.getString("name"));
				video.setSectionID(UUID.fromString(resultSet.getString("section_id")));
			} catch (final SQLException e) {
				LoggerService.severe(Arrays.toString(e.getStackTrace()));
			}
			return video;
		};
	}

}
