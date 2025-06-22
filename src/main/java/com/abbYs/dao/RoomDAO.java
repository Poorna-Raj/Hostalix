package com.abbYs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abbYs.model.RoomModel;
import com.abbYs.model.Enums.BedType;
import com.abbYs.model.Enums.RoomType;
@Repository
public class RoomDAO {
	@Autowired
	private DataSource dataSource;
	public boolean addRoom(RoomModel room) {
		String sql = "INSERT INTO tbl_rooms (room_name, room_type, bed_type, max_occupancy, price_per_night, location, floor_number, bed_count, has_wifi, has_ac, available) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, room.getRoomName());
			stmt.setString(2, room.getRoomType().name());
			stmt.setString(3, room.getBedType().name());
			stmt.setInt(4, room.getMaxOccupancy());
			stmt.setDouble(5, room.getPricePerNight());
			stmt.setString(6, room.getLocation());
			stmt.setInt(7, room.getFloorNumber());
			stmt.setInt(8, room.getBedCount());
			stmt.setBoolean(9, room.isHasWiFi());
			stmt.setBoolean(10, room.isHasAC());
			stmt.setBoolean(11, room.isAvailable());
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				return true; // Room added successfully
			} else {
				throw new RuntimeException("Insert failed: no rows affected"); // Room not added
			}
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error adding room: " + sqlex.getMessage(), sqlex);
		}
	}
	
	public boolean updateRoom(RoomModel room) {
		String sql = "UPDATE tbl_rooms SET room_name = ?, room_type = ?, bed_type = ?, max_occupancy = ?, price_per_night = ?, location = ?, floor_number = ?, bed_count = ?, has_wifi = ?, has_ac = ?, available = ? WHERE room_id = ?";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, room.getRoomName());
			stmt.setString(2, room.getRoomType().name());
			stmt.setString(3, room.getBedType().name());
			stmt.setInt(4, room.getMaxOccupancy());
			stmt.setDouble(5, room.getPricePerNight());
			stmt.setString(6, room.getLocation());
			stmt.setInt(7, room.getFloorNumber());
			stmt.setInt(8, room.getBedCount());
			stmt.setBoolean(9, room.isHasWiFi());
			stmt.setBoolean(10, room.isHasAC());
			stmt.setBoolean(11, room.isAvailable());
			stmt.setInt(12, room.getRoomId());
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				return true; 
			} else {
				throw new RuntimeException("Update failed: no rows affected"); 
			}
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error updating room: " + sqlex.getMessage(), sqlex);
		}
	}
	
	public boolean deleteRoom(int room_id) {
		String sql = "DELETE FROM tbl_rooms WHERE room_id = ?";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, room_id);
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				return true; // Room deleted successfully
			} else {
				throw new RuntimeException("Delete failed: no rows affected"); // Room not deleted
			}
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error deleting room: " + sqlex.getMessage(), sqlex);
		}
	}
	
	public List<RoomModel> getAllRooms() {
		List<RoomModel> rooms = new ArrayList<>();
		String sql = "SELECT * FROM tbl_rooms";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while(rs.next()) {
				RoomModel room = new RoomModel(
					rs.getInt("room_id"),
					rs.getString("room_name"),
					RoomType.fromString(rs.getString("room_type")),
					BedType.fromString(rs.getString("bed_type")),
					rs.getInt("max_occupancy"),
					rs.getDouble("price_per_night"),
					rs.getString("location"),
					rs.getInt("floor_number"),
					rs.getInt("bed_count"),
					rs.getBoolean("has_wifi"),
					rs.getBoolean("has_ac"),
					rs.getBoolean("available")
				);
				rooms.add(room);
			}
			return rooms; // Return the list of rooms
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error fetching rooms: " + sqlex.getMessage(), sqlex);
		}
//		return null;
	}
	
	public List<RoomModel> filterRooms(String roomName,Boolean available,Integer order){
		List<RoomModel> rooms = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM tbl_rooms WHERE 1=1");
		List<Object> parameters = new ArrayList<>();

	    // Build SQL with conditional WHERE
	    if (roomName != null && !roomName.isEmpty()) {
	        sql.append(" AND room_name LIKE ?");
	        parameters.add("%" + roomName + "%");
	    }

	    if (available != null) {
	        sql.append(" AND available = ?");
	        parameters.add(available);
	    }

	    if (order != null) {
	        if (order == 1) {
	            sql.append(" ORDER BY price_per_night ASC");
	        } else if (order == 2) {
	            sql.append(" ORDER BY price_per_night DESC");
	        }
	    }
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
			for (int i = 0; i < parameters.size(); i++) {
	            stmt.setObject(i + 1, parameters.get(i));
	        }
			try(ResultSet rs = stmt.executeQuery()) {
				while(rs.next()) {
					RoomModel room = new RoomModel(
						rs.getInt("room_id"),
						rs.getString("room_name"),
						RoomType.fromString(rs.getString("room_type")),
						BedType.fromString(rs.getString("bed_type")),
						rs.getInt("max_occupancy"),
						rs.getDouble("price_per_night"),
						rs.getString("location"),
						rs.getInt("floor_number"),
						rs.getInt("bed_count"),
						rs.getBoolean("has_wifi"),
						rs.getBoolean("has_ac"),
						rs.getBoolean("available")
					);
					rooms.add(room);
				}
			}
			return rooms; // Return the filtered list of rooms
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error filtering rooms: " + sqlex.getMessage(), sqlex);
		}
	}
	
	public RoomModel getRoomById(int roomId) {
		String sql = "SELECT * FROM tbl_rooms WHERE room_id = ?";
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, roomId);
			try(ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					return new RoomModel(
						rs.getInt("room_id"),
						rs.getString("room_name"),
						RoomType.fromString(rs.getString("room_type")),
						BedType.fromString(rs.getString("bed_type")),
						rs.getInt("max_occupancy"),
						rs.getDouble("price_per_night"),
						rs.getString("location"),
						rs.getInt("floor_number"),
						rs.getInt("bed_count"),
						rs.getBoolean("has_wifi"),
						rs.getBoolean("has_ac"),
						rs.getBoolean("available")
					);
				} else {
					return null; // No room found with the given ID
				}
			}
		}
		catch(SQLException sqlex) {
			throw new RuntimeException("Error fetching room by ID: " + sqlex.getMessage(), sqlex);
		}
	}
}
