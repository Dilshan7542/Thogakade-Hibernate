package lk.ijse.thogakade.bo.custom;

import lk.ijse.thogakade.bo.SuperBo;
import lk.ijse.thogakade.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBo {
    boolean saveCustomer(CustomerDTO customerDTO)throws SQLException;
    CustomerDTO SearchCustomer(Integer id)throws SQLException;
    List<CustomerDTO> getAllCustomer()throws SQLException;

}

