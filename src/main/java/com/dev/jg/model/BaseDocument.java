package com.dev.jg.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class BaseDocument {

    private String[] category;

    private String currency;

    @JsonSetter("customer_first_name")
    private String customerFirstName;

    @JsonSetter("customer_full_name")
    private String customerFullName;

    @JsonSetter("customer_gender")
    private String customerGender;

    @JsonSetter("customer_id")
    private long customerId;

    @JsonSetter("customer_phone")
    private String customerPhone;

    private String email;

    @JsonSetter("order_date")
    private String orderDate;
}
