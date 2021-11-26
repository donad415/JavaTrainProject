package ui.routesMenu;

import data.DataStorage;
import data.Storage;
import models.Route;
import models.Station;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;

import java.util.Scanner;

public class AddRouteMenu implements IMenu {

    private Scanner scanner;
    private DataStorage storage;

    ConsoleCallback callback;

    public AddRouteMenu(DataStorage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        System.out.println("Введите название маршрута: ");
        String routeName = scanner.nextLine();
        Route newRoute = new Route(routeName);

        System.out.println("Выберите поезд: ");
        for (int i = 0; i < storage.getTrains().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getTrains().get(i).getNumber() + "   "
                    +storage.getTrains().get(i).getBoss());
        }
        String input = scanner.nextLine();
        String[] validNums =
                new String[storage.getTrains().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            printMenu();
            return;
        }
        newRoute.setTrain(storage.getTrains().get(Integer.parseInt(input)-1));

        System.out.println("Маршрут добавлен! Заполните его в режиме редактирования!");

        callback.addRoute(newRoute);

        RoutesMenu routesMenu = new RoutesMenu(storage, callback);
        routesMenu.printMenu();
    }
}
