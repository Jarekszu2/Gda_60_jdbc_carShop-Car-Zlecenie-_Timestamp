package packCarShop.packCar;

public interface CarQueries {
    String CREATE_TABLE_QUERY = "create table if not exists `cars` (\n" +
            "`nummerId` int not null auto_increment primary key,\n" +
            "`nummerReg` varchar(255) not null,\n" +
            "`przebieg` int not null,\n" +
            "`markModel` varchar(255) not null,\n" +
            "`year` int not null,\n" +
            "`type` varchar(255) not null,\n" +
            "`userName` varchar(255) not null);";

    String INSERT_QUERY = "insert into cars\n" +
            "(nummerReg, przebieg, markModel, year, type, userName)\n" +
            "values(?, ?, ?, ?, ?, ?);";

    String DELETE_QUERY = "delete from `cars` where `nummerId` = ?";

    String SELECT_ALL_QUERY = "select * from `cars`";

    String SELECT_BY_NAME_QUERY = "select * from `cars` where `userName` = ?";

    String SELECT_WHERE_NUMMERREG_LIKE_QUERY = "select * from `cars` where `nummerReg` like ?";

    String SELECT_BY_MARKMODEL_LIKE_QUERY = "select * from `cars` where `markModel` like ?";
}
