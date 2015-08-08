package hipster.aurant.rest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class RESTAurantConfiguration {
	private Properties p;
	private static RESTAurantConfiguration instance;

	protected RESTAurantConfiguration(InputStream in) throws IOException,
			SQLException {
		this.p = new Properties();
		p.load(in);
		instance = this;
	}

	public int getPort() {
		return Integer.parseInt(p.getProperty("node.port"));
	}

	public String getHostName() {
		return p.getProperty("node.name");
	}

	protected String getDB() {
		return p.getProperty("node.db");
	}

	protected String getDBUser() {
		return p.getProperty("node.db.user");
	}

	protected String getDBPW() {
		return p.getProperty("node.db.pw");
	}

	protected String getJDBCDriver() {
		return p.getProperty("node.db.driver");
	}

	public static RESTAurantConfiguration getInstance() {
		return instance;
	}

}
