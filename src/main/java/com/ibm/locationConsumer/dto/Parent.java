package com.ibm.locationConsumer.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Parent {
    private String name;
    private String type;
    private String bdaType;
    private ArrayList<AlternateCode> alternateCodes;
}
