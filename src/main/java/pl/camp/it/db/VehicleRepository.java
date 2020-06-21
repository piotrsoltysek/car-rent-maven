package pl.camp.it.db;

import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.Vehicle;

public class VehicleRepository {
    private Vehicle[] vehicles = new Vehicle[10];

    public VehicleRepository() {
        Car c1 = new Car(1, "BMW", "3", "gtjoitrj");
        Car c2 = new Car(2, "Toyota", "Corolla", "gretgtrs");
        Car c3 = new Car(3, "Ford", "Focus", "ffef");

        this.vehicles[0] = c1;
        this.vehicles[1] = c2;
        this.vehicles[2] = c3;

        this.vehicles[3] = new Bus(4, "Solaris", "152", "ergregregeg", 40, 6);
        this.vehicles[4] = new Bus(5, "Solaris", "A100", "trrtsg", 45, 6);

    }

    public Vehicle[] getVehicles() {
        return this.vehicles;
    }
}
