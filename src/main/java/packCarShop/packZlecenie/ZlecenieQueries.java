package packCarShop.packZlecenie;

public interface ZlecenieQueries {
    String CREATE_Z_TABLE_QUERY = "create table if not exists `zlecenia` (\n" +
            "`zlecenieId` int not null auto_increment primary key,\n" +
            "`dataDodania` datetime not null,\n" + //default now();
            "`czyZrealizowane` tinyint not null,\n" +
            "`dataZrealizowania` datetime ,\n" +
            "`trescZlecenia` varchar(255) not null,\n" +
            "`carId` int not null,\n" +
            " foreign key (carId) references cars (nummerId))";

    String INSERT_Z_QUERY = "insert into zlecenia\n" +
            "(dataDodania, trescZlecenia, carId, czyZrealizowane, dataZrealizowania)\n" +
            "values(now(), ?, ?, 0, null);";

    String DELETE_Z_QUERY = "delete from `zlecenia` where `zlecenieId` = ?";

    String SELECT_Z_ALL_QUERY = "select * from `zlecenia`";
}
