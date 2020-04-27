package ua.nure.aseev.SummaryTask4.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

public class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	// ////////////////////////////////////////////////////////
	// singleton
	// ////////////////////////////////////////////////////////

	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env/");
			ds = (DataSource) envContext.lookup("jdbc/final");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DataSource ds;

	/**
	 * Returns a DB connection from the Pool Connections. Before using this method
	 * you must configure the Date Source and the Connections Pool in your
	 * WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return DB connection.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Closes a connection.
	 * 
	 * @param con Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * close Statment
	 * 
	 * @param stmt
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * close ResultSet
	 * 
	 * @param rs
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Close Connection,Statment,ResultSet
	 * 
	 * @param con
	 * @param stmt
	 * @param rs
	 */
	void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con Connection to be rollbacked.
	 */
	public void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				LOG.error(Messages.ERR_CANNOT_MAKE_ROLLBACK, e);

			}
		}
	}

}
