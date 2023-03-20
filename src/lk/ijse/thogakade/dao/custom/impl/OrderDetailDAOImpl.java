package lk.ijse.thogakade.dao.custom.impl;
import lk.ijse.thogakade.dao.custom.OrderDetailDAO;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.entity.OrderDetail;
import lk.ijse.thogakade.entity.Orders;
import org.hibernate.Session;

import javax.persistence.criteria.Order;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public OrderDetail save(Session session,OrderDetail entity) throws SQLException {
        System.out.println("is here");
        System.out.println(entity);
            session.save(entity);
            return entity;
    }

    @Override
    public OrderDetail update(Session session,OrderDetail entity) throws SQLException {
        session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session,Integer integer) throws SQLException {
            final OrderDetail orderDetail = new OrderDetail();
            final Orders orders = new Orders();
            orders.setOrderId(integer);
            orderDetail.setOrders(orders);
            session.delete(orderDetail);
            return session.get(OrderDetail.class,integer)==null;

    }

    @Override
    public OrderDetail search(Session session,Integer integer) throws SQLException {
        return session.get(OrderDetail.class, integer);

    }
    @Override
    public List<OrderDetail> getAll(Session session) throws SQLException {
        final List<OrderDetail> list = session.createQuery("FROM OrderDetail").list();
        return list;
    }
    @Override
    public boolean saveOrderList(Session session,List<OrderDetail> list) throws SQLException {
        for (OrderDetail o : list) {
         if(save(session,o)==null){
             return false;
         }
        }
        return true;
    }
}
