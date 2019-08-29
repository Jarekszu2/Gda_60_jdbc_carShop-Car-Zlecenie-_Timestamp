package packCarShop.packCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    private Integer nummerId;
    private String nummerReg;
    private int przebieg;
    private String markModel;
    private int year;
    private String type;
    private String userName;

    public Car(String nummerReg, int przebieg, String markModel, int year, String type, String userName) {
        this.nummerReg = nummerReg;
        this.przebieg = przebieg;
        this.markModel = markModel;
        this.year = year;
        this.type = type;
        this.userName = userName;
    }
}
