package pl.camp.it.db;

import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDb {
    public static final Connection connection = connect();


    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://79.96.53.155:3306/18942511_carrentdb?useUnicode=true&useJDBCCompliantTimezoneShift" +
                            "=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "18942511_carrentdb", "piotrek123");
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Brak połączenia z bazą");
        return null;
    }

    public static void saveVehicle(Vehicle vehicle) {
        try {
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder();
            sql
                    .append("INSERT INTO tvehicle (brand, model, vin, rent, personsAmount, wheelsCount) VALUES ('")
                    .append(vehicle.getBrand())
                    .append("', '")
                    .append(vehicle.getModel())
                    .append("', '")
                    .append(vehicle.getVin())
                    .append("', ")
                    .append(vehicle.isRent());
            // TODO: 2020-06-28 Sprawdzić czy działa automatyczne dodawanie null dla Car
                    if (vehicle instanceof Bus) {
                        Bus temp = (Bus) vehicle;
                        sql
                                .append(", ")
                                .append(temp.getPersonsAmount())
                                .append(", ")
                                .append(temp.getWheelsCount());
                    }
                    sql.append(")");

            statement.executeUpdate(sql.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> resultList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            ResultSet wyniki = statement.executeQuery("SELECT * FROM tvehicle");

            while (wyniki.next()) {
                int id = wyniki.getInt("id");
                String brand = wyniki.getString("brand");
                String model = wyniki.getString("model");
                String vin = wyniki.getString("vin");
                boolean rent = wyniki.getBoolean("rent");
                // TODO: 2020-06-28 Sprawdzić co się stanie jak w bazie będzie null
                Integer personsAmount = wyniki.getInt("personsAmount");
                Integer wheelsCount = wyniki.getInt("wheelsCount");

                if (personsAmount == null) {
                    resultList.add(new Car(id, brand, model, vin, rent));
                } else {
                    resultList.add(new Bus(id, brand, model, vin, rent, personsAmount, wheelsCount));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;

    }
}