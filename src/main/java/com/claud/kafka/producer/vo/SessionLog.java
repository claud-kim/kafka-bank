package com.claud.kafka.producer.vo;

public class SessionLog extends BaseVo{
    private Integer customerNumber; //null able

    public SessionLog() {
        super(LogType.SESSION_LOG);
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }
}
