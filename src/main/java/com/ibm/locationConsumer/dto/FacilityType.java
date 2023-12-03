package com.ibm.locationConsumer.dto;

import lombok.Data;

@Data
public class FacilityType {
    private String code;
    private String name;
    private String masterType;
    private String validThroughDate;
}
