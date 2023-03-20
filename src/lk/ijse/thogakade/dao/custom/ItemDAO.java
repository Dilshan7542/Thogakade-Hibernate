package lk.ijse.thogakade.dao.custom;

import lk.ijse.thogakade.dao.CrudDAO;
import lk.ijse.thogakade.entity.Item;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,Integer> {
    Item updateItemQty(Session session, Item item)throws SQLException;
    boolean updateItemQty(Session session,List<Item> itemList)throws SQLException;
}
