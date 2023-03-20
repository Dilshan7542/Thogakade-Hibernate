package lk.ijse.thogakade.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
@Entity
public class OrderDetail implements SuperEntity{
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID",referencedColumnName = "orderID")
    private Orders orders;
    private int qty;
    private double unitPrice;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="itemCode",referencedColumnName = "itemCode")
    private Item item;

}
