package com.ibm.locationConsumer.dto;

import lombok.Data;

import java.util.ArrayList;
@Data
public class Country {
    private String name;
    private ArrayList<AlternateCode> alternateCodes;
}
