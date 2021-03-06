package ui.routesMenu;

import data.DataStorage;
import data.Storage;
import models.Route;
import models.Station;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;
import ui.routesMenu.EditRouteMenu;
import ui.routesMenu.RoutesMenu;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.*;

public class ShowRoutesMenu implements IMenu {
    private Scanner scanner;
    private DataStorage storage;

    ConsoleCallback callback;

    private final String SHOW_ROUTES_MENU_STRING = "Выберите действие:\n" +
            "1-редактировать маршрут\n" +
            "2-вернуться";

    private final String[] SHOW_ROUTES_MENU_ACTIONS = new String[]{"1", "2"};

    public ShowRoutesMenu(DataStorage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        List<Route> routes =  storage.getRoutes();
        for (int i = 0; i < routes.size(); i++) {
            int num = i + 1;
            if(routes.get(i).getDepartureStation() == null){
                routes.get(i).setDepartureStation(new Station(""));
            }
            if(routes.get(i).getComingStation() == null){
                routes.get(i).setComingStation(new Station(""));
            }
            System.out.println(num + " - " + routes.get(i).getName() + "   "
                    + routes.get(i).getDepartureStation().getName() + " - "
                    + routes.get(i).getComingStation().getName());
        }
        System.out.println(SHOW_ROUTES_MENU_STRING);
        String input = scanner.nextLine();
        if(!InputValidator.isInputValid(input, SHOW_ROUTES_MENU_ACTIONS)){
            printMenu();
            return;
        }
        switch (input){
            case "1":
                getRouteNumber();
                break;
            case "2":
                back();
                break;
        }
    }

    private void back(){
        RoutesMenu routesMenu = new RoutesMenu(storage, callback);
        routesMenu.printMenu();
    }

    private void getRouteNumber(){
        System.out.println("Введите номер редактируемого маршрута:");
        String input = scanner.nextLine();
        String[] validNums = new String[storage.getRoutes().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            getRouteNumber();
            return;
        }
        EditRouteMenu editRouteMenu =
                new EditRouteMenu(Integer.parseInt(input)-1, storage, callback);
        editRouteMenu.printMenu();
    }
}
