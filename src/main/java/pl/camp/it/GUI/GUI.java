package pl.camp.it.GUI;

import org.apache.commons.codec.digest.DigestUtils;
import pl.camp.it.db.SQLDb;
import pl.camp.it.db.VehicleRepository;
import pl.camp.it.model.User;
import pl.camp.it.model.Vehicle;

import java.util.Scanner;

public class GUI {

    private static final Scanner scanner = new Scanner(System.in);

    public GUI() {
    }

    public static void showMainMenu() {
        System.out.println("1. Dostępne samochody");
        System.out.println("2. Wypożycz samochód");
        System.out.println("3. Exit");

        String choose = scanner.nextLine();

        switch (choose) {
            case "1":
                showCars();
                break;
            case "2":
                rentCar();
                break;
            case "3":
                //Persistance.saveData();
                SQLDb.closeConnection();
                System.exit(0);
            default:
                System.out.println("Nieprawidłowy wybór !!");
                showMainMenu();
                break;
        }
    }

    private static void showCars() {
        for (Vehicle tempVehicle : VehicleRepository.getRepository().getVehicles()) {
            if (tempVehicle != null && !tempVehicle.isRent()) {
                System.out.println(tempVehicle);
            }

        }
        showMainMenu();
    }

    private static void rentCar() {
        System.out.println("Wpisz id samochodu:");
        String carId = scanner.nextLine();
        for (Vehicle tempVehicle : VehicleRepository.getRepository().getVehicles()) {
            try {
                if (tempVehicle != null && tempVehicle.getId() == Integer.parseInt(carId)) {
                    if (!tempVehicle.isRent()) {
                        tempVehicle.setRent(true);
                        SQLDb.updateVehicleRent(tempVehicle);
                        System.out.println("Udało się wypożyczyć");
                    } else {
                        System.out.println("Auto niedostępne");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadzono błęde id");
                rentCar();
            }
        }
        showMainMenu();
    }

    public static void showLoginScreen() {
        System.out.println("Podaj login:");
        String login = scanner.nextLine();
        System.out.println("Podaj hasło:");
        String password = scanner.nextLine();

        User userFromDataBase = SQLDb.getUserByLoginWithPrepareStatement(login);
        if (userFromDataBase == null) {
            showLoginScreen();
        } else {
            String hashedPassword = DigestUtils.md5Hex(password);

            if (hashedPassword.equals(userFromDataBase.getPassword())) {
                showMainMenu();
            } else {
                showLoginScreen();
            }
        }
    }
}