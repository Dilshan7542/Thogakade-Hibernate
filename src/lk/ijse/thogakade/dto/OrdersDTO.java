package lk.ijse.thogakade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdersDTO {
    private int orderId;
    private Date date;
    private int customerID;
    List<OrderDetailDTO> orderDetailDTOList;

}
