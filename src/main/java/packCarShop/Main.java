package packCarShop;

/*
Uwaga: modyfikacja dla kilku tabel:
1. wersja: po prostu dodajemy kolejne klasy i ich dopełnienia(interfejsy)
   - jdbc.properties, MysqlConnectionParameters, MysqlConnection pozostają wspólne dla wszystkich
   - tworzymy nową klasę Name2 dla nowej tabeli w database (np. jest tabela Car, dodajemy do niej tab. Zlecenia)
     i dla tej klasy tworzymy INTERFACE!!! - Name2Queries, klasę - Name2Dao
   - w Mainie tworzymy następną zmienną Name2Dao name2Dao i dodajemy ją do tray-catcha name2Dao = new Name2Dao();
   - uzupełniamy parsera o nowe funkcjonalności

2. wersja:
   - jak wyżej tworzymy klasy dla nowej tabeli, jej klasęDao i interfejs z pytaniami, ale:
     konstruktor w klasachDao robimy typowy z polem: MysqlConnection mysqlConnection; i throws SQLException;
     (plus create table - żeby nam utworzył tablicę)
   - w Mainie definiujemy zmienne klasaDao i w try-catch'u dodajemy połączenie
     MysqlConnection mysqlConnection = new MysqlConnection(); oraz przypisujemy zmiennym połączenie
     klasa1Dao = new klasa1Dao(mysqlConnection);
 */

import packCarShop.packCar.Car;
import packCarShop.packCar.CarDao;
import packCarShop.packZlecenie.ZleceniaDao;
import packCarShop.packZlecenie.Zlecenie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
Konsolowa aplikacja do dodawania i usuwania samochodów w bazie danych.
- id
- nr rejestracyjny
- przebieg
- marka i model
- rocznik
- typ (enum KOMBI, SEDAN, HATCHBACK, CABRIO, SUV) (int / varchar)
- nazwisko wlasciciela
Metody:
- dodaj samochód
- usuń samochód po identyfikatorze
- usun samochod po numerze rejestracyjnym (który powinien być unikalny)
- listowanie rekordów
- szukanie rekordów po rejestracji (Like)
- szukanie rekordów po nazwisku właściciela
- szukanie po marce/modelu

Dodaj tabelę relacyjną z kluczem obcym w tabeli Car:
Klasa Zlecenie (nie nazywaj klasy Order słowo kluczowe order jest zarezerwowane wewnątrz mysql):
- data dodania
- czy zrealizowane (boolean)
- data zrealizowania
- treść zlecenia
- id samochodu z którym mamy relację
Możliwe czynności które można wykonać:
- listuj wszystkie zlecenia samochodu po id
- dodaj zlecenie (samochodowi o id)
- oznacz zlecenie jako zrealizowane (ustawia czas realizacji jako moment wywołania) (do zrealizowania zlecenia potrzebujemy id samochodu i id zlecenia)
- listuj zlecenia niezrealizowane
- listuj zlecenia zrealizowane
- listuj zlecenia z ostatniego tygodnia.

 */
public class Main {
    public static void main(String[] args) {
        System.out.println();

        ScannerWork scannerWork = new ScannerWork();

        CarDao carDao;
        ZleceniaDao zleceniaDao;
//        try {
//            carDao = new CarDao();
//            zleceniaDao = new ZleceniaDao();
        try {MysqlConnection mysqlConnection = new MysqlConnection();
            carDao = new CarDao(mysqlConnection);
            zleceniaDao = new ZleceniaDao(mysqlConnection);
        } catch (SQLException e) {
            System.err.println("Car dao cannot be created. Mysql error.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.err.println("Configuration file error.");
            System.err.println("Error: " + e.getMessage());
            return;
        }


        System.out.println("Program zarządza bazą danych CarShop.");
        System.out.println();
        System.out.println("Wybierz działanie:\n a) dodaj car\n b) wylistuj wszystkie" +
                "\n c) usuń po nrId\n d) wylistuj po nazwisku właściciela\n e) wylistuj po nr rejestracyjnym" +
                "\n f) listuj po marce/modelu\n g) dodaj zlecenie\n h) listuj wszystkie zlecenia w) koniec");
        boolean flag = false;
        do {
            System.out.println();
            System.out.println("Wybierz: a(dodaj car), b(wylistuj wszystkie), c(usuń po nrId)," +
                    "\n         d(wylistuj po nazwisku), e(listuj po nr rej.), f(listuj po marce/modelu)" +
                    "\n         g(dodaj zlecenie), w(koniec)");
            char znak = scannerWork.getChar();
            switch (znak) {
                case 'a':
                    System.out.println("Entry car data:");
                    System.out.println();
                    Car carA = scannerWork.buildCar();
                    try {
                        carDao.insertCar(carA);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'b':
                    try {
                        List<Car> carListB = carDao.listAllCars();
                        scannerWork.printCarsList(carListB);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'c':
                    System.out.println("Entry nummerId for deleted car:");
                    int deletedId = scannerWork.getInt();
                    try {
                        carDao.deleteCarById(deletedId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'd':
                    System.out.println("Entry usersName for select:");
                    String usersNameD = scannerWork.getStringLine();
                    try {
                        List<Car> carListD = carDao.getListByCarUsersName(usersNameD);
                        scannerWork.printCarsList(carListD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'e':
                    System.out.println("Entry nummerReg for select:");
                    String nummerRegE = scannerWork.getStringLine();
                    try {
                        List<Car> carListE = carDao.listWhereNummerRegLike(nummerRegE);
                        scannerWork.printCarsList(carListE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'f':
                    System.out.println("Entry markModel for select:");
                    String markModelF = scannerWork.getStringLine();
                    try {
                        List<Car> carListF = carDao.listWhereMarkModelLike(markModelF);
                        scannerWork.printCarsList(carListF);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'g':
                    System.out.println("Wprowadź zlecenie:");
                    Zlecenie zlecenieG = scannerWork.buildZlecenie();
                    try {
                        zleceniaDao.insertZlecenie(zlecenieG);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'h':
                    try {
                        List<Zlecenie> zlecenieListH = zleceniaDao.listAllZlecenies();
                        scannerWork.printZleceniesList(zlecenieListH);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'w':
                    flag = true;
                    break;
            }
        } while (!flag);
    }
}
