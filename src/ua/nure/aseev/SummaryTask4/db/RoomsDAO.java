package ua.nure.aseev.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.aseev.SummaryTask4.db.entity.Room;
import ua.nure.aseev.SummaryTask4.exception.DBException;
import ua.nure.aseev.SummaryTask4.exception.Messages;

/**
 * @author B.Aseev
 *
 */
public class RoomsDAO {

	private static final Logger LOG = Logger.getLogger(RoomsDAO.class);

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////
	private static final String SQL_FIND_ALL_ROOMS = "SELECT * FROM rooms ;";

	private static final String SQL_SORT_ROOMS_BY_VALUE = "SELECT * FROM rooms ORDER BY ";

	private static final String SQL_SORT_ROOMS_BY_STATUS = "SELECT * FROM rooms WHERE status = ? ; ";

	private static final String SQL_SORT_ROOMS_BY_CALSS = "SELECT * FROM rooms WHERE class_apartment = ? ;";

	private static final String SQL_CHEK_STATUS_ROOM_BY_NUMBER = "SELECT status FROM rooms WHERE number = ? ;";

	private static final String SQL_FIND_ROOM_BY_NUMBER = "SELECT * FROM rooms WHERE number = ? ;";

	private static final String SQL_FIND_FREE_ROOMS = "SELECT * FROM rooms WHERE status = 0 ; ";

	private static final String SQL_SELECTION_BY_NUMBER = "SELECT * FROM rooms WHERE number_of_places = ? ;";

	private static final String SQL_SELECTION_BY_CLASS = "SELECT * FROM rooms WHERE class_apartment = ? ;";

	private static final String SQL_SELECTION_BY_NUMBER_BY_CLASS = "SELECT * FROM rooms WHERE class_apartment = ? AND number_of_places = ? ;";

	private static final String SQL_UPDATE_ROOM_STATUS_TO_WAIT = "UPDATE rooms SET status = 2 WHERE number = ? ;";

	private static final String SQL_UPDATE_ROOM_STATUS_TO_BOOKED = "UPDATE rooms SET status = 1 WHERE number = ? ;";

	private static final String SQL_UPDATE_ROOM_STATUS_TO_FREE = "UPDATE rooms SET status = 0 WHERE number = ? ;";

	private static final String SQL_CHANGE_ROOM_STATUS_MANAGER = "UPDATE rooms SET status = ? WHERE number = ?";

	private static final String SQL_FIND_ROOM_BY_ID_ORDER = "SELECT room_number FROM request WHERE id = ? ;";

	// //////////////////////////////////////////////////////////
	// Methods
	// //////////////////////////////////////////////////////////

	/**
	 * Find rooms
	 * 
	 * @return List of Rooms
	 * @throws DBException
	 */
	public List<Room> findRooms() throws DBException {
		List<Room> roomList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_ROOMS);
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return roomList;
	}

	/**
	 * Find Free rooms
	 * 
	 * @return List of rooms
	 * @throws DBException
	 */
	public List<Room> findFreeRooms() throws DBException {
		List<Room> roomList = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_FREE_ROOMS);
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return roomList;
	}

	/**
	 * Sort room by value
	 * 
	 * @param sortValue
	 * @return List of rooms
	 * @throws DBException
	 */
	public List<Room> sortRoom(String sortValue) throws DBException {
		List<Room> roomList = new ArrayList<>();
		StringBuilder request = new StringBuilder();
		String sql = request.append(SQL_SORT_ROOMS_BY_VALUE).append(sortValue).toString();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_SORT_ROOM, ex);
			throw new DBException(Messages.ERR_CANNOT_SORT_ROOM, ex);
		} finally {
			DBManager.getInstance().close(con, stmt, rs);
		}
		return roomList;
	}

	/**
	 * Sort room by class
	 * 
	 * @param sortValue
	 * @return List of the rooms
	 * @throws DBException
	 */
	public List<Room> sortRoomClass(String sortValue) throws DBException {
		List<Room> roomList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SORT_ROOMS_BY_CALSS);
			pstmt.setString(1, sortValue);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_SORT_ROOM, ex);
			throw new DBException(Messages.ERR_CANNOT_SORT_ROOM, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return roomList;
	}

	/**
	 * Sort room by status
	 * 
	 * @param sortValue
	 * @return List of the Room
	 * @throws DBException
	 */
	public List<Room> sortRoomStatus(String sortValue) throws DBException {
		List<Room> roomList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SORT_ROOMS_BY_STATUS);
			pstmt.setString(1, sortValue);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();

		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_SORT_ROOM, ex);
			throw new DBException(Messages.ERR_CANNOT_SORT_ROOM, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return roomList;
	}

	/**
	 * Check status room by number
	 * 
	 * @param numberRoom
	 * @return Room
	 * @throws DBException
	 */
	public Room checkRoomStatus(String numberRoom) throws DBException {
		Room room = new Room();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_CHEK_STATUS_ROOM_BY_NUMBER);
			pstmt.setString(1, numberRoom);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				room.setStatus(rs.getInt(Fields.ROOM_STATUS));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_CHECK_ROOM_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_CHECK_ROOM_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return room;
	}

	/**
	 * Find Room by number
	 * 
	 * @param number
	 * @return Room
	 * @throws DBException
	 */
	public Room findRoomByNumber(String number) throws DBException {
		if (!number.isEmpty()) {
			Room room = new Room();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Connection con = null;
			try {
				con = DBManager.getInstance().getConnection();
				pstmt = con.prepareStatement(SQL_FIND_ROOM_BY_NUMBER);
				pstmt.setString(1, number);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					room = extractRoom(rs);
				}
				con.commit();
			} catch (SQLException ex) {
				DBManager.getInstance().rollback(con);
				LOG.error(Messages.ERR_CANNOT_FIND_ROOM_BY_NUMBER, ex);
				throw new DBException(Messages.ERR_CANNOT_FIND_ROOM_BY_NUMBER, ex);
			} finally {
				DBManager.getInstance().close(con, pstmt, rs);
			}
			return room;
		} else {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ROOMS, null);

		}
	}

	/**
	 * Selection rooms by number
	 * 
	 * @param number
	 * @return List of the Rooms
	 * @throws DBException
	 */
	public List<Room> selectionByNumber(String number) throws DBException {
		List<Room> roomList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECTION_BY_NUMBER);
			pstmt.setString(1, number);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return roomList;
	}

	/**
	 * Selection by class
	 * 
	 * @param classApp
	 * @return List of the Room
	 * @throws DBException
	 */
	public List<Room> selectionByClass(String classApp) throws DBException {
		List<Room> roomList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECTION_BY_CLASS);
			pstmt.setString(1, classApp);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return roomList;
	}

	/**
	 * Selection Rooms by number and by class
	 * 
	 * @param number
	 * @param classApp
	 * @return List of the Room
	 * @throws DBException
	 */
	public List<Room> selectionByNumberByClass(String number, String classApp) throws DBException {
		List<Room> roomList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_SELECTION_BY_NUMBER_BY_CLASS);
			pstmt.setString(1, classApp);
			pstmt.setString(2, number);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				roomList.add(extractRoom(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_MAKE_SELECTION, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}

		return roomList;
	}

	/**
	 * Update status rooom to wait
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRoomStatusToWait(String number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ROOM_STATUS_TO_WAIT);
			pstmt.setString(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update room status to Free
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRoomStatusToFree(int number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ROOM_STATUS_TO_FREE);
			pstmt.setInt(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Update Room to booked
	 * 
	 * @param number
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean updateRoomStatusToBooked(String number) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ROOM_STATUS_TO_BOOKED);
			pstmt.setString(1, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_ROOM_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ROOM_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Check room status
	 * 
	 * @param number
	 * @param status
	 * @return boolean value
	 * @throws DBException
	 */
	public boolean checkRoomStatusManager(int number, String status) throws DBException {
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_CHANGE_ROOM_STATUS_MANAGER);
			pstmt.setString(1, status);
			pstmt.setInt(2, number);
			pstmt.executeUpdate();
			con.commit();
			flag = true;
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_CHECK_ROOM_STATUS, ex);
			throw new DBException(Messages.ERR_CANNOT_CHECK_ROOM_STATUS, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return flag;
	}

	/**
	 * Find room by order ID
	 * 
	 * @param id
	 * @return int
	 * @throws DBException
	 */
	public int findRoomByIdOrder(int id) throws DBException {
		int result = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ROOM_BY_ID_ORDER);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("room_number");
			}
			con.commit();
		} catch (SQLException ex) {
			DBManager.getInstance().rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ORDER, ex);
		} finally {
			DBManager.getInstance().close(con, pstmt, rs);
		}
		return result;
	}

	/**
	 * Extract Room entity
	 * 
	 * @param rs
	 * @return Room
	 * @throws SQLException
	 */
	private Room extractRoom(ResultSet rs) throws SQLException {
		Room room = new Room();
		room.setNumber(rs.getInt(Fields.ROOM_NUMBER));
		room.setStatus(rs.getInt(Fields.ROOM_STATUS));
		room.setNumberOfPlace(rs.getInt(Fields.ROOM_NUMBER_OF_PLACES));
		room.setClassApartment(rs.getString(Fields.ROOM_CLASS_APARTMENT));
		room.setPrice(rs.getInt(Fields.ROOM_PRICE));
		return room;
	}

}
