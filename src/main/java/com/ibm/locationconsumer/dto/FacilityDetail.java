package com.ibm.locationconsumer.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FacilityDetail {
    private String brand;
    private String gpsFlag;
    private String gsmFlag;
    private String vesselAgent;
    private ArrayList<FacilityType> facilityTypes;
    private String telephoneNumber;
    private String commFacilityType;
    private String facilityFunction;
    private String weightLimitYardKg;
    private String weightLimitCraneKg;
    private String oceanFreightPricing;
    private String exportEnquiriesEmail;
    private String facilityFunctionDesc;
    private String importEnquiriesEmail;
    private String internationalDialCode;
}
