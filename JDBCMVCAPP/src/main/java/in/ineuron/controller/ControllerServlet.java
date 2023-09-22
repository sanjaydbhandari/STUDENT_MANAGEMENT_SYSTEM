package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ineuron.dto.Student;
import in.ineuron.factory.StudentServiceFactory;
import in.ineuron.service.IStudentService;

@WebServlet("/controller/*")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);

		IStudentService stdService = StudentServiceFactory.getStudentService();

		RequestDispatcher rd = null;
		if (requestURI.endsWith("layout")) {
			rd = request.getRequestDispatcher("../layout.html");
			rd.forward(request, response);
		}

		if (requestURI.endsWith("addform")) {
			String sname = request.getParameter("sname");
			String sage = request.getParameter("sage");
			String saddress = request.getParameter("saddr");

			Student student = new Student();
			student.setSname(sname);
			student.setSage(Integer.parseInt(sage));
			student.setSaddr(saddress);

			String status = stdService.save(student);
			System.out.println(status);

			if (status.equals("success")) {
				rd = request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			} else {
				rd = request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			}

		}
		if (requestURI.endsWith("searchform")) {
			String sid = request.getParameter("sid");
			Student student = stdService.findById(Integer.parseInt(sid));

			if (student != null) {
				response.setContentType("text/html");

				PrintWriter out = response.getWriter();
				out.println("<html><head><title>STUDENT DATA</title></head>");
				out.println("<body bgcolor='lightblue'>");
				out.println("<br/><br/><br/>");
				out.println("<table align='center' border='1'>");
				out.println("<tr>");
				out.println("<th>SID</th>");
				out.println("<th>SNAME</th>");
				out.println("<th>SAGE</th>");
				out.println("<th>SADDRESS</th>");
				out.println("</tr>");

				out.println("<tr>");
				out.println("<td>" + student.getSid() + "</td>");
				out.println("<td>" + student.getSname() + "</td>");
				out.println("<td>" + student.getSage() + "</td>");
				out.println("<td>" + student.getSaddr() + "</td>");
				out.println("</tr>");

				out.println("</table>");
				out.println("</body>");
				out.println("</html>");

				out.close();

			} else {
				rd = request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
		if (requestURI.endsWith("deleteform")) {
			String sid = request.getParameter("sid");
			String status = stdService.deleteById(Integer.parseInt(sid));

			if (status.equals("success")) {
				rd = request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			} else if (status.equals("failure")) {
				rd = request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			} else {
				rd = request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}
		if (requestURI.endsWith("editform")) {
			String sid = request.getParameter("sid");
			Student student = stdService.findById(Integer.parseInt(sid));
			if (student != null) {
				response.setContentType("text/html");

				// display editpage using html
				PrintWriter out = response.getWriter();
				out.println("<html><head><title>OUTPUT</title></head>");
				out.println("<body bgcolor='lightblue'>");
				out.println("<br/><br/><br/>");
				out.println("<form method='post' action='./update'>");
				out.println("<table align='center'>");
				out.println("<tr><th>ID</th><td>" + student.getSid() + "</td></tr>");
				out.println("<input type='hidden' name='sid' value='" + student.getSid() + "'/>");
				out.println("<tr><th>NAME</th><td><input type='text' name='sname' value='" + student.getSname()
						+ "'/></td></tr>");
				out.println("<tr><th>AGE</th><td><input type='text' name='sage' value='" + student.getSage()
						+ "'/></td></tr>");
				out.println("<tr><th>ADDRESS</th><td><input type='text' name='saddr' value='" + student.getSaddr()
						+ "'/></td></tr>");
				out.println("<tr><td></td><td><input type='submit' value='update'/></td></tr>");
				out.println("</table>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
				out.close();

			} else {
				rd = request.getRequestDispatcher("../notfound.html");
				rd.forward(request, response);
			}
		}

		if (requestURI.endsWith("update")) {

			String sid = request.getParameter("sid");
			String sname = request.getParameter("sname");
			String sage = request.getParameter("sage");
			String saddr = request.getParameter("saddr");

			Student student = new Student();
			student.setSid(Integer.parseInt(sid));
			student.setSname(sname);
			student.setSage(Integer.parseInt(sage));
			student.setSaddr(saddr);
			
			String status = stdService.updateById(student);
			if (status.equals("success")) {
				rd = request.getRequestDispatcher("../success.html");
				rd.forward(request, response);
			} else if (status.equals("failure")) {
				rd = request.getRequestDispatcher("../failure.html");
				rd.forward(request, response);
			} 
		}
	}
}
