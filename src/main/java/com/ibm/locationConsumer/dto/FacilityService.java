package com.ibm.locationConsumer.dto;

import lombok.Data;

@Data
public class FacilityService {
    private String serviceCode;
    private String serviceName;
    private String validThroughDate;
    private String serviceDescription;
}
