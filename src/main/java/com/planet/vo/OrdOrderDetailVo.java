package com.planet.vo;

import com.planet.ordorder.domain.OrdOrder;
import com.planet.ordpreorder.domain.OrdPreOrder;

import java.util.List;

/**
 * Created by Li on 2016/2/15.
 */
public class OrdOrderDetailVo {
    private OrdOrder ordOrder;

    private List<OrdPreOrder> ordPreOrders;

    public OrdOrder getOrdOrder() {
        return ordOrder;
    }

    public void setOrdOrder(OrdOrder ordOrder) {
        this.ordOrder = ordOrder;
    }

    public List<OrdPreOrder> getOrdPreOrders() {
        return ordPreOrders;
    }

    public void setOrdPreOrders(List<OrdPreOrder> ordPreOrders) {
        this.ordPreOrders = ordPreOrders;
    }
}
