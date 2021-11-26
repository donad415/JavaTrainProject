package models;

public class Train {
    private Long id;
    private String number;
    private String boss;

    public Train(String number, String boss) {
        this.number = number;
        this.boss = boss;
    }

    public Train() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }
}
