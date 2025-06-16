package com.abbYs.model.Enums;

public enum RoomType{
	Standard,Deluxe,Suite,Family;
	
	public static RoomType fromString(String value) {
	    for (RoomType type : RoomType.values()) {
	        if (type.name().equalsIgnoreCase(value)) {
	            return type;
	        }
	    }
	    throw new IllegalArgumentException("Invalid RoomType: " + value);
	}

}

