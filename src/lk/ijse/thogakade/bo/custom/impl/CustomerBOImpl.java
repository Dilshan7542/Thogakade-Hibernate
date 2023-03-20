package lk.ijse.thogakade.bo.custom.impl;

import lk.ijse.thogakade.bo.custom.CustomerBO;
import lk.ijse.thogakade.dao.DAOFactory;
import lk.ijse.thogakade.dao.DAOType;
import lk.ijse.thogakade.dao.custom.CustomerDAO;
import lk.ijse.thogakade.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.thogakade.dao.util.FactoryConfiguration;
import lk.ijse.thogakade.entity.Customer;
import lk.ijse.thogakade.dto.CustomerDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO=(CustomerDAOImpl) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    Session session;
    Transaction transaction;
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        openSession();
        final Customer save = customerDAO.save(session, new Customer(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getSalary()
        ));
        closeSession();
            return save!=null ? true:false;
    }

    @Override
    public CustomerDTO SearchCustomer(Integer id) throws SQLException {
        openSession();
        final Customer search = customerDAO.search(session,id);
        closeSession();
        return new CustomerDTO(
                search.getId(),
                search.getName(),
                search.getAddress(),
                search.getSalary()
        );
    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException {
        openSession();
        final List<CustomerDTO> list = customerDAO.getAll(session).stream().map(c -> new CustomerDTO(
                c.getId(),
                c.getName(),
                c.getAddress(),
                c.getSalary())).collect(Collectors.toList());
        closeSession();
        for (CustomerDTO customerDTO : list) {
            System.out.println(customerDTO);
        }
        return list;
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
