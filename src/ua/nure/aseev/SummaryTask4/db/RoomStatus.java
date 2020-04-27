package ua.nure.aseev.SummaryTask4.db;

import ua.nure.aseev.SummaryTask4.db.entity.Room;

/**
 * Status entity.
 * 
 * @author B.Aseev
 * 
 */

public enum RoomStatus {

	FREE, BOOKED, WAIT, LOCKED, UNABLE;

	public static RoomStatus getStatus(Room room) {
		int statusId = room.getStatus();
		return RoomStatus.values()[statusId];
	}

	public static RoomStatus getStatus(int statusId) {
		return RoomStatus.values()[statusId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
