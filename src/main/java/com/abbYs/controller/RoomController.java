package com.abbYs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abbYs.dao.RoomDAO;
import com.abbYs.model.RoomModel;

@RestController
public class RoomController {
	@Autowired
	private RoomDAO roomDAO;
	
	@PostMapping("/rooms")
	public ResponseEntity<Map<String, Object>> addRoom(@RequestBody RoomModel room) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			boolean isAdded = roomDAO.addRoom(room);
			if (isAdded) {
				response.put("success", true);
				response.put("message", "Room added successfully");
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "Failed to add room");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error adding room: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/rooms")
	public ResponseEntity<Map<String,Object>>updateRoom(@RequestBody RoomModel room){
		Map<String,Object> response = new HashMap<>();
		try {
			boolean isUpdated = roomDAO.updateRoom(room);
			if(isUpdated) {
				response.put("success", true);
				response.put("message", "Room updated successfully");
				return ResponseEntity.ok(response);
			}
			else {
				response.put("success", false);
				response.put("message", "Failed to update room");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		}
		catch(Exception ex) {
			response.put("success", false);
			response.put("message", "Error updating room: " + ex.getMessage());
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<Map<String,Object>> deleteRoom(@PathVariable int id){
		Map<String,Object> response = new HashMap<>();
		try {
			boolean isDeleted = roomDAO.deleteRoom(id);
			if(isDeleted) {
				response.put("success", true);
				response.put("message", "Room deleted successfully");
				return ResponseEntity.ok(response);
			}
			else {
				response.put("success", false);
				response.put("message", "Failed to delete room");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		}
		catch(Exception ex) {
			response.put("success", false);
			response.put("message", "Error deleting room: " + ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/rooms/filter")
	public ResponseEntity<Map<String,Object>> getFilterRoom(
	@RequestParam(required = false) String name,
    @RequestParam(required = false) Boolean available,
    @RequestParam(required = false) Integer orderBy) {
		Map<String,Object> response = new HashMap<>();
		List<RoomModel> rooms = new ArrayList<>();
		try {
			rooms = roomDAO.filterRooms(name, available, orderBy);
			if(rooms != null && !rooms.isEmpty()) {
				response.put("success", true);
				response.put("message", "Filtered rooms fetched successfully");
				response.put("data", rooms);
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "No rooms found with the given criteria");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}
		catch(Exception ex) {
			response.put("success", false);
			response.put("message", "Error fetching filtered rooms: " + ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/rooms/{id}")
	public ResponseEntity<Map<String,Object>> getRoomById(@PathVariable int id){
		Map<String,Object> response = new HashMap<>();
		try {
			RoomModel room = roomDAO.getRoomById(id);
			if(room != null) {
				response.put("success", true);
				response.put("message", "Room fetched successfully");
				response.put("data", room);
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "Room not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}
		catch(Exception ex) {
			response.put("success", false);
			response.put("message", "Error fetching room: " + ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/rooms")
	public ResponseEntity<Map<String,Object>>getAllRooms(){
		Map<String,Object> response = new HashMap<>();
		List<RoomModel> rooms = new ArrayList<>();
		try {
			rooms = roomDAO.getAllRooms();
			if(rooms != null && !rooms.isEmpty()) {
				response.put("success", true);
				response.put("message", "Rooms fetched successfully");
				response.put("data", rooms);
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "No rooms found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}
		catch(Exception ex) {
			response.put("success", false);
			response.put("message", "Error fetching rooms: " + ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}