package lk.ijse.thogakade.bo.custom.impl;

import lk.ijse.thogakade.bo.custom.ItemBO;
import lk.ijse.thogakade.dao.DAOFactory;
import lk.ijse.thogakade.dao.DAOType;
import lk.ijse.thogakade.dao.custom.ItemDAO;
import lk.ijse.thogakade.dao.custom.impl.ItemDAOImpl;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.entity.Item;
import lk.ijse.thogakade.dto.ItemDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO=(ItemDAOImpl)DAOFactory.getInstance().getDAO(DAOType.ITEM);
    Session session;
    Transaction transaction;
    @Override
    public boolean saveItem(ItemDTO item) throws SQLException {
        openSession();
        final Item save = itemDAO.save(session,new Item(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        ));
        closeSession();
        return save != null;
    }

    @Override
    public boolean updateItemQty(ItemDTO item) throws SQLException {
        openSession();
        final Item update = itemDAO.updateItemQty(session,new Item(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        ));
        closeSession();
        return update != null;
    }

    @Override
    public boolean updateItemQty(List<ItemDTO> itemList) throws SQLException {
        openSession();
        final boolean isUpdate = itemDAO.updateItemQty(session, itemList.stream().map(i -> new Item(
                i.getCode(),
                i.getDescription(),
                i.getUnitPrice(),
                i.getQtyOnHand())).collect(Collectors.toList()));
        closeSession();
        return isUpdate;

    }

    @Override
    public boolean deleteItem(int id) throws SQLException {
        openSession();
        final boolean delete = itemDAO.delete(session, id);
        closeSession();
        return delete;

    }

    @Override
    public List<ItemDTO> getAllItem() throws SQLException {
        openSession();
        final List<ItemDTO>list = itemDAO.getAll(session).stream().map(i -> new ItemDTO(
                i.getItemCode(),
                i.getDescription(),
                i.getUnitPrice(),
                i.getQtyOnHand())).collect(Collectors.toList());
        closeSession();
        return list;
    }

    @Override
    public ItemDTO searchItem(int id) throws SQLException {
        openSession();
        final Item search =itemDAO.search(session,id);
        final ItemDTO itemDTO = new ItemDTO(
                search.getItemCode(),
                search.getDescription(),
                search.getUnitPrice(),
                search.getQtyOnHand()
        );
        closeSession();
        return itemDTO;
    }
    @Override
    public void openSession() {
        session= FactoryConfiguration.getInstance().getSession();
        transaction=session.beginTransaction();
    }

    @Override
    public void closeSession() {
        transaction.commit();
        session.close();
    }
}
