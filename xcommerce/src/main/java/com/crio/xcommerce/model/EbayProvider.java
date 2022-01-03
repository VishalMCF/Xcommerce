package com.crio.xcommerce.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class EbayProvider implements IProvider{

    @JsonProperty("txn_id")
    private long txnId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("transaction_status")
    private Status transactionStatus;

    @JsonProperty("transaction_date")
    private LocalDate transactionDate;

    @JsonProperty("amount")
    private double amount;

    public EbayProvider(){}

    @Override
    public LocalDate getTransDate() {
        return this.transactionDate;
    }

    @Override
    public Status getTransStatus() {
        return this.transactionStatus;
    }

    @Override
    public double getTransAmount() {
        return this.amount;
    }

    @Override
    public long getTransId() {
        return this.txnId;
    }

    public void setTransactionStatus(String status){
        this.transactionStatus = status.equals("")?Status.NA:Status.valueOf(status.toUpperCase());
    }

    public void setTransactionDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.transactionDate = LocalDate.parse(date, formatter);
    }

    public void setAmount(String amount){
        this.amount = Double.parseDouble(amount);
    }
    
}
