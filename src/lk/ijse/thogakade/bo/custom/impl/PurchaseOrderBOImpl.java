package lk.ijse.thogakade.bo.custom.impl;
import lk.ijse.thogakade.bo.custom.PurchaseOrderBO;
import lk.ijse.thogakade.dao.DAOFactory;
import lk.ijse.thogakade.dao.DAOType;
import lk.ijse.thogakade.dao.custom.ItemDAO;
import lk.ijse.thogakade.dao.custom.OrderDetailDAO;
import lk.ijse.thogakade.dao.custom.OrdersDAO;
import lk.ijse.thogakade.dao.custom.impl.ItemDAOImpl;
import lk.ijse.thogakade.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.thogakade.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.entity.Customer;
import lk.ijse.thogakade.entity.Item;
import lk.ijse.thogakade.entity.OrderDetail;
import lk.ijse.thogakade.entity.Orders;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.dto.OrdersDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    ItemDAO itemDAO=(ItemDAOImpl) DAOFactory.getInstance().getDAO(DAOType.ITEM);
    OrdersDAO orderDAO=(OrdersDAOImpl) DAOFactory.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO=(OrderDetailDAOImpl) DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
    Session session;
    Transaction transaction;
    @Override
    public boolean saveOrder(OrdersDTO ordersDTO) throws SQLException {
        openSession();

        final Customer customer = new Customer();
        customer.setId(ordersDTO.getCustomerID());
        final Orders save = orderDAO.save(session,new Orders(
                ordersDTO.getOrderId(),
                ordersDTO.getDate(),
                customer
        ));


        if(save!=null){
            if(!saveOrderDetailList(ordersDTO.getOrderDetailDTOList(),save.getOrderId())){

                transaction.rollback();
                session.close();
                return false;
            }else{
                final boolean isUpdated = updateItemQty(ordersDTO.getOrderDetailDTOList().stream().map(itm -> new ItemDTO(
                        itm.getCode(),
                        itm.getDescription(),
                        itm.getUnitPrice(),
                        itm.getQty()
                )).collect(Collectors.toList()));
                if (isUpdated){
                    System.out.println("errr");
                 closeSession();
                return isUpdated;

                }
            }
       }

        transaction.rollback();
        session.close();
       return false;
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException {
        final Orders orders = new Orders();
        orders.setOrderId(orderDetailDTO.getOrderId());
        final Item item = new Item();
            item.setItemCode(orderDetailDTO.getCode());
        final boolean isSaved= orderDetailDAO.save(session, new OrderDetail(
                orders,
                orderDetailDTO.getQty(),
                orderDetailDTO.getUnitPrice(),
                item)) != null;
        return isSaved;
    }

    @Override
    public boolean saveOrderDetailList(List<OrderDetailDTO> list,int orderID) throws SQLException {
        for (OrderDetailDTO or : list) {
            or.setOrderId(orderID);
            if(!saveOrderDetail(or)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateItemQty(ItemDTO item) throws SQLException {

        return itemDAO.updateItemQty(session,new Item(
                item.getCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()))!=null;
    }

    @Override
    public boolean updateItemQty(List<ItemDTO> itemList) throws SQLException {
        for (ItemDTO itm : itemList) {
            if(!updateItemQty(itm)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getLastOrderID() throws SQLException {
        openSession();
        final int lastOrderID = orderDAO.getLastOrderID(session);
        closeSession();
        return lastOrderID;
    }
    @Override
    public void openSession() {
        session= FactoryConfiguration.getInstance().getSession();
        transaction=session.beginTransaction();
        System.out.println("sesion On");
    }

    @Override
    public void closeSession() {
        System.out.println("sesion close");
        transaction.commit();
        session.close();
    }
}
