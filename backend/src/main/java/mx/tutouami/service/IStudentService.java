package mx.tutouami.service;

import java.util.List;

import mx.tutouami.model.dto.StudentDTO;

public interface IStudentService {
	
	public List<StudentDTO> findAll();
	public StudentDTO findById(Long id);
}
