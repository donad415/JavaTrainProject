package ui.trainsMenu;

import data.Storage;
import models.Station;
import models.Train;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;
import ui.MainMenu;

import java.util.Scanner;

public class TrainsMenu implements IMenu {
    private Scanner scanner;
    private Storage storage;

    ConsoleCallback callback;

    private final String TRAINS_MENU_STRING = "\n|Меню управления поездами| Выберите действие:\n" +
            "1-показать список поездов\n" +
            "2-добавить поезд\n" +
            "3-вернуться";

    private final String[] TRAINS_MENU_ACTIONS = new String[]{"1", "2","3"};

    public TrainsMenu(Storage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        System.out.println(TRAINS_MENU_STRING);
        String input = scanner.nextLine();
        if (!InputValidator.isInputValid(input, TRAINS_MENU_ACTIONS)) {
            printMenu();
            return;
        }
        switch (input){
            case "1":
                printTrains();
                break;
            case "2":
                addNewTrain();
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

    private void printTrains(){
        for (int i = 0; i < storage.getTrains().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getTrains().get(i).getNumber()+"  "
                    +storage.getTrains().get(i).getBoss());
        }
        System.out.println("\n1-вернуться");
        String input = scanner.nextLine();
        if(!InputValidator.isInputValid(input, new String[]{"1"})){
            printMenu();
            return;
        }
        printMenu();
    }

    private void addNewTrain(){
        System.out.println("Введите номер поезда:");
        String trainNumber = scanner.nextLine();
        System.out.println("Введите имя начальника поезда:");
        String boss = scanner.nextLine();

        Train newTrain = new Train(trainNumber, boss);
        callback.addTrain(newTrain);
        System.out.println("Поезд успешно добавлен!");
        printMenu();
    }
}
