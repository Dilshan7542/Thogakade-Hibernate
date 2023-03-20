package lk.ijse.thogakade.bo.custom;

import lk.ijse.thogakade.bo.SuperBo;
import lk.ijse.thogakade.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBo {
        boolean saveItem(ItemDTO item)throws SQLException;
        boolean updateItemQty(ItemDTO item)throws SQLException;
        boolean updateItemQty(List<ItemDTO> itemList)throws SQLException;
        boolean deleteItem(int id)throws SQLException;
        List<ItemDTO> getAllItem()throws SQLException;
        ItemDTO searchItem(int id)throws SQLException;

}
