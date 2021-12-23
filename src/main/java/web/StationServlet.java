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
import java.util.Collection;
import java.util.List;


@WebServlet(name = "station", urlPatterns = {"/stations"})
public class StationServlet extends HttpServlet {
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
        req.setAttribute("stations", getStationsArray());
        req.getRequestDispatcher("/stations.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String json = req.getReader().readLine();
        try {
            Station station = mapStation(json);
            storage.addStation(station);
            resp.setStatus(200);
            resp.getWriter().println("Создание прошло успешно");
        } catch (Exception e) {
            //handleException(req, resp, e);
        }
    }

    private Station[] getStationsArray() throws UnsupportedEncodingException {
        List<Station> stationList = storage.getStations();
        Station[] collection= new Station[stationList.size()];
        for (int i = 0; i<stationList.size(); i++){
            collection[i] = stationList.get(i);
        }
        return collection;
    }

    private Station mapStation(String json) throws IOException {
        JsonNode node = mapper.readTree(json);
        Station station = new Station();
        station.setName(node.get("name").asText());
        station.setDepartureTime(node.get("departure").asText());
        station.setComingTime(node.get("coming").asText());
        return station;
    }
}
