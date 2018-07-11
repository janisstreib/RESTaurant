package hipster.aurant.rest.dbobjects;

import hipster.aurant.rest.DatabaseConnection;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public class Booking {
	private String name;
	private Date begin, end;
	private Table table;
	private long id;
	private Restaurant restaurant;

	public static Booking getById(long id) throws SQLException {
		PreparedStatement s = DatabaseConnection.getInstance().prepare(
				"SELECT * FROM bookings WHERE id=?");
		s.setLong(1, id);
		ResultSet res = s.executeQuery();
		if (res.first()) {
			return new Booking(res);
		}
		return null;

	}

	public Booking(Restaurant restaurant, String name, Date begin, Date end, Table table)
			throws SQLException {
		this.name = name;
		this.begin = begin;
		this.table = table;
		//Date end = new Date(begin.getTime() + 1000 * 60 * 60 * 3);
		this.end = end;
		SecureRandom r = new SecureRandom();
		this.id = Math.abs(r.nextLong());
		PreparedStatement prep = DatabaseConnection
				.getInstance()
				.prepare(
						"INSERT INTO bookings SET id=?, name=?, `table`=?, timeBegin=?, timeEnd=?, restaurant=?");
		prep.setLong(1, id);
		prep.setString(2, name);
		prep.setInt(3, table.getId());
		prep.setLong(4, begin.getTime());
		prep.setLong(5, end.getTime());
		prep.setInt(6, restaurant.getId());
		prep.execute();
	}

	public Booking(ResultSet r) throws SQLException {
		this.name = r.getString("name");
		this.begin = new Date(r.getLong("timeBegin"));
		this.end = new Date(r.getLong("timeEnd"));
		this.id = r.getLong("id");
		this.restaurant = Restaurant.getById(r.getInt("restaurant"));
		this.table = Table.getById(r.getInt("table"), restaurant);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBegin() {
		return begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setBegin(Date time) {
		this.begin = time;
	}

	public void setEnd(Date time) {
		this.end = time;
	}

	public Table getTable() {
		return table;
	}

	public JSONObject toJSON() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("begin", begin.getTime());
		map.put("end", end.getTime());
		map.put("table", table.toJSON());
		map.put("restaurant", restaurant.toJSON());
		return new JSONObject(map);
	}

	public void cancel() throws SQLException {
		PreparedStatement prep = DatabaseConnection.getInstance().prepare(
				"DELETE FROM bookings WHERE id=?");
		prep.setLong(1, id);
		prep.execute();
	}

}
