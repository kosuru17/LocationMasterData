package com.ibm.locationConsumer.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BdaLocation {
    private String name;
    private String type;
    private String status;
    private ArrayList<AlternateCode> alternateCodes;
}
