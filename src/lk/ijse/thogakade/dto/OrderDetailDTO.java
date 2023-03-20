package lk.ijse.thogakade.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class OrderDetailDTO {
    private int orderId;
    private int code;
    private int qty;
    private String description;
    private double unitPrice;

}
