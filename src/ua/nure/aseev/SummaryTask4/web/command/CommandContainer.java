package ua.nure.aseev.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.web.command.client.ListRoomCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.LookOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.MakeOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.MakeOwnOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.OfferCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.PayCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.PayManagerCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.PersonalOfficeCommand;
import ua.nure.aseev.SummaryTask4.web.command.client.SorterCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.ChangeRoomStatus;
import ua.nure.aseev.SummaryTask4.web.command.manager.CheckTimeCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.ChooseThatRoomCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.DeclineOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.ListPersonalOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.ListQuickOrdersCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.SelectionCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.ShowOrderCommand;
import ua.nure.aseev.SummaryTask4.web.command.manager.UpdateSucsessCommand;

public class CommandContainer {

	private CommandContainer() {
		// no op
	}

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<>();

	static {
		// common commands
		commands.put("register", new RegisterCommand());
		commands.put("login", new LoginCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("errorPage", new ErrorPageCommand());
		commands.put("updateLocale", new UpdateLocaleCommand());

		// client commands
		commands.put("offer", new OfferCommand());
		commands.put("lookOrder", new LookOrderCommand());
		commands.put("personalOffice", new PersonalOfficeCommand());
		commands.put("sort", new SorterCommand());
		commands.put("listMenu", new ListRoomCommand());
		commands.put("makeOrder", new MakeOrderCommand());
		commands.put("pay", new PayCommand());
		commands.put("makeOwnOrder", new MakeOwnOrderCommand());
		commands.put("payManager", new PayManagerCommand());

		// manager commands
		commands.put("changeStatusRoom", new ChangeRoomStatus());
		commands.put("chooseThatRoom", new ChooseThatRoomCommand());
		commands.put("selection", new SelectionCommand());
		commands.put("showOrder", new ShowOrderCommand());
		commands.put("updateSucsess", new UpdateSucsessCommand());
		commands.put("quickOrders", new ListQuickOrdersCommand());
		commands.put("personalOrders", new ListPersonalOrderCommand());
		commands.put("checkTime", new CheckTimeCommand());
		commands.put("declineOrder", new DeclineOrderCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}
}
