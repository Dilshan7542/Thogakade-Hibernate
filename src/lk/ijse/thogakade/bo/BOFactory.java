package lk.ijse.thogakade.bo;

import lk.ijse.thogakade.bo.custom.impl.CustomerBOImpl;
import lk.ijse.thogakade.bo.custom.impl.ItemBOImpl;
import lk.ijse.thogakade.bo.custom.impl.PurchaseOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getInstance(){
       return boFactory==null ? boFactory=new BOFactory():boFactory;
    }
    public SuperBo getBO(BOType boType){
        switch (boType) {
            case CUSTOMER:return new CustomerBOImpl();
            case ITEM:return new ItemBOImpl();
            case PURCHASE_ORDER:return new PurchaseOrderBOImpl();
            default:return null;
        }
    }
}
