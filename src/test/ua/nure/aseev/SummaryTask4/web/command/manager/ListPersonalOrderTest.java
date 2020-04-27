package test.ua.nure.aseev.SummaryTask4.web.command.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.db.OrderDAO;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.AppException;
import ua.nure.aseev.SummaryTask4.web.command.manager.ListPersonalOrderCommand;

public class ListPersonalOrderTest extends Mockito {

	@Test
	public void testExecute() throws IOException, ServletException, AppException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		OrderDAO orderDao = mock(OrderDAO.class);

		List<UserOrderBean> list = new ArrayList<>();
		UserOrderBean order = new UserOrderBean();
		order.setId(1l);
		list.add(order);

		when(orderDao.findUserOwnOrder()).thenReturn(list);

		ListPersonalOrderCommand listPersonal = new ListPersonalOrderCommand(orderDao);
		listPersonal.execute(request, response);

		verify(request, atLeast(1)).setAttribute("orderOwnList", list);
	}
}
