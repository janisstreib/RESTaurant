package hipster.aurant.rest.dbobjects;

import hipster.aurant.rest.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import javax.xml.crypto.Data;

public class Table {
    private int seats;
    private boolean atWindow;
    private int id;
    private Restaurant restaurant;


    public static Table getOptimal(Restaurant rest, Date from, Date to, boolean atWindow) throws SQLException {
        PreparedStatement s = DatabaseConnection.getInstance().prepare("SELECT tables.* from tables left join bookings on bookings.table=tables.id WHERE tables.restaurant=? and tables.atWindow=? and  (not ((timeBegin <= ?) and (timeEnd >= ?)) or timeBegin is null) LIMIT 1");
        s.setInt(1, rest.getId());
        s.setBoolean(2, atWindow);
        s.setLong(3, to.getTime()/1000);
        s.setLong(4, from.getTime()/1000);
        ResultSet res = s.executeQuery();
        if(res.first()) {
            return new Table(res, rest);
        }
        return null;
    }

    public static Table getById(int id, Restaurant rest) throws SQLException {
        PreparedStatement s = DatabaseConnection.getInstance().prepare(
                "SELECT * FROM tables WHERE id=? AND restaurant=?");
        s.setInt(1, id);
        s.setInt(2, rest.getId());
        ResultSet res = s.executeQuery();
        if (res.first()) {
            return new Table(res, rest);
        }
        return null;

    }

    public Table(ResultSet res) throws SQLException {
        this.seats = res.getInt("seats");
        this.atWindow = res.getBoolean("atwindow");
        this.id = res.getInt("id");
        this.restaurant = Restaurant.getById(res.getInt("restaurant"));
    }

    public Table(ResultSet res, Restaurant rest) throws SQLException {
        this.seats = res.getInt("seats");
        this.atWindow = res.getBoolean("atwindow");
        this.id = res.getInt("id");
        this.restaurant = rest;

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
        //Date to = new Date(from.getTime() + 1000 * 60 * 60 * 3);
        prep.setLong(3, to.getTime());
        return !prep.executeQuery().first();
    }
}
