package com.planet.quoquotation.domain;

/**
 *
 */
public enum QuotationStatus {
    CREATE(0),CONFIRM(1),FINISHED(2);

    private Integer value;
    QuotationStatus(Integer value){
        this.value = value;
    }
    public Integer getValue(){
        return value;
    }
}
