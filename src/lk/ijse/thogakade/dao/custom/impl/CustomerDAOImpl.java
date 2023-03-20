package lk.ijse.thogakade.dao.custom.impl;

import lk.ijse.thogakade.dao.custom.CustomerDAO;
import lk.ijse.thogakade.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer save(Session session, Customer entity) throws SQLException {
        session.save(entity);
        return entity;
    }

    @Override
    public Customer update(Session session, Customer entity) throws SQLException {
        session.update(entity);
        return entity;
    }


    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
        session.delete(session.get(Customer.class, integer));
        return session.get(Customer.class, integer) == null;

    }

    @Override
    public Customer search(Session session, Integer integer) throws SQLException {
        return session.get(Customer.class, integer);


    }

    @Override
    public List<Customer> getAll(Session session) throws SQLException {
        final Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        return list;

    }
}
