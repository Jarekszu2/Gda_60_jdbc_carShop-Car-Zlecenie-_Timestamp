package packCarShop.packCar;

import packCarShop.MysqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static packCarShop.packCar.CarQueries.*;

public class CarDao {
    private MysqlConnection mysqlConnection;

//    public CarDao() throws SQLException, IOException {
//        mysqlConnection = new MysqlConnection();
//        createTableIfNotExists();
//    }


    public CarDao(MysqlConnection mysqlConnection) throws SQLException {
        this.mysqlConnection = mysqlConnection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertCar(Car car) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, car.getNummerReg());
                statement.setInt(2, car.getPrzebieg());
                statement.setString(3, car.getMarkModel());
                statement.setInt(4, car.getYear());
                statement.setString(5, car.getType());
                statement.setString(6, car.getUserName());

//                boolean success = statement.execute(); // jeśli interesuje nas czy w wyniku otrzymaliśmy dane
                int affectedRecords = statement.executeUpdate();  // jeśli interesuje nas ile rekordów zostało zmienionych

                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    Integer generatedId = resultSet.getInt(1);
                    System.out.println("Został utworzony rekord o identyfikatorze: " + generatedId);
                }
            }
        }
    }

    public List<Car> listAllCars() throws SQLException {
        List<Car> carList = new ArrayList<>();

        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();

                loadMultipleCarsFromResultSet(carList, resultSet);
            }
        }
        return carList;
    }

    private Car loadCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();

        car.setNummerId(resultSet.getInt(1));
        car.setNummerReg(resultSet.getString(2));
        car.setPrzebieg(resultSet.getInt(3));
        car.setMarkModel(resultSet.getString(4));
        car.setYear(resultSet.getInt(5));
        car.setType(resultSet.getString(6));
        car.setUserName(resultSet.getString(7));
        return car;
    }

    private void loadMultipleCarsFromResultSet(List<Car> carList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Car car = loadCarFromResultSet(resultSet);

            carList.add(car);
        }
    }

    public boolean deleteCarById(int deletedId) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, deletedId);

//                boolean success = statement.execute();
                statement.execute();

                System.out.println("Usunięto studenta o id = " + deletedId);

//                Statement.RETURN_GENERATED_KEYS // do dodania w try (PreparedStatemenr...
//                int affectedRecords = statement.executeUpdate();
//
//                if (affectedRecords > 0) {
//                    // usuneliśmy rekord
//                    System.out.println("Usunięto studenta o id: " + deletedId);
//                    System.out.println("Ilość zmienionych rekordów: " + affectedRecords);
//                    return true;
//                }
            }
        }
        return false;
    }

    public List<Car> getListByCarUsersName(String searchedName) throws SQLException {
        return getCars(searchedName, SELECT_BY_NAME_QUERY);
    }

    public List<Car> listWhereNummerRegLike(String nameLike) throws SQLException {
        return getCars(nameLike, SELECT_WHERE_NUMMERREG_LIKE_QUERY);
    }

    public List<Car> listWhereMarkModelLike(String nameLike) throws SQLException {
        return getCars(nameLike, SELECT_BY_MARKMODEL_LIKE_QUERY);
    }

    private List<Car> getCars(String nameLike, String selectWhereNummerregLikeQuery) throws SQLException {
        List<Car> carList = new ArrayList<>();

        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(selectWhereNummerregLikeQuery)) {
                statement.setString(1, "%" + nameLike + "%");

                ResultSet resultSet = statement.executeQuery();

                loadMultipleCarsFromResultSet(carList, resultSet);
            }
        }
        return carList;
    }
}
