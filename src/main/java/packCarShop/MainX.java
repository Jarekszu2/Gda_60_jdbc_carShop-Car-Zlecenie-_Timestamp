package packCarShop;

import packCarShop.packCar.CarType;


public class MainX {
    public static void main(String[] args) {
        System.out.println();

        CarType[] carTypes = CarType.values();
        for (CarType carType : carTypes) {
            System.out.print(carType + " ");
        }
        System.out.println();
        System.out.println(carTypes[2]);
    }
}
