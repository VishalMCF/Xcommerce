package com.crio.xcommerce.model;

import lombok.Data;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class FlipkartProvider implements IProvider {

    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("external_transaction_id")
    private String externalTransactionId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("transaction_date")
    private LocalDate transactionDate;

    @JsonProperty("transaction_status")
    private Status transactionStatus;


    public double amount;

    public FlipkartProvider(){}

    @Override
    public Status getTransStatus() {
        return this.transactionStatus;
    }

    @Override
    public long getTransId() {
        return this.transactionId;
    }

    @Override
    public LocalDate getTransDate() {
        return this.transactionDate;
    }

    @Override
    public double getTransAmount(){
        return this.amount;
    }

    public void setTransactionStatus(String status){
        this.transactionStatus = status.equals("")?Status.NA:Status.valueOf(status.toUpperCase());
    }

    public void setTransactionDate(String date){
        this.transactionDate = LocalDate.parse(date);
    }

    public void setAmount(String amount){
        this.amount = Double.parseDouble(amount);
    }

}
