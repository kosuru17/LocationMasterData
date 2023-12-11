package com.ibm.locationconsumer.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Bda {
    private String name;
    private String type;
    private String bdaType;
    private ArrayList<AlternateCode> alternateCodes;
}
