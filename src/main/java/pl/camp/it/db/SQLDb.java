package pl.camp.it.db;

import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.User;
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
                    if (vehicle instanceof Bus) {
                        Bus temp = (Bus) vehicle;
                        sql
                                .append(", ")
                                .append(temp.getPersonsAmount())
                                .append(", ")
                                .append(temp.getWheelsCount());
                    } else {
                        sql
                                .append(", ")
                                .append("NULL")
                                .append(", ")
                                .append("NULL");
                    }
                    sql.append(")");

            statement.executeUpdate(sql.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveVehicleWithPrepareStatement(Vehicle vehicle) {
        try {

            String sql = "INSERT INTO tvehicle (brand, model, vin, rent, personsAmount, wheelsCount) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, vehicle.getBrand());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getVin());
            preparedStatement.setBoolean(4, vehicle.isRent());

            if (vehicle instanceof Bus) {
                Bus bus = (Bus) vehicle;
                preparedStatement.setString(5, vehicle.getBrand());
                preparedStatement.setString(6, vehicle.getBrand());
            } else {
                preparedStatement.setNull(5, Types.NULL);
                preparedStatement.setNull(6, Types.NULL);
            }

            preparedStatement.executeUpdate();

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

                Integer personsAmount = wyniki.getInt("personsAmount");
                Integer wheelsCount = wyniki.getInt("wheelsCount");

                if (wyniki.wasNull()) {
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


    public static void updateVehicleRent(Vehicle vehicle) {
        try {
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder();
            sql
                    .append("UPDATE tvehicle SET rent = ")
                    .append(vehicle.isRent())
                    .append(" WHERE id = ")
                    .append(vehicle.getId());
            // TODO: 2020-06-28 Czy działa kwerenda bez średnika

            statement.executeUpdate(sql.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static User getUserByLogin(String login) {
        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM tuser WHERE login = '" + login + "'";

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public static User getUserByLoginWithPrepareStatement(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, login);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}