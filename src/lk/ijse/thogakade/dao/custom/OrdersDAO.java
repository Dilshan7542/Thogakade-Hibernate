package lk.ijse.thogakade.dao.custom;

import lk.ijse.thogakade.dao.CrudDAO;
import lk.ijse.thogakade.entity.Orders;
import org.hibernate.Session;

import java.sql.SQLException;

public interface OrdersDAO extends CrudDAO<Orders,Integer> {
    int getLastOrderID(Session session)throws SQLException;
}
