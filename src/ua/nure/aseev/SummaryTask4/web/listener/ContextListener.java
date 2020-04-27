package ua.nure.aseev.SummaryTask4.web.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context listener.
 * 
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// no op
		log("Servlet context destruction finished");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initI18N(servletContext);
		log("Servlet context initialization finished");
	}

	/**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		LOG.debug("I18N subsystem initialization started");

		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			LOG.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}

			LOG.debug("Application attribute set: locales --> " + locales);
			servletContext.setAttribute("locales", locales);
		}

		LOG.debug("I18N subsystem initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}
		log("Log4J initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.aseev.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}
