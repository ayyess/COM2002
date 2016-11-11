package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Address implements Saveable{

	private Optional<Long> id;
	private int houseNumber;
	private String street;
	private String district;
	private String city;
	private String postcode;

	//TODO add creation from db?

	public Address(int houseNumber, String street, String district, String city, String postcode) {
		this(Optional.empty(),houseNumber,street,district,city,postcode);
	}
	
	public Address(Optional<Long> id,int houseNumber, String street, String district, String city, String postcode) {
		this.id = id;
		this.houseNumber = houseNumber;
		this.street = street;
		this.district = district;
		this.city = city;
		this.postcode = postcode;
	}
	
	public String toString() {
		return houseNumber + " " + street + ", " + district + ", " + city + ", " + postcode;
	}

	public static Address getByID(int id) {
		return ConnectionManager.withStatement("SELECT * FROM addresses WHERE id=?",(stmt)->{
			stmt.setInt(1,id);
			ResultSet res = stmt.executeQuery();
			return new Address(Optional.of(res.getLong(1)),res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
		});
	}

	public static List<Address> findByPostcode(String postcode) {
		List<Address> addresses = new ArrayList<>();
		ConnectionManager.withStatement("SELECT * FROM addresses WHERE postcode=?",(stmt)->{
			stmt.setString(1,postcode);
			ResultSet res = stmt.executeQuery();
			while(res.next()){
				addresses.add(new Address(Optional.of(res.getLong(1)),res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6)));
			}
			return null;
		});
		return addresses;

	}

	public static int getID(int houseNumber, String postcode) {
		return ConnectionManager.withStatement("SELECT id FROM addresses WHERE house_number=?, postcode=?",(stmt)->{
			stmt.setInt(1,houseNumber);
			stmt.setString(2,postcode);
			return stmt.executeQuery().getInt(1);
		});
	}

	@Override
	public boolean isInDb() {
		return id.isPresent();
	}

	@Override
	public void update() {
		ConnectionManager.withStatement("UPDATE addresses SET house_number=?, street=?, district=?, city=?, postcode=? WHERE id=?",(stmt)->{
			stmt.setInt(1, houseNumber);
			stmt.setString(2, street);
			stmt.setString(3, district);
			stmt.setString(4, city);
			stmt.setString(5, postcode);
			stmt.setLong(6, id.get());
			stmt.executeUpdate();
			return null;
		});
	}

	@Override
	public void insert() {
		ConnectionManager.withStatement("INSERT INTO addresses VALUES (DEFAULT,?,?,?,?,?)",(stmt)->{
			stmt.setInt(1,houseNumber);
			stmt.setString(2, street);
			stmt.setString(3, district);
			stmt.setString(4, city);
			stmt.setString(5, postcode);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			id = Optional.of(generatedKeys.getLong(1));
			return null;
		});
	}

	public Optional<Long> getId() {
		return id;
	}
}
