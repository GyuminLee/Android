package org.tacademy.basic.gae;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tacademy.basic.gae.object.ContactInfo;
import org.tacademy.basic.gae.object.Employee;

@SuppressWarnings("serial")
public class TestgaeprojectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
//		String name = "name";
//		String email = "email";
//		PetDTO pet = new PetDTO("name");
//		
//		UserDTO user = new UserDTO(name, email, pet);

		Employee employee = new Employee("name");
		ContactInfo contactInfo = new ContactInfo("city");
		employee.setContactInfo(contactInfo);
		contactInfo.setEmployee(employee);
		
		PersistenceManagerFactory persistenceFactory = 
				JDOHelper.getPersistenceManagerFactory("transactions-optional");
		
		PersistenceManager manager = persistenceFactory.getPersistenceManager();
		
		manager.makePersistent(employee);
//		manager.makePersistent(user);
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
