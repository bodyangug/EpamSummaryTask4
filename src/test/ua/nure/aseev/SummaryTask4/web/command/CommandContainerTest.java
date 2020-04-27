package test.ua.nure.aseev.SummaryTask4.web.command;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.mockito.Mockito;

import ua.nure.aseev.SummaryTask4.web.command.CommandContainer;
import ua.nure.aseev.SummaryTask4.web.command.LoginCommand;
import ua.nure.aseev.SummaryTask4.web.command.NoCommand;

public class CommandContainerTest extends Mockito {

	@Test
	public void doGetTest() {
		assertSame(LoginCommand.class, CommandContainer.get("login").getClass());
	}

	@Test
	public void noSuchCommandTest() {
		assertSame(NoCommand.class, CommandContainer.get("not").getClass());
	}

	@Test
	public void noCommandTest() {
		assertSame(NoCommand.class, CommandContainer.get(null).getClass());
	}
}
