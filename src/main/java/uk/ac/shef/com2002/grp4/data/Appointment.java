package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private String practitioner;

	public Appointment(LocalDate date, LocalTime start, int duration, String practitioner) {
		this.date = date;
		this.start = start;
		System.out.println(duration);
		this.end = start.plusMinutes(duration);
		this.practitioner = practitioner;
	}

	public static List<Appointment> getByPatientID(int id) {
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE id=?",(stmt)->{
			stmt.setInt(1,id);
			ResultSet res = stmt.executeQuery();
			while(res.next()){
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2)));
			}
			return appointments;
		});
	}

	public static List<Appointment> getByDate(LocalDate date) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date=?",(stmt)-> {
			stmt.setDate(1, sqlDate);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2)));
			}
			return appointments;
		});
	}

	public static void insert(LocalDate date, String practioner, long patientId, LocalTime start, Duration duration) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

		ConnectionManager.withStatement("INSERT INTO appointments VALUES (?,?,?,?,?)",(stmt)-> {
			stmt.setDate(1, sqlDate);
			stmt.setString(2, practioner);
			stmt.setLong(3, patientId);
			stmt.setTime(4, sqlStartTime);
			stmt.setInt(5, (int)duration.toMinutes());
			stmt.executeUpdate();
			return null;
		});
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getStart() {
		return start;
	}
	
	public LocalTime getEnd() {
		return end;
	}

	public String getPractitioner() {
		return practitioner;
	}
	
	public int getDuration() {
		return (int) Duration.between(start, end).getSeconds()/60;
	}
}
