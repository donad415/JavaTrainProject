package ui.routesMenu;

import data.DataStorage;
import data.Storage;
import models.Route;
import models.Station;
import ui.ConsoleCallback;
import ui.IMenu;
import ui.InputValidator;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.Scanner;

public class EditRouteMenu implements IMenu {
    private Scanner scanner;
    private int routeIndex;
    private DataStorage storage;

    ConsoleCallback callback;

    private final String EDIT_ROUTES_MENU_STRING = "\nВыберите действие:\n" +
            "1-изменить пункт отправления\n" +
           // "2-удалить пункт отправления\n" +
            "2-изменить пункт прибытия\n" +
            //"4-удалить пункт прибытия\n" +
            "3-изменить промежуточный пункт\n" +
            "4-добавить промежуточный пункт\n"+
            "5-удалить промежуточный пункт\n" +
            "6-Вернуться";

    private final String[] EDIT_ROUTES_MENU_ACTIONS =
            new String[]{"1", "2", "3", "4", "5", "6"};

    public EditRouteMenu(int routeIndex, DataStorage storage, ConsoleCallback callback) {
        scanner = new Scanner(System.in);
        this.routeIndex = routeIndex;
        this.storage = storage;
        this.callback = callback;
    }

    public void printMenu() {
        Route route = storage.getRoutes().get(routeIndex);
        System.out.println("\nНазвание маршрута: "+route.getName());
        System.out.println("Номер поезда: "+route.getTrain().getNumber());
        if(route.getDepartureStation() == null){
            route.setDepartureStation(new Station(""));
        }
        if(route.getComingStation() == null){
            route.setComingStation(new Station(""));
        }
        System.out.println("Пункт отправления: "+route.getDepartureStation().getName()+"  "
                +route.getDepartureStation().getComingTime()+"  "
                +route.getDepartureStation().getDepartureTime());
        System.out.println("Промежуточные станции: ");
        for (int i = 0; i < route.getIntermediateStations().size(); i++) {
            int num = i + 1;
            if(route.getIntermediateStations().get(i) == null){
                route.getIntermediateStations().set(i, new Station(""));
            }
            System.out.println(num + " - " + route.getIntermediateStations().get(i).getName() + "   "
                    + route.getIntermediateStations().get(i).getComingTime() + "  "
                    + route.getIntermediateStations().get(i).getDepartureTime());
        }
        System.out.println("Пункт прибытия: "+route.getComingStation().getName()+"  "
                +route.getComingStation().getComingTime()+"  "
                +route.getComingStation().getDepartureTime());
        System.out.println(EDIT_ROUTES_MENU_STRING);
        String input = scanner.nextLine();
        if(!InputValidator.isInputValid(input, EDIT_ROUTES_MENU_ACTIONS)){
            printMenu();
            return;
        }
        switch (input){
            case "1":
                addDepartureStation();
                break;
            /*case "2":
                deleteDepartureStation();
                break;*/
            case "2":
                addComingStation();
                break;
            /*case "4":
                deleteComingStation();
                break;*/
            case "3":
                editIntermediateStation();
                break;
            case "4":
                addIntermediateStation();
                break;
            case "5":
                deleteIntermediateStation();
                break;
            case "6":
                back();
                break;

        }
    }

    private void back(){
        ShowRoutesMenu showRoutesMenu = new ShowRoutesMenu(storage, callback);
        showRoutesMenu.printMenu();
    }

    private void getIntermediateStation(){
        System.out.println("Введите номер редактируемой промежуточной станции:");
        String input = scanner.nextLine();
        String[] validNums =
                new String[storage.getRoutes().get(routeIndex).getIntermediateStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            getIntermediateStation();
            return;
        }
        printEditStationMenu(Integer.parseInt(input)-1);
    }

    // -2 - пункт отправления  -1 - пункт прибытия
    private void printEditStationMenu(int stationIndex){

    }

    private void deleteDepartureStation(){
        callback.deleteDepartureStation(routeIndex);
        System.out.println("Пункт отправления удалён!");
        printMenu();
    }

    private void deleteComingStation(){
        callback.deleteComingStation(routeIndex);
        System.out.println("Пункт прибытия удалён!");
        printMenu();
    }

    private void addDepartureStation(){
        System.out.println("Выберите станцию: ");
        for (int i = 0; i < storage.getStations().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getStations().get(i).getName());
        }
        String input = scanner.nextLine();
        String[] validNums =
                new String[storage.getStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            addDepartureStation();
            return;
        }
        Station station = storage.getStations().get(Integer.parseInt(input)-1);
        System.out.println("Введите время прибытия: ");
        station.setComingTime(scanner.nextLine());
        System.out.println("Введите время отправления: ");
        station.setDepartureTime(scanner.nextLine());
        storage.updateStation(station);


        callback.addDepartureStation(routeIndex, station);

        System.out.println("Пункт отправления добавлен!");
        printMenu();
    }

    private void addComingStation(){
        System.out.println("Выберите станцию: ");
        for (int i = 0; i < storage.getStations().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getStations().get(i).getName());
        }
        String input = scanner.nextLine();
        String[] validNums =
                new String[storage.getStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            addDepartureStation();
            return;
        }
        Station station = storage.getStations().get(Integer.parseInt(input)-1);
        System.out.println("Введите время прибытия: ");
        station.setComingTime(scanner.nextLine());
        System.out.println("Введите время отправления: ");
        station.setDepartureTime(scanner.nextLine());
        storage.updateStation(station);

        callback.addComingStation(routeIndex, station);

        System.out.println("Пункт прибытия добавлен!");
        printMenu();
    }

    private void deleteIntermediateStation(){
        List<Station> intermediateStations =
                storage.getRoutes().get(routeIndex).getIntermediateStations();
        System.out.println("Выберите станцию: ");
        for (int i = 0; i < intermediateStations.size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + intermediateStations.get(i).getName());
        }
        String input = scanner.nextLine();
        String[] validNums =
                new String[intermediateStations.size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            deleteIntermediateStation();
            return;
        }

        callback.deleteIntermediateStation(routeIndex, Integer.parseInt(input)-1);

        System.out.println("Промежуточный пункт удалён!");
        printMenu();
    }

    private void addIntermediateStation(){
        System.out.println("Выберите позицию: ");
        String position = scanner.nextLine();
        String[] validNums =
                new String[storage.getRoutes().get(routeIndex).getIntermediateStations().size()+1];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(position, validNums)){
            addIntermediateStation();
            return;
        }

        System.out.println("Выберите станцию: ");
        for (int i = 0; i < storage.getStations().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getStations().get(i).getName());
        }
        String input = scanner.nextLine();
        validNums =
                new String[storage.getStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            addIntermediateStation();
            return;
        }
        Station station = storage.getStations().get(Integer.parseInt(input)-1);
        System.out.println("Введите время прибытия: ");
        station.setComingTime(scanner.nextLine());
        System.out.println("Введите время отправления: ");
        station.setDepartureTime(scanner.nextLine());
        storage.updateStation(station);

        callback.addIntermediateStation(routeIndex, station, Integer.parseInt(position));

        System.out.println("Промежуточный пункт добавлен!");
        printMenu();
    }

    private void editIntermediateStation(){
        System.out.println("Выберите позицию: ");
        String position = scanner.nextLine();
        String[] validNums =
                new String[storage.getRoutes().get(routeIndex).getIntermediateStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(position, validNums)){
            addIntermediateStation();
            return;
        }

        System.out.println("Выберите станцию: ");
        for (int i = 0; i < storage.getStations().size(); i++) {
            int num = i + 1;
            System.out.println(num + " - " + storage.getStations().get(i).getName());
        }
        String input = scanner.nextLine();
        validNums =
                new String[storage.getStations().size()];
        for(int i = 0; i< validNums.length; i++){
            validNums[i] = String.valueOf(i+1);
        }
        if(!InputValidator.isInputValid(input, validNums)){
            addIntermediateStation();
            return;
        }
        Station station = storage.getStations().get(Integer.parseInt(input)-1);
        System.out.println("Введите время прибытия: ");
        station.setComingTime(scanner.nextLine());
        System.out.println("Введите время отправления: ");
        station.setDepartureTime(scanner.nextLine());
        storage.updateStation(station);

        callback.editIntermediateStation(routeIndex, station, Integer.parseInt(position));

        System.out.println("Промежуточный пункт изменён!");
        printMenu();
    }

}
