package lk.ijse.thogakade.dao.custom.impl;

import lk.ijse.thogakade.dao.custom.OrdersDAO;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.entity.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.Order;
import java.sql.SQLException;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public Orders save(Session session,Orders entity) throws SQLException {
        System.out.println(entity.getOrderId()+"****---------");
      session.save(entity);
        System.out.println(entity.getOrderId()+"****");

     return entity;
    }

    @Override
    public Orders update(Session session,Orders entity) throws SQLException {
        session.update(entity);
        return session.get(Orders.class,entity.getOrderId());
    }

    @Override
    public boolean delete(Session session,Integer integer) throws SQLException {
        session.delete(session.get(Orders.class,integer));
        return session.get(Orders.class,integer)==null;
    }

    @Override
    public Orders search(Session session,Integer integer) throws SQLException {
        return session.get(Orders.class, integer);
    }

    @Override
    public List<Orders> getAll(Session session) throws SQLException {
        final List<Orders> list = session.createQuery("FROM Orders").list();
        return list;
    }

    @Override
    public int getLastOrderID(Session session) throws SQLException {
        final Query query = session.createQuery("FROM Orders ORDER BY orderId DESC").setMaxResults(1);
        final List<Orders> list = query.list();
        for (Orders orders : list) {
            return orders.getOrderId();
        }
           return -1;
    }
}
