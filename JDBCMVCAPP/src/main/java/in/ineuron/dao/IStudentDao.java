package in.ineuron.dao;

import in.ineuron.dto.Student;

public interface IStudentDao {
	String save(Student student);// Creating a record
	Student findById(Integer sid);// Reading a record
	String updateById(Student student);// Updating a record
	String deleteById(Integer sid);// Deleting a record
}
