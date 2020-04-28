package ua.nure.aseev.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.db.entity.OwnRequest;
import ua.nure.aseev.SummaryTask4.db.entity.Request;
import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.db.entity.UserOrderBean;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

/**
 * @author B.Aseev
 *
 */
public class OrderDAO {

	private static final Logger LOG = Logger.getLogger(OrderDAO.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_INSERT_REQUEST = "INSERT INTO request (id_user , room_number, "
			+ " time_start, time_end , payed_status ,operation, sucsess) VALUES (?,?,?,?,?,?,?) ;";

	private static final String SQL_FIND_USER_REQUEST = "SELECT id,  room_number , number_of_places, "
			+ "class_apartment, price, time_start, time_end, sucsess ,payed_status,operation FROM request "
			+ "INNER  JOIN rooms r ON request.room_number = r.number where id_user = ? ; ";

	private static final String SQL_FIND_OWN_REQUEST_BY_USER_ID = "SELECT * FROM own_request WHERE id_user = ? ;";

	private static final String SQL_FIND_OWN_REQUEST_BY_ID = "SELECT r.id, first_name , last_name , number_of_places ,class_apartment ,"
			+ " time_start , time_end , payed_status from own_request r JOIN users u ON r.id_user = u.id WHERE r.id = ? ;";

	private static final String SQL_INSERT_OWN_REQUEST = "INSERT INTO own_request (id_user , number_of_places , "
			+ "class_apartment , time_start , time_end,operation, payed_status) VALUES (?,?,?,?,?,?,?) ; ";

	private static final String SQL_FIND_ALL_USER_ORDERS_WHERE_OPERETION_0 = "SELECT r.id , first_name , last_name , room_number ,"
			+ " number_of_places , class_apartment , time_start , time_end , price , payed_status ,operation "
			+ "FROM request r JOIN users u ON r.id_user = u.id  JOIN rooms on r.room_number = rooms.number WHERE operation = 0 ;";

	private static final String SQL_FIND_ALL_USER_OWN_ORDERS_WHERE_OPERETION_0 = "SELECT r.id, first_name , last_name , number_of_places , "
			+ "class_apartment , time_start , time_end ,  payed_status from own_request r JOIN users u ON r.id_user = u.id WHERE operation = 0 ;";

	private static final String SQL_UPDATE_SUCSESS_REQUEST_BY_ORDER_ID_TO_TRUE = " UPDATE request SET sucsess = true WHERE id = ? ;";

	private static final String SQL_UPDATE_SUCSESS_REQUEST_BY_ORDER_ID_TO_FALSE = " UPDATE request SET sucsess = false WHERE id = ? ;";

	private static final String SQL_FIND_ALL_USER_ORDERS = "SELECT r.id , first_name , last_name , room_number ,"
			+ " number_of_places , class_apartment , time_start ,sucsess , time_end , price , payed_status ,operation "
			+ "FROM request r JOIN users u ON r.id_user = u.id  JOIN rooms ON r.room_number = rooms.number  ;";

	private static final String SQL_FIND_ALL_USER_OWN_ORDERS = "SELECT * FROM own_request ; ";

	private static final String SQL_INSERT_TO_MANAGER_TABLE = "INSERT INTO request_from_manager (id_user , room_number, "
			+ " time_start , time_end,agree,wait) VALUES (?,?,?,?,?,?) ; ";

	public static final String SQL_FIND_REQUEST_FROM_MANAGER = "SELECT r.id ,id_user , room_number ,"
			+ " number_of_places , class_apartment , price ,wait,agree FROM "
			+ "request_from_manager r JOIN rooms on r.room_number = rooms.number WHERE id_user = ? ;";

	public static final String SQL_DELETE_QUICK_REQUEST = "DELETE FROM request WHERE id = ? ;";

	public static final String SQL_DELETE_ORDER_TIME_END = "DELETE FROM request WHERE id_user = ?  ; ";

	public static final String SQL_DELETE_REQUEST_FROM_MANAGER = "DELETE FROM request_from_manager WHERE id = ?";

	private static final String SQL_UPDATE_AGREE_STATUS_TO_TRUE = "UPDATE request_from_manager SET agree = true WHERE id = ?";

	private static final String SQL_UPDATE_OPERATION_OWN_REQUEST_TO_TRUE = "UPDATE own_request SET operation = true WHERE id = ? ;";

	private static final String SQL_UPDATE_OPERATION_OWN_REQUEST_TO_FALSE = "UPDATE own_request SET operation = false WHERE id = ? ;";

	private static final String SQL_UPDATE_OPERATION_REQUEST_TO_TRUE = "UPDATE request SET operation = true WHERE id = ? ;";

	private static final String SQL_UPDATE_OPERATION_REQUEST_TO_FALSE = "UPDATE request set operation = false WHERE id = ? ;";

	private static final String SQL_UPDATE_WAIT_TO_FALSE = "UPDATE request_from_manager SET wait = false WHERE id = ? ;";

	private static final String SQL_UPDATE_PAYED_STATUS_MANAGER_ORDER = "UPDATE own_request SET payed_status = True WHERE id = ?";

	private static final String SQL_UPDATE_ORDER_PAYED_STATUS = "UPDATE request SET payed_status = True WHERE id = ?";

	public static final String SQL_DELETE_OWN_ORDER_BY_ID = "DELETE FROM own_request WHERE id = ? ;";

	public static final String SQL_DELETE_OWN_ORDER_TIME_END = "DELETE FROM own_request WHERE id_user = ?  ; ";

	public static final String SQL_DELET_MANAGER_ORDER = "DELETE FROM request_from_manager WHERE id_user = ? ;";

	// //////////////////////////////////////////////////////////s
	// Methods
	// //////////////////////////////////////////////////////////
	/**
	 * Insert Order
	 * 
	 * @param id
	 * @param room
	 * @param start
	 * @param end
	 * @return boolean flag
	 * @throws DBException
	 */
	public boolean insertRequest(Long id, Room room, String start, String end) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_REQUEST);
			pstmt.setLong(1, id);
			pstmt.setInt(2, room.getNumber());
			pstmt.setString(3, start);
			pstmt.setString(4, end);
			pstmt.setBoolean(5, false);
			pstmt.setBoolean(6, false);
			pstmt.setBoolean(7, false);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_INSERT_REQUSEST, ex);
			throw new DBException(Messages.ERR_INSERT_REQUSEST, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Insert OwnOrder
	 * 
	 * @param id
	 * @param numberOfPlace
	 * @param classApartment
	 * @param start
	 * @param end
	 * @return boolean flag
	 * @throws DBException
	 */
	public boolean insertOwnRequest(Long id, String numberOfPlace, String classApartment, String start, String end)
			throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_OWN_REQUEST);
			pstmt.setLong(1, id);
			pstmt.setString(2, numberOfPlace);
			pstmt.setString(3, classApartment);
			pstmt.setString(4, start);
			pstmt.setString(5, end);
			pstmt.setBoolean(6, false);
			pstmt.setBoolean(7, false);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_INSERT_REQUSEST, ex);
			throw new DBException(Messages.ERR_INSERT_REQUSEST, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Find User order
	 * 
	 * @param id
	 * @return List Order`s
	 * @throws DBException
	 */
	public List<Request> findUserRequest(Long id) throws DBException {
		List<Request> req = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_REQUEST);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				req.add(extractRequest(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return req;
	}

	/**
	 * Find order user order
	 * 
	 * @param id
	 * @return List of Own Order
	 * @throws DBException
	 */
	public List<OwnRequest> findOwnUserRequest(Long id) throws DBException {
		List<OwnRequest> req = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_OWN_REQUEST_BY_USER_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				req.add(extractOwnRequest(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return req;

	}

	/**
	 * Find all users where operation equals 0
	 * 
	 * @return List of User order
	 * @throws DBException
	 */
	public List<UserOrderBean> findUserOrder() throws DBException {
		List<UserOrderBean> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER_ORDERS_WHERE_OPERETION_0);
			while (rs.next()) {
				list.add(extractUserOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Find Own order where operation equals 0
	 * 
	 * @return List of own Orders
	 * @throws DBException
	 */
	public List<UserOrderBean> findUserOwnOrder() throws DBException {
		List<UserOrderBean> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER_OWN_ORDERS_WHERE_OPERETION_0);
			while (rs.next()) {
				list.add(extractUserOwnOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;

	}

	/**
	 * Find all Usr Orders
	 * 
	 * @return List of Request
	 * @throws DBException
	 */
	public List<Request> findAllUserOrders() throws DBException {
		List<Request> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER_ORDERS);
			while (rs.next()) {
				list.add(extractRequestFromManager(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;

	}

	/**
	 * Find all user`s own orders
	 * 
	 * @return List of Own orders
	 * @throws DBException
	 */
	public List<OwnRequest> findAllUserOwnOrders() throws DBException {
		List<OwnRequest> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER_OWN_ORDERS);
			while (rs.next()) {
				list.add(extractOwnRequest(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return list;

	}

	/**
	 * Update order success to True
	 * 
	 * @param id
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRequestSucsessStatusToTrue(int id) throws DBException {

		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SUCSESS_REQUEST_BY_ORDER_ID_TO_TRUE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_ORDER_SUCSESS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_SUCSESS, ex);

		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Update order success
	 * 
	 * @param id
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRequestSucsessStatusToFalse(int id) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SUCSESS_REQUEST_BY_ORDER_ID_TO_FALSE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_ORDER_SUCSESS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ORDER_SUCSESS, ex);

		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Find own order by id
	 * 
	 * @param id
	 * @return user order
	 * @throws DBException
	 */
	public UserOrderBean findOneUserOwnOrder(Long id) throws DBException {
		UserOrderBean list = new UserOrderBean();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_OWN_REQUEST_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = extractUserOwnOrder(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return list;

	}

	/**
	 * Insert order to manager table
	 * 
	 * @param id
	 * @param room
	 * @param start
	 * @param end
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean insertRequestToManager(Long id, Room room, String start, String end) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_TO_MANAGER_TABLE);
			pstmt.setLong(1, id);
			pstmt.setInt(2, room.getNumber());
			pstmt.setString(3, start);
			pstmt.setString(4, end);
			pstmt.setBoolean(5, false);
			pstmt.setBoolean(6, true);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_INSER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Find Order from manager
	 * 
	 * @param id
	 * @return order
	 * @throws DBException
	 */
	public Request findOrderFromManager(Long id) throws DBException {
		Request req = new Request();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_REQUEST_FROM_MANAGER);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				req = extractRequestFromManager(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return req;

	}

	/**
	 * Update own order operation to true
	 * 
	 * @param id
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean upateOwnRequestOperationToTrue(Long id) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_OPERATION_OWN_REQUEST_TO_TRUE);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update own order operation to false
	 * 
	 * @param id
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean upateOwnRequestOperationToFalse(Long id) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_OPERATION_OWN_REQUEST_TO_FALSE);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update operation order to True
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean upateRequestOperationToTrue(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_OPERATION_REQUEST_TO_TRUE);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update operation order to False
	 * 
	 * @param id
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean upateRequestOperationToFalse(String id) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_OPERATION_REQUEST_TO_FALSE);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update wait status to False
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateWaitStatusToFalse(Long number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_WAIT_TO_FALSE);
			pstmt.setLong(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_WAIT_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_OPERATION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update order agree status to Tree
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRequestAgreeStatusToTrue(Long number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_AGREE_STATUS_TO_TRUE);
			pstmt.setLong(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_AGREE, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_AGREE, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete order from Manager
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean deleteRequestFromManager(Long number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_REQUEST_FROM_MANAGER);
			pstmt.setLong(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete order by timeEnd
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean deleteOrderTimeEnd(Long number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_ORDER_TIME_END);
			pstmt.setLong(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete own Order where time order end
	 * 
	 * @param number
	 * @return
	 * @throws DBException
	 */
	public boolean deleteOwnOrderTimeEnd(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_OWN_ORDER_TIME_END);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete quick Order
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean deleteQuickRequest(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_QUICK_REQUEST);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete order from Manager
	 * 
	 * @param number
	 * @return
	 * @throws DBException
	 */
	public boolean deleteOrderFromManagerTimeEnd(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELET_MANAGER_ORDER);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Update payed Status order
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updatePayedStatusOrder(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ORDER_PAYED_STATUS);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_PAYED_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_PAYED_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Update payed status manager Order
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updatePayedStatusManagerOrder(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_PAYED_STATUS_MANAGER_ORDER);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_PAYED_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_PAYED_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Delete own Order by ID
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean deleteOwnOrder(Long number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_OWN_ORDER_BY_ID);
			pstmt.setLong(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;

	}

	/**
	 * Extract Request Entity
	 * 
	 * @param rs
	 * @return Request
	 * @throws SQLException
	 */
	private Request extractRequest(ResultSet rs) throws SQLException {
		Request req = new Request();
		req.setId(rs.getLong(Fields.ENTITY_ID));
		req.setRoomNumber(rs.getInt(Fields.REQUEST_ROOM_NUMBER));
		req.setNumberOfPlaces(rs.getInt(Fields.REQUEST_NUMBER_OF_PLACES));
		req.setClassApartment(rs.getString(Fields.REQUEST_CLASS_APARTMENT));
		req.setPrice(rs.getInt(Fields.REQUEST_PRICE));
		req.setTimeStart(rs.getTimestamp(Fields.REQUEST_TIME_START));
		req.setTimeEnd(rs.getTimestamp(Fields.REQUEST_TIME_END));
		req.setSucsess(rs.getBoolean(Fields.REQUEST_SUCSESS));
		req.setOpearation(rs.getBoolean(Fields.REQUEST_OPERATION));
		req.setPayedStatus(rs.getBoolean(Fields.REQUEST_PAYED_STATUS));
		return req;
	}

	/**
	 * Extract own Order
	 * 
	 * @param rs
	 * @return User`s own order
	 * @throws SQLException
	 */
	private UserOrderBean extractUserOwnOrder(ResultSet rs) throws SQLException {
		UserOrderBean bean = new UserOrderBean();
		bean.setId(rs.getLong(Fields.ENTITY_ID));
		bean.setUserFirstName(rs.getString(Fields.BEAN_FIRST_NAME));
		bean.setUserLastName(rs.getString(Fields.BEAN_LAST_NAME));
		bean.setNumberOfPlaces(rs.getInt(Fields.BEAN_NUMBER_OF_PLACES));
		bean.setClassApartment(rs.getString(Fields.BEAN_CLASS_APARTMENT));
		bean.setTimeStart(rs.getTimestamp(Fields.BEAN_TIME_START));
		bean.setTimeEnd(rs.getTimestamp(Fields.BEAN_TIME_END));
		bean.setPayedStatus(rs.getBoolean(Fields.BEAN_PAYED_STATUS));

		return bean;
	}

	/**
	 * Extract user`s order
	 * 
	 * @param rs
	 * @return user`s order
	 * @throws SQLException
	 */
	private UserOrderBean extractUserOrder(ResultSet rs) throws SQLException {
		UserOrderBean bean = new UserOrderBean();
		bean.setId(rs.getLong(Fields.ENTITY_ID));
		bean.setUserFirstName(rs.getString(Fields.BEAN_FIRST_NAME));
		bean.setUserLastName(rs.getString(Fields.BEAN_LAST_NAME));
		bean.setRoomNumber(rs.getInt(Fields.BEAN_ROOM_NUMBER));
		bean.setNumberOfPlaces(rs.getInt(Fields.BEAN_NUMBER_OF_PLACES));
		bean.setClassApartment(rs.getString(Fields.BEAN_CLASS_APARTMENT));
		bean.setTimeStart(rs.getTimestamp(Fields.BEAN_TIME_START));
		bean.setTimeEnd(rs.getTimestamp(Fields.BEAN_TIME_END));
		bean.setOperation(rs.getBoolean(Fields.BEAN_OPERATION));
		bean.setPrice(rs.getInt(Fields.BEAN_PRICE));
		bean.setPayedStatus(rs.getBoolean(Fields.BEAN_PAYED_STATUS));
		return bean;
	}

	/**
	 * Extract own order
	 * 
	 * @param rs
	 * @return own Request
	 * @throws SQLException
	 */
	private OwnRequest extractOwnRequest(ResultSet rs) throws SQLException {
		OwnRequest req = new OwnRequest();
		req.setId(rs.getLong(Fields.ENTITY_ID));
		req.setIdUser(rs.getInt(Fields.REQUEST_USER_ID));
		req.setNumberOfPlaces(rs.getInt(Fields.REQUEST_NUMBER_OF_PLACES));
		req.setClassApartment(rs.getString(Fields.REQUEST_CLASS_APARTMENT));
		req.setTimeStart(rs.getTimestamp(Fields.REQUEST_TIME_START));
		req.setTimeEnd(rs.getTimestamp(Fields.REQUEST_TIME_END));
		req.setPayedStatus(rs.getBoolean(Fields.REQUEST_PAYED_STATUS));
		return req;
	}

	/**
	 * Extract order from manager
	 * 
	 * @param rs
	 * @return Request
	 * @throws SQLException
	 */
	private Request extractRequestFromManager(ResultSet rs) throws SQLException {
		Request req = new Request();
		req.setId(rs.getLong(Fields.ENTITY_ID));
		req.setIdUser(rs.getInt(Fields.REQUEST_USER_ID));
		req.setRoomNumber(rs.getInt(Fields.REQUEST_ROOM_NUMBER));
		req.setNumberOfPlaces(rs.getInt(Fields.REQUEST_NUMBER_OF_PLACES));
		req.setClassApartment(rs.getString(Fields.REQUEST_CLASS_APARTMENT));
		req.setPrice(rs.getInt(Fields.REQUEST_PRICE));
		req.setAgree(rs.getBoolean(Fields.REQUEST_AGREE));
		req.setWait(rs.getBoolean(Fields.REQUEST_WAIT));
		return req;
	}

}
