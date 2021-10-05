package data;

import models.Route;
import models.Station;
import models.Train;

import java.util.ArrayList;

public class Storage {
    private ArrayList<Route> routes;
    private ArrayList<Train> trains;
    private ArrayList<Station> stations;

    public Storage(){
        routes = new ArrayList<>();
        trains = new ArrayList<>();
        stations = new ArrayList<>();
    }

    public void initDefaultData(){
        trains.add(new Train("14123", "Евгений Козлов"));
        trains.add(new Train("56939", "Василий Паровозов"));

        Station a = new Station("Волгоградская");
        stations.add(a);
        a.setComingTime("14:30");
        a.setDepartureTime("15:00");

        Station b = new Station("Чернозёмная");
        stations.add(b);
        b.setComingTime("17:15");
        b.setDepartureTime("17:20");

        Station c = new Station("Ростовская");
        stations.add(c);
        c.setComingTime("19:48");
        c.setDepartureTime("19:53");

        Station d = new Station("Тропическая");
        stations.add(d);
        d.setComingTime("22:44");
        d.setDepartureTime("22:55");

        Route r = new Route("Южный");
        r.setDepartureStation(a);
        r.setComingStation(d);
        r.setTrain(trains.get(1));
        r.addIntermediateStation(b, 1);
        r.addIntermediateStation(c, 2);
        routes.add(r);

    }

    public void addRoute(Route route){
        routes.add(route);
    }

    public void addTrain(Train train){
        trains.add(train);
    }

    public void addStation(Station station){
        stations.add(station);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}
