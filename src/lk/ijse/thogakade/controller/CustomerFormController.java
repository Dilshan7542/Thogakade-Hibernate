package lk.ijse.thogakade.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thogakade.bo.BOFactory;
import lk.ijse.thogakade.bo.BOType;
import lk.ijse.thogakade.bo.custom.CustomerBO;
import lk.ijse.thogakade.bo.custom.impl.CustomerBOImpl;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.util.Navigation;
import lk.ijse.thogakade.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    CustomerBO customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOType.CUSTOMER);
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;

    @FXML
    private TableView<CustomerDTO> tblCustomer;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colAction;
        ObservableList<CustomerDTO> obCustomerList= FXCollections.observableArrayList();
    public void initialize(){
        try {
            final List<CustomerDTO> allCustomer = customerBO.getAllCustomer();
                if(obCustomerList.size()>0){
                    obCustomerList.clear();
                }
            for (CustomerDTO c : allCustomer) {
                obCustomerList.add(c);
            }
            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));




            tblCustomer.setItems(obCustomerList);
            tblCustomer.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        CustomerDTO customerDTO = new CustomerDTO(id, name, address, salary);
        try {
            boolean isAdded = customerBO.saveCustomer(customerDTO);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "CustomerDTO Added!").show();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {

        try {
            CustomerDTO customerDTO = customerBO.SearchCustomer(Integer.parseInt(txtId.getText()));
            if (customerDTO != null) {
                fillData(customerDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(CustomerDTO customerDTO) {
        txtId.setText(customerDTO.getId()+"");
        txtName.setText(customerDTO.getName());
        txtAddress.setText(customerDTO.getAddress());
        txtSalary.setText(String.valueOf(customerDTO.getSalary()));
    }
}
