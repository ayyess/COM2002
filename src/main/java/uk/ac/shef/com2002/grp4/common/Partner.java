package uk.ac.shef.com2002.grp4.common;

public enum Partner {
	DENTIST("Dentist"),
	HYGIENIST("Hygienist");

	// Member to hold the name
	private String name;

	// constructor to set the string
	Partner(String name) {
		this.name = name;
	}

	// the toString just returns the given name
	@Override
	public String toString() {
		return name;
	}
	
	public static Partner valueOfIngnoreCase(String p) {
		return valueOf(p.toUpperCase());
	}
	
}
