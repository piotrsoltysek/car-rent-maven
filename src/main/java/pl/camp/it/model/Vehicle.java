package pl.camp.it.model;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String vin;
    private boolean rent;

    public Vehicle(int id, String brand, String model, String vin) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.rent = false;
    }

    public Vehicle(int id, String brand, String model, String vin, boolean rent) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.rent = rent;
    }

    public int getId() {
        return this.id;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public String getVin() {
        return this.vin;
    }

    public boolean isRent() {
        return this.rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }
}