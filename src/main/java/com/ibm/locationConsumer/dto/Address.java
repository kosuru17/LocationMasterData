package com.ibm.locationConsumer.dto;

import lombok.Data;

@Data
public class Address {
    private String city;
    private Object poBox;
    private String street;
    private Object district;
    private String latitude;
    private String longitude;
    private Object territory;
    private String postalCode;
    private String countryCode;
    private String countryName;
    private Object houseNumber;
    private String addressLine2;
    private Object addressLine3;
    private String addressQualityCheckIndicator;
}
