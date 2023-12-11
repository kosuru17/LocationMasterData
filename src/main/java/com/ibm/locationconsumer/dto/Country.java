package com.ibm.locationconsumer.dto;

import lombok.Data;

import java.util.ArrayList;
@Data
public class Country {
    private String name;
    private ArrayList<AlternateCode> alternateCodes;
}
