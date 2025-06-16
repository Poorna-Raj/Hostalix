package com.abbYs.model.Enums;

public enum BedType {
	Single, Double, Queen, King, Twin;

	public static BedType fromString(String bedType) {
		for (BedType type : BedType.values()) {
			if (type.name().equalsIgnoreCase(bedType)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown bed type: " + bedType);
	}
}