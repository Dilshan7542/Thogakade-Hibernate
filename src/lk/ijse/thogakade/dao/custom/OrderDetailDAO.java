package lk.ijse.thogakade.dao.custom;

import lk.ijse.thogakade.dao.CrudDAO;
import lk.ijse.thogakade.entity.OrderDetail;
import lk.ijse.thogakade.dto.OrdersDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,Integer> {
      boolean saveOrderList(Session session, List<OrderDetail> list)throws SQLException;
}
