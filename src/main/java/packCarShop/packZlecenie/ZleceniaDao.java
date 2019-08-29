package packCarShop.packZlecenie;

import packCarShop.MysqlConnection;
import packCarShop.packCar.Car;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;
import static packCarShop.packCar.CarQueries.INSERT_QUERY;
import static packCarShop.packCar.CarQueries.SELECT_ALL_QUERY;
import static packCarShop.packZlecenie.ZlecenieQueries.*;

public class ZleceniaDao {
    private MysqlConnection mysqlConnection;

//    public ZleceniaDao() throws SQLException, IOException {
//        mysqlConnection = new MysqlConnection();
//        createTableIfNotExists();
//    }


    public ZleceniaDao(MysqlConnection mysqlConnection) throws SQLException {
        this.mysqlConnection = mysqlConnection;
        createTableZIfNotExists();
    }

    private void createTableZIfNotExists() throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_Z_TABLE_QUERY)) {
                statement.execute();
            }
        }
    }

    public void insertZlecenie(Zlecenie zlecenie) throws SQLException {
        try (Connection connection = mysqlConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(INSERT_Z_QUERY, Statement.RETURN_GENERATED_KEYS)) {
//                statement.setDate(1, java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
                statement.setString(1, zlecenie.getTrescZlecenia());
                statement.setInt(2, zlecenie.getCarId());

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

    public List<Zlecenie> listAllZlecenies() throws SQLException {
        List<Zlecenie> zlecenieList = new ArrayList<>();

        try (Connection connection = mysqlConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_Z_ALL_QUERY)) {
                ResultSet resultSet = statement.executeQuery();

                loadMultipleZleceniesFromResultSet(zlecenieList, resultSet);
            }
        }
        return zlecenieList;
    }

    private Zlecenie loadZlecenieFromResultSet(ResultSet resultSet) throws SQLException {
        Zlecenie zlecenie = new Zlecenie();

        zlecenie.setZlecenieId(resultSet.getInt(1));
        Timestamp dateDodania = resultSet.getTimestamp(2);
        LocalDateTime dateTime;
        if(dateDodania != null){
            dateTime = dateDodania.toLocalDateTime();
            zlecenie.setDataDodania(dateTime);
        }
        zlecenie.setCzyZrealizowane(resultSet.getBoolean(3));
        Timestamp dateZrealizowania = resultSet.getTimestamp(4);
        if(dateZrealizowania != null){
            dateTime = dateZrealizowania.toLocalDateTime();
            zlecenie.setDataZrealizowania(dateTime);
        }
        zlecenie.setTrescZlecenia(resultSet.getString(5));
        zlecenie.setCarId(resultSet.getInt(6));
        return zlecenie;
    }

    private void loadMultipleZleceniesFromResultSet(List<Zlecenie> zlecenieList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Zlecenie zlecenie = loadZlecenieFromResultSet(resultSet);

            zlecenieList.add(zlecenie);
        }
    }
}
