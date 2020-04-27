package ua.nure.aseev.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.db.entity.User;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

/**
 * @author B.Aseev
 *
 */
public class UserDAO {

	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////
	private static final String SQL_CREATE_USER = "INSERT INTO users (id , login , pass, first_name , last_name ,mail, role) VALUES (DEFAULT,?, ?, ? , ? ,?, ?);";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=? ;";

	private static final String SQL_GET_USER_LOGIN = "SELECT * FROM users ;";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id= ? ;";

	private static final String SQL_FIND_USER_ID_BY_ORDER = "SELECT id_user FROM request WHERE id = ? ;";

	private static final String SQL_FIND_USER_ID_BY_OWN_ORDER = "SELECT id_user FROM own_request WHERE id = ? ;";

	// //////////////////////////////////////////////////////////s
	// Methods to insert User
	// //////////////////////////////////////////////////////////
	/**
	 * Insert User in the table.
	 * 
	 * @return result of this method.
	 */

	private final DBManager dbManager;

	public UserDAO() throws DBException {
		this.dbManager = DBManager.getInstance();
	}

	public UserDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * Insert user
	 * 
	 * @param user
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean insertUser(User user) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_USER);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getSecondName());
			pstmt.setString(5, user.getMail());
			pstmt.setInt(6, user.getRole());
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);

		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Find user by login
	 * 
	 * @param login
	 * @return User
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return user;

	}

	/**
	 * Find user`s login and mail
	 * 
	 * @return List of the Users
	 * @throws DBException
	 */
	public List<User> getUserLoginAndMail() throws DBException {
		List<User> userList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_LOGIN);
			while (rs.next()) {
				userList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USER_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_GET_USER_LOGIN, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return userList;

	}

	/**
	 * Find User by ID
	 * 
	 * @param id
	 * @return User
	 * @throws DBException
	 */
	public User findUserById(Long id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_ID, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return user;

	}

	/**
	 * Find user by own order
	 * 
	 * @param id
	 * @return Long
	 * @throws DBException
	 */
	public Long findIdUserByOwnRequest(Long id) throws DBException {
		Long result = (long) 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_ID_BY_OWN_ORDER);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong("id_user");
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return result;
	}

	/**
	 * Find User by order
	 * 
	 * @param id
	 * @return Long
	 * @throws DBException
	 */
	public Long findIdUserByRequest(int id) throws DBException {
		Long result = (long) 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_ID_BY_ORDER);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong("id_user");
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return result;
	}

	/**
	 * Extract User entity
	 * 
	 * @param rs
	 * @return User
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setMail(rs.getString(Fields.USER_MAIL));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setSecondName(rs.getString(Fields.USER_LAST_NAME));
		user.setRole(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

	/**
	 * Getter DBManager
	 * 
	 * @return DBManager
	 */
	public DBManager getDbManager() {
		return dbManager;
	}

}
