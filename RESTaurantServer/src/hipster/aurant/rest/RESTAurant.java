package hipster.aurant.rest;

import hipster.aurant.rest.dbobjects.Booking;
import hipster.aurant.rest.dbobjects.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

public class RESTAurant extends HttpServlet {
	private static String indexPage = "";
	private static URL indexPageUrl = RESTAurant.class
			.getResource("index.html");
	private static long lastLoaded;

	static {

	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp, RESTAction.DELETE);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp, RESTAction.GET);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp, RESTAction.POST);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp, RESTAction.PUT);
	}

	private void handleRequest(HttpServletRequest req,
			HttpServletResponse resp, RESTAction action) throws IOException {
		String pathInfo = req.getPathInfo();
		try {
			if (pathInfo.equals("/")) {
				if (new File(indexPageUrl.toURI()).lastModified() > lastLoaded) {
					loadIndex();
				}
				resp.getWriter().print(indexPage);
				return;
			}

			if (pathInfo.startsWith("/booking/")) {
				String r = pathInfo.substring("/booking/".length(),
						pathInfo.length());
				if (action == RESTAction.PUT) {
					Date begin = new Date(Long.parseLong(req
							.getParameter("begin")));
					Date end = new Date(Long.parseLong(req.getParameter("end")));
					if (end.getTime() <= begin.getTime()) {
						resp.sendError(400, "end <= begin");
						return;
					}
					Table table = Table.getById(Integer.parseInt(req
							.getParameter("table")));
					if (table == null) {
						resp.sendError(400, "table not existant");
						return;
					}
					if (!table.isBookable(begin, end)) {
						resp.sendError(409, "table not bookable anymore");
						return;
					}
					Booking b = new Booking(req.getParameter("name"), begin,
							table, end);
					resp.setStatus(201);
					resp.getWriter().println(b.toJSON());
					return;
				}

				// actions on specific booking
				if (r.matches("[0-9]+")) {
					Booking b = Booking.getById(Long.parseLong(r));
					if (b == null) {
						resp.sendError(404);
						return;
					}
					switch (action) {
						case GET:
							resp.getWriter().println(b.toJSON());
							return;
						case POST:

							break;
						case DELETE:
							b.cancel();
							return;

						default:
							break;
					}
				}
			} else if (pathInfo.startsWith("/tables")) {
				if (action == RESTAction.GET) {
					ResultSet res = DatabaseConnection.getInstance()
							.prepare("SELECT * FROM tables").executeQuery();
					res.beforeFirst();
					LinkedList<Object> obj = new LinkedList<Object>();
					while (res.next()) {
						obj.add(new Table(res).toJSON());
					}
					resp.getWriter().println(new JSONArray(obj));
					return;
				}
			}
		} catch (NumberFormatException | SQLException | URISyntaxException e) {
			e.printStackTrace();
			resp.sendError(500);
		}
		resp.sendError(404);
	}

	private static void loadIndex() throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(
				indexPageUrl.openStream()));
		String tmp;
		synchronized (indexPage) {
			indexPage = "";
			try {
				while ((tmp = r.readLine()) != null) {
					indexPage += "\n" + tmp;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			lastLoaded = System.currentTimeMillis();
			System.out.println("Reloaded index.");
		}
	}
}
