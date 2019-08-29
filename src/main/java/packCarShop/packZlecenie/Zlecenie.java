package packCarShop.packZlecenie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zlecenie {
    private Integer zlecenieId;
    private LocalDateTime dataDodania;
    private boolean czyZrealizowane;
    private LocalDateTime dataZrealizowania;
    private String trescZlecenia;
    private Integer carId;

    public Zlecenie(LocalDateTime dataDodania, String trescZlecenia, Integer carId) {
        this.dataDodania = LocalDateTime.now();
        this.czyZrealizowane = false;
        this.dataZrealizowania = null;
        this.trescZlecenia = trescZlecenia;
        this.carId = carId;
    }


}
