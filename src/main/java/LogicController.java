import data.Storage;
import models.Route;
import models.Station;
import models.Train;
import ui.ConsoleCallback;
import ui.MainMenu;

public class LogicController implements ConsoleCallback {

    private Storage storage;

    public LogicController(){
        storage = new Storage();
        storage.initDefaultData();
        MainMenu mainMenu = new MainMenu(storage, this);
        mainMenu.printMenu();
    }

    @Override
    public void deleteDepartureStation(int routeIndex) {
        storage.getRoutes().get(routeIndex).deleteDepartureStation();
    }

    @Override
    public void addDepartureStation(int routeIndex, Station station) {
        storage.getRoutes().get(routeIndex).setDepartureStation(station);
    }

    @Override
    public void deleteComingStation(int routeIndex) {
        storage.getRoutes().get(routeIndex).deleteComingStation();
    }

    @Override
    public void addComingStation(int routeIndex, Station station) {
        storage.getRoutes().get(routeIndex).setComingStation(station);
    }

    @Override
    public void deleteIntermediateStation(int routeIndex, int stationIndex) {
        storage.getRoutes().get(routeIndex).deleteIntermediateStation(stationIndex);
    }

    @Override
    public void addIntermediateStation(int routeIndex, Station station, int position) {
        storage.getRoutes().get(routeIndex).addIntermediateStation(station, position);
    }

    @Override
    public void editIntermediateStation(int routeIndex, Station station, int position) {
        storage.getRoutes().get(routeIndex).editIntermediateStation(station, position);
    }

    @Override
    public void addRoute(Route route) {
        storage.addRoute(route);
    }

    @Override
    public void addStation(Station station) {
        storage.addStation(station);
    }

    @Override
    public void addTrain(Train train) {
        storage.addTrain(train);
    }
}
