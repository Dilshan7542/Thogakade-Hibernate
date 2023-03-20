package lk.ijse.thogakade.dao.custom.impl;

import lk.ijse.thogakade.dao.custom.ItemDAO;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.entity.Item;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public Item save(Session session,Item entity) throws SQLException {
            session.save(entity);
            return entity;
    }

    @Override
    public Item update(Session session,Item entity) throws SQLException {
            session.update(entity);
            return entity;
    }

    @Override
    public boolean delete(Session session,Integer integer) throws SQLException {
                session.delete(session.get(Item.class,integer));
                return session.get(Item.class,integer)==null;
    }

    @Override
    public Item search(Session session,Integer integer) throws SQLException {
        return  session.get(Item.class, integer);
    }

    @Override
    public List<Item> getAll(Session session) throws SQLException {
        final List<Item> list = session.createQuery("FROM Item").list();
        return list;

    }

    @Override
    public Item updateItemQty(Session session,Item item) throws SQLException {
            session.update(item);
            return item;
    }

    @Override
    public boolean updateItemQty(Session session,List<Item> itemList) throws SQLException {
        for (Item item : itemList) {
            if(updateItemQty(session,item)==null){
                return false;
            }
        }
        return true;
    }
}
