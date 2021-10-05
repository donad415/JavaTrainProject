package ui;

import data.Storage;
import ui.routesMenu.RoutesMenu;
import ui.stationsMenu.StationsMenu;
import ui.trainsMenu.TrainsMenu;

import java.util.Scanner;

public class MainMenu implements IMenu{
    private Scanner scanner;
    private Storage storage;

    private final String MAIN_MENU_STRING = "\n|Главное меню|  Выберите действие:\n1-управление маршрутами\n" +
            "2-управление поездами\n3-управление станциями";

    private final String[] MENU_ACTIONS = new String[]{"1", "2", "3"};

    ConsoleCallback callback;

    public MainMenu(Storage storage, ConsoleCallback callback){
        scanner = new Scanner(System.in);
        this.callback = callback;
        this.storage = storage;
    }

    public void printMenu(){
        System.out.println(MAIN_MENU_STRING);
        String input = scanner.nextLine();
        if(!InputValidator.isInputValid(input, MENU_ACTIONS)){
            printMenu();
            return;
        }
        switch (input){
            case "1":
                RoutesMenu routesMenu = new RoutesMenu(storage, callback);
                routesMenu.printMenu();
                break;
            case "2":
                TrainsMenu trainsMenu = new TrainsMenu(storage, callback);
                trainsMenu.printMenu();
                break;
            case "3":
                StationsMenu stationsMenu = new StationsMenu(storage, callback);
                stationsMenu.printMenu();
                break;
        }
    }

}
