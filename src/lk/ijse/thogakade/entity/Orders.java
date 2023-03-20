package lk.ijse.thogakade.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
@Entity
@Table(name = "orders")
public class Orders implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @CreationTimestamp
    private Date date;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customerID", referencedColumnName = "id")
    private Customer customer;
    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<OrderDetail> orderDetailList;
    public Orders(int orderId, Date date, Customer customer) {
        this.orderId = orderId;
        this.date = date;
        this.customer = customer;
    }

}
