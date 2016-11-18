package uk.ac.shef.com2002.grp4;

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
}
