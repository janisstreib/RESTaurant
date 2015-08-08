package hipster.aurant.rest.dbobjects;

import hipster.aurant.rest.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public class Table {
	private int seats;
	private boolean atWindow;
	private int id;

	public static Table getById(int id) throws SQLException {
		PreparedStatement s = DatabaseConnection.getInstance().prepare(
				"SELECT * FROM tables WHERE id=?");
		s.setInt(1, id);
		ResultSet res = s.executeQuery();
		if (res.first()) {
			return new Table(res);
		}
		return null;

	}

	public Table(ResultSet res) throws SQLException {
		this.seats = res.getInt("seats");
		this.atWindow = res.getBoolean("atwindow");
		this.id = res.getInt("id");
	}

	public int getSeats() {
		return seats;
	}

	public boolean isAtWindow() {
		return atWindow;
	}

	public int getId() {
		return id;
	}

	public JSONObject toJSON() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seats", seats);
		map.put("atWindow", atWindow);
		map.put("id", id);
		return new JSONObject(map);
	}

	public boolean isBookable(Date from, Date to) throws SQLException {
		PreparedStatement prep = DatabaseConnection
				.getInstance()
				.prepare(
						"SELECT id FROM bookings WHERE `table`=? AND timeBegin <= ? AND timeEnd >= ?");
		prep.setInt(1, id);
		prep.setLong(2, from.getTime());
		prep.setLong(3, to.getTime());
		return !prep.executeQuery().first();
	}
}
