package test.ua.nure.aseev.SummaryTask4.web.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.Path;
import ua.nure.aseev.SummaryTask4.db.UserDAO;
import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.RegisterCommand;

public class RegisterCommandTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		UserDAO userDao = mock(UserDAO.class);

		String forward = Path.PAGE_LOGIN;

		List<User> list = new ArrayList<>();

		User user = mock(User.class);
		user.setId(1l);
		user.setFirstName("Some Name");
		user.setSecondName("SomeSname");
		user.setMail("email");
		user.setLogin("login123");
		list.add(user);

		when(request.getParameter("f_name")).thenReturn("f_name");
		when(request.getParameter("s_name")).thenReturn("s_name");
		when(request.getParameter("login")).thenReturn("login");
		when(request.getParameter("mail")).thenReturn("mail");
		when(userDao.getUserLoginAndMail()).thenReturn(list);
		when(request.getParameter("password")).thenReturn("passs");
		when(userDao.insertUser(user)).thenReturn(true);

		RegisterCommand register = new RegisterCommand(userDao);
		register.execute(request, response);

		verify(request, atLeast(1)).getParameter("f_name");
		verify(request, atLeast(1)).getParameter("s_name");
		verify(request, atLeast(1)).getParameter("login");
		verify(request, atLeast(1)).getParameter("mail");
		assertEquals(forward, register.execute(request, response));
	}
}
