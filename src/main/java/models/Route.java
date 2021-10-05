package models;

import java.util.ArrayList;

public class Route {
    private String name;
    private Station departureStation;
    private Station comingStation;
    private Train train;
    private ArrayList<Station> intermediateStations = new ArrayList<>();

    public Route(String name) {
        this.name = name;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getComingStation() {
        return comingStation;
    }

    public void setComingStation(Station comingStation) {
        this.comingStation = comingStation;
    }

    public ArrayList<Station> getIntermediateStations() {
        return intermediateStations;
    }

    public void addIntermediateStation(Station station, int prefferedPosition){
        if (prefferedPosition > intermediateStations.size()){
            intermediateStations.add(station);
        }else if(prefferedPosition == 1){
            ArrayList<Station> list = intermediateStations;
            intermediateStations.clear();
            intermediateStations.add(station);
            intermediateStations.addAll(list);
        } else{
            ArrayList<Station> list = new ArrayList<>();
            for(int i = 0; i<intermediateStations.size(); i++){
                if(i==prefferedPosition-1){
                    list.add(station);
                    list.add(intermediateStations.get(i));
                }
            }
            intermediateStations = list;
        }
    }

    public void editIntermediateStation(Station station, int position){
        intermediateStations.set(position-1, station);
    }

    public void deleteIntermediateStation(int index){
        intermediateStations.remove(index);
    }

    public void deleteDepartureStation(){
        departureStation = null;
    }

    public void deleteComingStation(){
        comingStation = null;
    }
}
