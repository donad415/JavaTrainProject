package ui.stationsMenu;

import data.Storage;
import models.Station;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;
import ui.MainMenu;
import ui.routesMenu.AddRouteMenu;

import java.util.Scanner;

public class StationsMenu implements IMenu {

    private Scanner scanner;
    private Storage storage;

    ConsoleCallback callback;

    private final String STATIONS_MENU_STRING = "\n|Меню управления станциями| Выберите действие:\n" +
            "1-показать список станций\n" +
            "2-добавить станцию\n" +
            "3-вернуться";

    private final String[] STATIONS_MENU_ACTIONS = new String[]{"1", "2","3"};

    public StationsMenu(Storage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        System.out.println(STATIONS_MENU_STRING);
        String input = scanner.nextLine();
        if (!InputValidator.isInputValid(input, STATIONS_MENU_ACTIONS)) {
            printMenu();
            return;
        }
        switch (input){
            case "1":
                printStatioons();
                break;
            case "2":
                addNewStation();
                break;
            case "3":
                back();
                break;
        }
    }

    private void back(){
        MainMenu mainMenu = new MainMenu(storage, callback);
        mainMenu.printMenu();
    }

    private void printStatioons(){
        for (int i = 0; i < storage.getStations().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getStations().get(i).getName());
        }
        System.out.println("\n1-вернуться");
        String input = scanner.nextLine();
        if(!InputValidator.isInputValid(input, new String[]{"1"})){
            printMenu();
            return;
        }
        printMenu();
    }

    private void addNewStation(){
        System.out.println("Введите название станции:");
        String stationName = scanner.nextLine();
        Station newStation = new Station(stationName);
        callback.addStation(newStation);
        System.out.println("Станция успешно добавлена!");
        printMenu();
    }
}
