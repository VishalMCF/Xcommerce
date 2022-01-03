package com.crio.xcommerce.model;

import java.time.LocalDate;

public interface IProvider {
    public LocalDate getTransDate();
    public Status getTransStatus();
    public double getTransAmount();
    public long getTransId();
}