package pl.camp.it.db;

import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.Vehicle;

import java.io.*;
import java.util.List;

public class Persistance {
    private static final String fileName = "D:\\DOKUMENTY\\IdeaProjects\\Comarch\\2020-06-21.Sortowanie\\db\\carRentDb.txt";

    public static void saveData() {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            boolean flag = false;

            for (Vehicle tempVehicle : VehicleRepository.getRepository().getVehicles()) {
                StringBuilder sb = new StringBuilder();
                if (tempVehicle instanceof Car) {
                    Car c = (Car) tempVehicle;
                    sb.append("Car;")
                            .append(c.getId())
                            .append(";")
                            .append(c.getBrand())
                            .append(";")
                            .append(c.getModel())
                            .append(";")
                            .append(c.getVin())
                            .append(";")
                            .append(c.isRent());

                    if (flag) {
                        bufferedWriter.newLine();
                    }
                    flag = true;

                    bufferedWriter.append(sb.toString());

                } else {
                    Bus b = (Bus) tempVehicle;
                    sb.append("Bus;")
                            .append(b.getId())
                            .append(";")
                            .append(b.getBrand())
                            .append(";")
                            .append(b.getModel())
                            .append(";")
                            .append(b.getVin())
                            .append(";")
                            .append(b.isRent())
                            .append(";")
                            .append(b.getPersonsAmount())
                            .append(";")
                            .append(b.getWheelsCount());

                    if (flag) {
                        bufferedWriter.newLine();
                    }
                    flag = true;

                    bufferedWriter.append(sb.toString());
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData(List<Vehicle> list) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] tablica = line.split(";");
                if (tablica[0].equals("Car")) {
                    Car car = new Car(Integer.parseInt(tablica[1]),
                            tablica[2], tablica[3], tablica[4], Boolean.parseBoolean(tablica[5]));
                    list.add(car);
                } else {
                    Bus bus = new Bus(Integer.parseInt(tablica[1]),
                            tablica[2], tablica[3], tablica[4], Boolean.parseBoolean(tablica[5]),
                            Integer.parseInt(tablica[6]), Integer.parseInt(tablica[7]));
                    list.add(bus);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}