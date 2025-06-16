package com.abbYs.model;

import com.abbYs.model.Enums.BedType;
import com.abbYs.model.Enums.RoomType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_rooms")
public class RoomModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="room_id")
	private int roomId;
	@Column(name="room_name")
	private String roomName;
	@Column(name="room_type")
	@Enumerated(EnumType.STRING)
	private RoomType roomType;
	@Column(name="bed_type")
	@Enumerated(EnumType.STRING)
	private BedType bedType;
	@Column(name="max_occupancy")
	private int maxOccupancy;
	@Column(name="price_per_night")
	private double pricePerNight;
	@Column(name="location")
	private String location;
	@Column(name="floor_number")
	private int floorNumber;
	@Column(name="bed_count")
	private int bedCount;
	@Column(name="has_wifi")
	private boolean hasWiFi;
	@Column(name="has_ac")
	private boolean hasAC;
	@Column(name="available")
	private boolean available;
	
	public RoomModel() {
	}
	public RoomModel(int roomId, String roomName, RoomType roomType, BedType bedType, int maxOccupancy,
			double pricePerNight, String location, int floorNumber, int bedCount, boolean hasWiFi, boolean hasAC,
			boolean available) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomType = roomType;
		this.bedType = bedType;
		this.maxOccupancy = maxOccupancy;
		this.pricePerNight = pricePerNight;
		this.location = location;
		this.floorNumber = floorNumber;
		this.bedCount = bedCount;
		this.hasWiFi = hasWiFi;
		this.hasAC = hasAC;
		this.available = available;
	}
	public RoomModel(String roomName, RoomType roomType, BedType bedType, int maxOccupancy,
			double pricePerNight, String location, int floorNumber, int bedCount, boolean hasWiFi, boolean hasAC,
			boolean available) {
		this.roomName = roomName;
		this.roomType = roomType;
		this.bedType = bedType;
		this.maxOccupancy = maxOccupancy;
		this.pricePerNight = pricePerNight;
		this.location = location;
		this.floorNumber = floorNumber;
		this.bedCount = bedCount;
		this.hasWiFi = hasWiFi;
		this.hasAC = hasAC;
		this.available = available;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public BedType getBedType() {
		return bedType;
	}
	public void setBedType(BedType bedType) {
		this.bedType = bedType;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public double getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public int getBedCount() {
		return bedCount;
	}
	public void setBedCount(int bedCount) {
		this.bedCount = bedCount;
	}
	public boolean isHasWiFi() {
		return hasWiFi;
	}
	public void setHasWiFi(boolean hasWiFi) {
		this.hasWiFi = hasWiFi;
	}
	public boolean isHasAC() {
		return hasAC;
	}
	public void setHasAC(boolean hasAC) {
		this.hasAC = hasAC;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	@Override
	public String toString() {
		return "RoomModel [roomId=" + roomId + ", roomName=" + roomName + ", roomType=" + roomType + ", bedType="
				+ bedType + ", maxOccupancy=" + maxOccupancy + ", pricePerNight=" + pricePerNight + ", location="
				+ location + ", floorNumber=" + floorNumber + ", bedCount=" + bedCount + ", hasWiFi=" + hasWiFi
				+ ", hasAC=" + hasAC + ", available=" + available + "]";
	}
	
}
