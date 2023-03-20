package lk.ijse.thogakade.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thogakade.bo.BOFactory;
import lk.ijse.thogakade.bo.BOType;
import lk.ijse.thogakade.bo.custom.CustomerBO;
import lk.ijse.thogakade.bo.custom.ItemBO;
import lk.ijse.thogakade.bo.custom.PurchaseOrderBO;
import lk.ijse.thogakade.bo.custom.impl.CustomerBOImpl;
import lk.ijse.thogakade.bo.custom.impl.ItemBOImpl;
import lk.ijse.thogakade.bo.custom.impl.PurchaseOrderBOImpl;
import lk.ijse.thogakade.dto.CustomerDTO;
import lk.ijse.thogakade.dto.ItemDTO;
import lk.ijse.thogakade.dto.OrderDetailDTO;
import lk.ijse.thogakade.dto.OrdersDTO;
import lk.ijse.thogakade.util.Navigation;
import lk.ijse.thogakade.util.Routes;
import lk.ijse.thogakade.view.tm.PlaceOrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class PlaceOrderFormController implements Initializable {
    CustomerBO customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOType.CUSTOMER);
    ItemBO itemBO = (ItemBOImpl) BOFactory.getInstance().getBO(BOType.ITEM);
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBOImpl) BOFactory.getInstance().getBO(BOType.PURCHASE_ORDER);
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private JFXComboBox<CustomerDTO> cmbCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private JFXComboBox<ItemDTO> cmbItemCode;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private TextField txtQty;

    @FXML
    private TableView<PlaceOrderTM> tblOrderCart;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colAction;
    private final ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadOrderDate();
        loadCustomerIds();
        loadNextOrderId();
        loadItemCodes();
        setCellValueFactory();
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
       ItemDTO itemDTO = cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String desc = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = unitPrice * qty;
        Button btnDelete = new Button("Delete");

        txtQty.setText("");

        if (!obList.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row dto the table */
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(itemDTO.getCode())) {
                    qty += (int) colQty.getCellData(i);
                    total = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);
                    tblOrderCart.refresh();
                    return;
                }
            }
        }

        /* set delete button dto some action before it put on obList */
        btnDelete.setOnAction((e) -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                PlaceOrderTM tm = tblOrderCart.getSelectionModel().getSelectedItem();
                /*
                netTot = Double.parseDouble(txtNetTot.getText());
                netTot = netTot - tm.getTotalPrice();
                */

                tblOrderCart.getItems().removeAll(tblOrderCart.getSelectionModel().getSelectedItem());
            }
        });
        obList.add(new PlaceOrderTM(itemDTO.getCode(), desc, qty, unitPrice, total, btnDelete));
        tblOrderCart.setItems(obList);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        int orderId = Integer.parseInt(lblOrderId.getText());
        final CustomerDTO customer = cmbCustomerId.getValue();

        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        /* load all cart items' dto orderDetails arrayList */
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            /* get each row details dto (PlaceOrderTm)tm in each time and add them dto the orderDetails */
            PlaceOrderTM tm = obList.get(i);
            orderDetailDTOS.add(new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getDescription(), tm.getUnitPrice()));
        }
        final OrdersDTO ordersDTO = new OrdersDTO(orderId, Date.valueOf(LocalDate.now()),customer.getId(), orderDetailDTOS);
        try {

            for (OrderDetailDTO or : orderDetailDTOS) {
            }
            boolean isPlaced = purchaseOrderBO.saveOrder(ordersDTO);
            if (isPlaced) {
                /* dto clear table */
                obList.clear();
                loadNextOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Orders Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Orders Not Placed!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextOrderId() {
        try {
            lblOrderId.setText((purchaseOrderBO.getLastOrderID() + 1) + "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadCustomerIds() {
        try {
            ObservableList<CustomerDTO> observableList = FXCollections.observableArrayList();

            final List<CustomerDTO> allCustomer = customerBO.getAllCustomer();

            for (CustomerDTO c : allCustomer) {
                observableList.add(c);
            }
            cmbCustomerId.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        try {
            ObservableList<ItemDTO> observableList = FXCollections.observableArrayList();
            final List<ItemDTO> allItem = itemBO.getAllItem();
            for (ItemDTO item : allItem) {
                observableList.add(item);
            }
            cmbItemCode.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colAction.setCellValueFactory(new PropertyValueFactory("btnDelete"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.DASHBOARD, pane);
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {

        try {
            ItemDTO itemDTO = itemBO.searchItem(cmbItemCode.getValue().getCode());
            fillItemFields(itemDTO);
            txtQty.requestFocus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(ItemDTO itemDTO) {
        lblDescription.setText(itemDTO.getDescription());
        lblUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }
}
