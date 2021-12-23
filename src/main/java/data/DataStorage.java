package data;

import models.Route;
import models.Station;
import models.Train;

import java.sql.*;
import java.util.*;

public class DataStorage {

    public Station addStation(Station station){
        try{
            return saveStationToDb(station);
        }catch (SQLException | ClassNotFoundException exception){
            throw new RuntimeException("Cannot save station", exception);
        }
    }

    public Train addTrain(Train train){
        try{
            return saveTrainToDb(train);
        }catch (SQLException | ClassNotFoundException exception){
            throw new RuntimeException("Cannot save train", exception);
        }
    }

    public Route addRoute(Route route){
        try{
            return saveRouteToDb(route);
        }catch (SQLException | ClassNotFoundException exception){
            throw new RuntimeException("Cannot save route", exception);
        }
    }

    public List<Train> getTrains() {
        List<Train> result = new ArrayList<>();
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("select * from trains");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Train train = new Train();
                train.setId(rs.getLong(1));
                train.setNumber(rs.getString(2));
                train.setBoss(rs.getString(3));
                result.add(train);
            }
            return result;
        }catch (SQLException | ClassNotFoundException ex){
            throw new RuntimeException("Cannot load trains", ex);
        }
    }

    public List<Station> getStations() {
        List<Station> result = new ArrayList<>();
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("select * from stations");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Station station = new Station();
                station.setId(rs.getLong(1));
                station.setName(rs.getString(2));
                station.setComingTime(rs.getString(3));
                station.setDepartureTime(rs.getString(4));
                result.add(station);
            }
            return result;
        }catch (SQLException | ClassNotFoundException ex){
            throw new RuntimeException("Cannot load stations", ex);
        }
    }

    public List<Route> getRoutes(){
        List<Route> result = new ArrayList<>();
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("select * from routes");
            ResultSet rs = statement.executeQuery();
            List<Train> trains = getTrains();
            List<Station> stations = getStations();
            while (rs.next()){
                Route route = new Route();
                route.setName(rs.getString(1));
                route.setId(rs.getLong(2));
                route.setDepartureStation(getStationById(stations, rs.getLong(3)));
                route.setComingStation(getStationById(stations, rs.getLong(4)));
                route.setTrain(getTrainById(trains, rs.getLong(5)));
                if(rs.getArray(6) != null){
                    Long[] stationIds = (Long[]) rs.getArray(6).getArray();
                    int i = 1;
                    for(Long id: stationIds){
                        route.addIntermediateStation(getStationById(stations, id), i);
                        i++;
                    }
                }
                result.add(route);
            }
            return result;
        }catch (SQLException | ClassNotFoundException ex){
            throw new RuntimeException("Cannot load routes", ex);
        }
    }

    public void deleteRouteById(int id){
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement("delete from routes where id = "+id);
            statement.executeUpdate();
        }catch (SQLException | ClassNotFoundException ex){
            throw new RuntimeException("Cannot load stations", ex);
        }
    }

    private Station getStationById(List<Station> stations, long id) {
        for(Station station: stations){
            if(station.getId() == id){
                return station;
            }
        }
        return null;
    }

    private Train getTrainById(List<Train> trains, long id) {
        for(Train train: trains){
            if(train.getId() == id){
                return train;
            }
        }
        return null;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        //DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/trainproject?user=postgres&password=12345678");
    }

    private Station saveStationToDb (Station station) throws SQLException, ClassNotFoundException {
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "insert into stations(station_name, coming_time, departure_time) values (?, ?, ?)");
            statement.setString(1, station.getName());
            statement.setString(2, station.getComingTime());
            statement.setString(3, station.getDepartureTime());

            statement.executeUpdate();
        }
        return station;
    }

    private Train saveTrainToDb (Train train) throws SQLException, ClassNotFoundException {
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "insert into trains(train_number, boss) values (?, ?)");
            statement.setString(1, train.getNumber());
            statement.setString(2, train.getBoss());

            statement.executeUpdate();
        }
        return train;
    }

    private Route saveRouteToDb (Route route) throws SQLException, ClassNotFoundException {
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "insert into routes(route_name, train_id, departure_station_id, coming_station_id, intermediate_stations) values (?, ?, ?, ?, ?)");
            statement.setString(1, route.getName());
            statement.setLong(2, route.getTrain().getId());
            statement.setLong(3, route.getDepartureStation().getId());
            statement.setLong(4, route.getComingStation().getId());

            Long[] stationIds = new Long[route.getIntermediateStations().size()];
            for(int i = 0; i< stationIds.length; i++){
                stationIds[i] = route.getIntermediateStations().get(i).getId();
            }
            statement.setArray(5, c.createArrayOf("BIGINT", stationIds));
            statement.executeUpdate();
        }
        return route;
    }

    public Station updateStation(Station station)  {
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement(
                            "update stations set coming_time=?,departure_time=? where id=?");
            statement.setString(1, station.getComingTime());
            statement.setString(2, station.getDepartureTime());
            statement.setLong(3, station.getId());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return station;
    }

    public Route updateRoute(Route route)  {
        try(Connection c = getConnection()){
            Long[] stationIds = new Long[route.getIntermediateStations().size()];
            for(int i = 0; i< stationIds.length; i++){
                stationIds[i] = route.getIntermediateStations().get(i).getId();
            }
            PreparedStatement statement = null;
            if(stationIds.length == 0){
                if(route.getDepartureStation()!=null){
                    statement = c.prepareStatement(
                            "update routes set departure_station_id=? where id=?");
                    statement.setLong(1, route.getDepartureStation().getId());
                    statement.setLong(2, route.getId());
                }
                if(route.getComingStation()!=null){
                    statement = c.prepareStatement(
                            "update routes set coming_station_id=? where id=?");
                    statement.setLong(1, route.getComingStation().getId());
                    statement.setLong(2, route.getId());
                }


            }else{
                 statement = c.prepareStatement(
                        "update routes set departure_station_id=?, coming_station_id=?," +
                                "intermediate_stations=?  where id=?");
                statement.setLong(1, route.getDepartureStation().getId());
                statement.setLong(2, route.getComingStation().getId());
                statement.setArray(3, c.createArrayOf("BIGINT", stationIds));
                statement.setLong(4, route.getId());

            }

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return route;
    }
/*
    public void deleteFromDb (ScoreModel scoreModel) throws SQLException, ClassNotFoundException {
        try(Connection c = getConnection()){
            PreparedStatement statement = c.prepareStatement(
                    "delete from score where tank_id="+scoreModel.getTankId());
            statement.executeUpdate();
        }
    }*/
}
