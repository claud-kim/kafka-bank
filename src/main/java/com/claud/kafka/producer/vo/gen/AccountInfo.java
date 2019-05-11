package com.claud.kafka.producer.vo.gen;

public class AccountInfo {
    private int userNumber;
    private String accountNumber;
    private String name;
    private String birthDay;
    private long money;

    public AccountInfo(int customerNumber, String accountNumber, String name, String birthDay, long money) {
        this.userNumber = customerNumber;
        this.accountNumber = accountNumber;
        this.name = name;
        this.birthDay = birthDay;
        this.money = money;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void updatePlusMoney(int inMoney) {
        this.money = this.money + inMoney;
    }

    public void updateMinusMoney(int inMoney) {
        this.money = this.money - inMoney;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "userNumber=" + userNumber +
                ", accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", money=" + money +
                '}';
    }
}
