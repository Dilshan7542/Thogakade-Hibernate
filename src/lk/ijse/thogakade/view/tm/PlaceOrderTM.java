package lk.ijse.thogakade.view.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PlaceOrderTM {
    private int code;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btnDelete;


    public int getCode() {
        return code;
    }


}
