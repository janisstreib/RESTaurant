package hipster.aurant.rest;

import hipster.aurant.rest.dbobjects.Booking;
import hipster.aurant.rest.dbobjects.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RESTAurant extends HttpServlet {
	private static String indexPage = "";

	static {
		BufferedReader r = new BufferedReader(new InputStreamReader(
				RESTAurant.class.getResourceAsStream("index.html")));
		String tmp;
		try {
			while ((tmp = r.readLine()) != null) {
				indexPage += "\n" + tmp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				System.out.println(r);
				if (r.matches("[0-9]+")) {
					switch (action) {
						case GET:
							Booking b = Booking.getById(Long.parseLong(r));
							if (b == null) {
								resp.sendError(404);
								return;
							} else {
								resp.getWriter().println(b.toJSON());
								return;
							}
						case POST:

							break;
						case DELETE:

							break;

						default:
							break;
					}
				}
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			resp.sendError(500);
		}
		resp.sendError(404);
	}
}
