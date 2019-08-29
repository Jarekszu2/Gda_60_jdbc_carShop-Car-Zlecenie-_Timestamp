package packCarShop;

import packCarShop.packCar.Car;
import packCarShop.packCar.CarType;
import packCarShop.packZlecenie.Zlecenie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ScannerWork {
    Scanner scanner = new Scanner(System.in);

    public char getChar() {
//        System.out.println();
        System.out.println("Podaj znak:");
        char znak = scanner.nextLine().charAt(0);
        return  znak;
    }

    public String getCharTypeABCDE() {
        boolean flag = false;
        String carType = "";
        do {
//            getCarTypeList();
            System.out.println("Wybierz: a(KOMBI),b(SEDAN),c(HATCHBACK),d(CABRIO),e(SUV) / ?");
            char znak = scanner.nextLine().charAt(0);
            switch (znak) {
                case 'a':
                    System.out.println("Wybrano: KOMBI");
                    carType = "KOMBI";
                    flag = true;
                    break;
                case 'b':
                    System.out.println("Wybrano: SEDAN");
                    carType = "SEDAN";
                    flag = true;
                    break;
                case 'c':
                    System.out.println("Wybrano: HATCHBACK");
                    carType = "HATCHBACK";
                    flag = true;
                    break;
                case 'd':
                    System.out.println("Wybrano: CABRIO");
                    carType = "Cabrio";
                    flag = true;
                    break;
                case 'e':
                    System.out.println("Wybrano: SUV");
                    carType = "SUV";
                    flag = true;
                    break;
            }
        } while (!flag);
        return carType;
    }

    public String getStringLine() {
//        System.out.println();
        System.out.println("Podaj tekst:");
        String nameLine = scanner.nextLine();
        return nameLine;
    }

    public int getInt() {
//        System.out.println();
        System.out.println("Podaj liczbę int:");
        String liczbaString = scanner.nextLine();
        int liczba = Integer.valueOf(liczbaString);
        return liczba;
    }

    public void printCarsList(List<Car> carList) {
        carList.forEach(System.out::println);
    }
    public void printZleceniesList(List<Zlecenie> zlecenieList) {
        zlecenieList.forEach(System.out::println);
    }

    public Car buildCar() {
        System.out.println("Get nummerReg:");
        String nummerReg = getStringLine();
        System.out.println("Get przebieg:");
        int przebieg = getInt();
        System.out.println("Get markModel:");
        String markModel = getStringLine();
        System.out.println("Get year:");
        int year = getInt();
        System.out.println("Get type:");
        String type = getCharTypeABCDE();
        System.out.println("Get userName:");
        String userName = getStringLine();

        Car car = new Car(nummerReg, przebieg, markModel, year, type, userName);

        return car;
    }

    public Zlecenie buildZlecenie() {
        LocalDateTime dataDodania = LocalDateTime.now();
//        boolean czyZrealizowane = false;
//        LocalDateTime dataZrealizowania = null;
        System.out.println("Podaj treść zlecenia:");
        String trescZlecenia = getStringLine();
        System.out.println("Podaj carId:");
        int carId = getInt();

        Zlecenie zlecenie = new Zlecenie(dataDodania, trescZlecenia, carId);

        return zlecenie;
    }

    public void getCarTypeList() {
        CarType[] carTypes = CarType.values();
        for (int i = 0; i < carTypes.length; i++) {
            System.out.print(carTypes[i] + " ");
        }
    }
}
