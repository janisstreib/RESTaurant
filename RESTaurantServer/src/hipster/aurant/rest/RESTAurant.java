package hipster.aurant.rest;

import hipster.aurant.rest.dbobjects.Booking;
import hipster.aurant.rest.dbobjects.Restaurant;
import hipster.aurant.rest.dbobjects.Table;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
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
        PrintWriter writer = resp.getWriter();
        try {
            if (pathInfo.equals("/")) {
                if (new File(indexPageUrl.toURI()).lastModified() > lastLoaded) {
                    loadIndex();
                }
                writer.print(indexPage);
                return;
            }

            if (pathInfo.startsWith("/booking/")) {
                String r = pathInfo.substring("/booking/".length(),
                        pathInfo.length());
                if (r.startsWith("restaurant/")) {
                    r = r.substring("restaurant/".length(), r.length());
                    if (action == RESTAction.PUT) {
                        Restaurant rest = Restaurant.getById(Integer
                                .parseInt(r));
                        Date begin = new Date(Long.parseLong(req
                                .getParameter("begin")));

                        Date end = new Date(Long.parseLong(req
                                .getParameter("end")));
                        Table table;
                        if(req.getParameter("table") != null) {
                            table = Table.getById(
                                    Integer.parseInt(req.getParameter("table")),
                                    rest);
                            if (table == null) {
                                resp.sendError(400, "table not existant");
                                return;
                            }
                            if (!table.isBookable(begin, end)) {
                                resp.sendError(409, "no bookable table anymore");
                                return;
                            }
                        } else {
                            table = Table.getOptimal(rest, begin, end, req.getParameter("atWindow").toLowerCase().equals("true"));
                            if(table == null){
                                resp.sendError(409, "no bookable table anymore");
                                return;
                            }
                        }
                        Booking b = new Booking(rest, req.getParameter("name"),
                                begin, end, table);
                        resp.setStatus(201);
                        resp.getWriter().println(b.toJSON());
                        return;
                    }
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
                            writer.println(b.toJSON());
                            return;
                        case POST:
// FIXME!
                            break;
                        case DELETE:
                            b.cancel();
                            return;

                        default:
                            break;
                    }
                }
            } else if (pathInfo.startsWith("/restaurants")) {
                if (action == RESTAction.GET) {
                    String r = pathInfo.substring("/restaurants".length(),
                            pathInfo.length());
                    if (r.isEmpty()) {
                        ResultSet res = DatabaseConnection.getInstance()
                                .prepare("SELECT * FROM restaurants")
                                .executeQuery();
                        res.beforeFirst();
                        LinkedList<Object> obj = new LinkedList<Object>();
                        while (res.next()) {
                            obj.add(new Restaurant(res).toJSON());
                        }
                        resp.getWriter().println(new JSONArray(obj));
                    } else {
                        String[] parts = r.split("/");
                        if (parts[2].equals("tables")) {
                            PreparedStatement prepare = DatabaseConnection
                                    .getInstance()
                                    .prepare(
                                            "SELECT * FROM tables WHERE restaurant=?");
                            prepare.setInt(1, Integer.parseInt(parts[1]));
                            ResultSet res = prepare.executeQuery();
                            res.beforeFirst();
                            LinkedList<Object> obj = new LinkedList<Object>();
                            while (res.next()) {
                                obj.add(new Table(res).toJSON());
                            }

                            writer.println(new JSONArray(obj));
                        }
                    }

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
