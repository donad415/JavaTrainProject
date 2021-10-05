package ui;

import models.Route;
import models.Station;
import models.Train;

public interface ConsoleCallback {
    void deleteDepartureStation(int routeIndex);
    void addDepartureStation(int routeIndex, Station station);
    void deleteComingStation(int routeIndex);
    void addComingStation(int routeIndex, Station station);
    void deleteIntermediateStation(int routeIndex, int stationIndex);
    void addIntermediateStation(int routeIndex, Station station, int position);
    void editIntermediateStation(int routeIndex, Station station, int position);

    void addRoute(Route route);

    void addStation(Station station);

    void addTrain(Train train);

}
