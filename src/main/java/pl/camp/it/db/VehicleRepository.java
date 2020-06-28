package pl.camp.it.db;

import pl.camp.it.model.Vehicle;
import java.util.List;

public class VehicleRepository {
    private List<Vehicle> vehicles = SQLDb.getAllVehicles();
    private static final VehicleRepository vehicleRepository = new VehicleRepository();


    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public static VehicleRepository getRepository() {
        return vehicleRepository;
    }
}