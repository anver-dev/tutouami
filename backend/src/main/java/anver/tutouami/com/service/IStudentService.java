package anver.tutouami.com.service;

import java.util.List;

import anver.tutouami.com.model.dto.StatusDTO;
import anver.tutouami.com.model.dto.StudentDTO;

public interface IStudentService {
	
	public List<StudentDTO> findAll();
	public StudentDTO findById(Long id);
	public StudentDTO create(StudentDTO student);
	public StudentDTO updateStatus(Long id, StatusDTO status);
}
