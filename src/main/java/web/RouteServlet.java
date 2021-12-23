package web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.DataStorage;
import models.Route;
import models.Station;
import models.Train;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet(name = "main", urlPatterns = {"/"})
public class RouteServlet extends HttpServlet {
    private DataStorage storage;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = new DataStorage();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("routes", getRoutesArray());
        req.setAttribute("trains", getTrainsArray());
        req.setAttribute("stations", getStationsArray());
        req.getRequestDispatcher("/routes.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String json = req.getReader().readLine();
        try {
            Route route = mapRoute(json);
            storage.addRoute(route);
            resp.setStatus(200);
            resp.getWriter().println("Создание прошло успешно");
        } catch (Exception e) {
            //handleException(req, resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            storage.deleteRouteById(id);
            req.setAttribute("routes", getRoutesArray());
            req.setAttribute("trains", getTrainsArray());
            req.setAttribute("stations", getStationsArray());

            req.getRequestDispatcher("/routes.jsp").forward(req, resp);
        } catch (Exception e) {
            //handleException(req, resp, e);
        }
    }

    private Route mapRoute(String json) throws IOException {
        JsonNode node = mapper.readTree(json);
        Route route = new Route();
        route.setName(node.get("name").asText());
        System.out.println("!!!" +node.get("intermediate"));
        for (JsonNode id: node.get("intermediate")){
            Station station = new Station();
            station.setId(Long.parseLong(id.asText()));
            route.addIntermediateStation(station,route.getIntermediateStations().size()+1);
        }
        Station station1 = new Station();
        station1.setId(Long.parseLong(node.get("departure").asText()));
        route.setDepartureStation(station1);

        Station station2 = new Station();
        station2.setId(Long.parseLong(node.get("coming").asText()));
        route.setComingStation(station2);

        Train train = new Train();
        train.setId(Long.parseLong(node.get("train").asText()));
        route.setTrain(train);
        return route;
    }

    private Route[] getRoutesArray() throws UnsupportedEncodingException {
        List<Route> routeList = storage.getRoutes();
        Route[] collection= new Route[routeList.size()];
        for (int i = 0; i<routeList.size(); i++){
            //routeList.get(i).setName(new String(routeList.get(i).getName().getBytes("WINDOWS-1251"), "UTF-8"));
            collection[i] = routeList.get(i);
        }
        return collection;
    }

    private Station[] getStationsArray() throws UnsupportedEncodingException {
        List<Station> stationList = storage.getStations();
        Station[] collection= new Station[stationList.size()];
        for (int i = 0; i<stationList.size(); i++){
            collection[i] = stationList.get(i);
        }
        return collection;
    }

    private Train[] getTrainsArray() throws UnsupportedEncodingException {
        List<Train> trainList = storage.getTrains();
        Train[] collection= new Train[trainList.size()];
        for (int i = 0; i<trainList.size(); i++){
            collection[i] = trainList.get(i);
        }
        return collection;
    }
}
