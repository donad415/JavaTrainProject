package ui.routesMenu;

import data.DataStorage;
import data.Storage;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;
import ui.MainMenu;

import java.util.Scanner;

public class RoutesMenu implements IMenu {
    private Scanner scanner;
    private DataStorage storage;

    ConsoleCallback callback;

    private final String ROUTES_MENU_STRING = "\n|Меню управления маршрутами| Выберите действие:\n" +
            "1-показать список маршрутов\n" +
            "2-добавить маршрут\n" +
            "3-вернуться";

    private final String[] ROUTES_MENU_ACTIONS = new String[]{"1", "2","3"};

    public RoutesMenu(DataStorage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        System.out.println(ROUTES_MENU_STRING);
        String input = scanner.nextLine();
        if (!InputValidator.isInputValid(input, ROUTES_MENU_ACTIONS)) {
            printMenu();
            return;
        }
        switch (input){
            case "1":
                ShowRoutesMenu showRoutesMenu = new ShowRoutesMenu(storage, callback);
                showRoutesMenu.printMenu();
                break;
            case "2":
                AddRouteMenu addRouteMenu = new AddRouteMenu(storage, callback);
                addRouteMenu.printMenu();
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

}
