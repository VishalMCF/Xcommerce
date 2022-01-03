package com.crio.xcommerce.model;

import lombok.Data;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class AmazonProvider implements IProvider {

    @JsonProperty("transaction_id")
    private long transactionId;

    @JsonProperty("ext_txn_id")
    private String extTxnId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("amount")
    private double amount;

    public AmazonProvider() {}

    public AmazonProvider(Status status, double amount) {
        this.status = status;
        this.amount = amount;
    }

    @Override
    public LocalDate getTransDate() {
        return this.date;
    }

    @Override
    public double getTransAmount() {
        return this.amount;
    }

    @Override
    public long getTransId() {
        return this.transactionId;
    }

    @Override
    public Status getTransStatus() {
        return this.status;
    }

    public void setStatus(String status){
       this.status = status.equals("")?Status.NA:Status.valueOf(status.toUpperCase());
    }

    public void setDate(String date){
       this.date = LocalDate.parse(date);
    }

    public void setAmount(String amount){
       this.amount = Double.parseDouble(amount);
    }
}
