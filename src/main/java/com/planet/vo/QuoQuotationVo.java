package com.planet.vo;

import com.planet.ordpreorder.domain.OrdPreOrder;
import com.planet.quoquotation.domain.QuoQuotation;

import java.util.List;

/**
 * Created by Li on 2016/2/16.
 */
public class QuoQuotationVo {
    private QuoQuotation quoQuotation;
    private List<OrdPreOrder> ordPreOrder;

    public QuoQuotation getQuoQuotation() {
        return quoQuotation;
    }

    public void setQuoQuotation(QuoQuotation quoQuotation) {
        this.quoQuotation = quoQuotation;
    }

    public List<OrdPreOrder> getOrdPreOrder() {
        return ordPreOrder;
    }

    public void setOrdPreOrder(List<OrdPreOrder> ordPreOrder) {
        this.ordPreOrder = ordPreOrder;
    }
}
