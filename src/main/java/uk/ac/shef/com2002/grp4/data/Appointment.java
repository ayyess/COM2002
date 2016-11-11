package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

	private long patientId;
	private LocalDate date;
	private LocalTime start;
	private long duration;
	private String practitioner;

	public Appointment(LocalDate date, String practitioner, long patientId, LocalTime start, Duration duration) {
		this(date,practitioner,patientId,start,duration.toMinutes());
	}

	public Appointment(LocalDate date, String practitioner, long patientId, LocalTime start, long duration) {
		this.date = date;
		this.practitioner = practitioner;
		this.patientId = patientId;
		this.start = start;
		this.duration = duration;
	}

	public static List<Appointment> getByPatientID(int patientId) {
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE patient_id=?",(stmt)->{
			stmt.setInt(1,patientId);
			ResultSet res = stmt.executeQuery();
			while(res.next()){
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getString(2), res.getLong(3), res.getTime(4).toLocalTime(),res.getInt(5)));
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
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getString(2), res.getLong(3), res.getTime(4).toLocalTime(),res.getInt(5)));
			}
			return appointments;
		});
	}

	public void insert() {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

		ConnectionManager.withStatement("INSERT INTO appointments VALUES (?,?,?,?,?)",(stmt)-> {
			stmt.setDate(1, sqlDate);
			stmt.setString(2, practitioner);
			stmt.setLong(3, patientId);
			stmt.setTime(4, sqlStartTime);
			stmt.setLong(5, duration);
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
		return start.plusMinutes(duration);
	}

	public String getPractitioner() {
		return practitioner;
	}
	
	public long getDuration() {
		return duration;
	}
}
