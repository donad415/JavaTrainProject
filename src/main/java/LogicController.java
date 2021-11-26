import data.DataStorage;
import data.Storage;
import models.Route;
import models.Station;
import models.Train;
import ui.ConsoleCallback;
import ui.MainMenu;

public class LogicController implements ConsoleCallback {

    private DataStorage storage;

    public LogicController(){
        storage = new DataStorage();
        MainMenu mainMenu = new MainMenu(storage, this);
        mainMenu.printMenu();
    }

    //TODO исправить класс маршрута
    //TODO array list to list
    //TODO поправить лоджик контроллер

    @Override
    public void deleteDepartureStation(int routeIndex) {
        Route r = storage.getRoutes().get(routeIndex);
        r.deleteDepartureStation();
        storage.updateRoute(r);
    }

    @Override
    public void addDepartureStation(int routeIndex, Station station) {
        Route r = storage.getRoutes().get(routeIndex);
        r.setDepartureStation(station);
        storage.updateRoute(r);
    }

    @Override
    public void deleteComingStation(int routeIndex) {
        Route r = storage.getRoutes().get(routeIndex);
        r.deleteComingStation();
        storage.updateRoute(r);
    }

    @Override
    public void addComingStation(int routeIndex, Station station) {
        Route r = storage.getRoutes().get(routeIndex);
        r.setComingStation(station);
        storage.updateRoute(r);
    }

    @Override
    public void deleteIntermediateStation(int routeIndex, int stationIndex) {
        Route r = storage.getRoutes().get(routeIndex);
        r.deleteIntermediateStation(stationIndex);
        storage.updateRoute(r);
    }

    @Override
    public void addIntermediateStation(int routeIndex, Station station, int position) {
        Route r = storage.getRoutes().get(routeIndex);
        r.addIntermediateStation(station, position);
        storage.updateRoute(r);
    }

    @Override
    public void editIntermediateStation(int routeIndex, Station station, int position) {
        Route r = storage.getRoutes().get(routeIndex);
        r.editIntermediateStation(station, position);
        storage.updateRoute(r);
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
