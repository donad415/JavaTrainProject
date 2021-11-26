package models;

public class Station {
    private Long id;
    private String name;
    private String comingTime;
    private String departureTime;

    public Station() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComingTime() {
        return comingTime;
    }

    public void setComingTime(String comingTime) {
        this.comingTime = comingTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
