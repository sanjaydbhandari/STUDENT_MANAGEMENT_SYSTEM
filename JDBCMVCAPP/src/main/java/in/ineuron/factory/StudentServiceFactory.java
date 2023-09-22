package in.ineuron.factory;

import in.ineuron.service.IStudentService;
import in.ineuron.service.StudentServiceImpl;

public class StudentServiceFactory {

	private static IStudentService studentServiceImpl;

	private StudentServiceFactory() {
		// TODO Auto-generated constructor stub
	}

	public static IStudentService getStudentService() {
		if (studentServiceImpl == null)
			studentServiceImpl = new StudentServiceImpl();

		return studentServiceImpl;
	}
}
