package lk.ijse.thogakade.bo.custom;

import lk.ijse.thogakade.bo.SuperBo;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseOrderBO extends SuperBo {
    boolean saveOrder(OrdersDTO ordersDTO)throws SQLException;
    boolean saveOrderDetail(OrderDetailDTO orderDetailDTO)throws SQLException;
    boolean saveOrderDetailList(List<OrderDetailDTO> list,int orderID)throws SQLException;
    boolean updateItemQty(ItemDTO item)throws SQLException;
    boolean updateItemQty(List<ItemDTO> itemList)throws SQLException;
    int getLastOrderID()throws SQLException;

}
