package hipster.aurant.rest.dbobjects;

import hipster.aurant.rest.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;

public class Restaurant {
	private String owner, name;
	private EatType eatType;
	private int parkingSpace, id;
	private boolean accessible;

	public static Restaurant getById(int id) throws SQLException {
		PreparedStatement s = DatabaseConnection.getInstance().prepare(
				"SELECT * FROM restaurants WHERE id=?");
		s.setLong(1, id);
		ResultSet res = s.executeQuery();
		if (res.first()) {
			return new Restaurant(res);
		}
		return null;

	}

	public Restaurant(ResultSet r) throws SQLException {
		this.id = r.getInt("id");
		this.name = r.getString("name");
		this.owner = r.getString("owner");
		this.parkingSpace = r.getInt("parking_space");
		this.accessible = r.getBoolean("accessible");
		this.eatType = EatType.valueOf(r.getString("eattype"));
	}

	public Restaurant(String owner, String name, EatType eatType,
			int parkingSpace, boolean accessible) {
		this.owner = owner;
		this.name = name;
		this.eatType = eatType;
		this.parkingSpace = parkingSpace;
		this.accessible = accessible;
	}

	public EatType getEatType() {
		return eatType;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public int getParkingSpace() {
		return parkingSpace;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public int getId() {
		return id;
	}

	public JSONObject toJSON() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("owner", owner);
		map.put("eatType", eatType.name());
		map.put("parkingSpace", parkingSpace);
		map.put("id", id);
		map.put("name", name);
		map.put("accessible", accessible);
		return new JSONObject(map);
	}
}
