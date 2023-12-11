package com.ibm.locationconsumer.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Location {

    private String url;
    private ArrayList<Bda> bdas;
    private String name;
    private String type;
    private String doDAAC;
    private String fences;
    private String locationId;
    private String status;
    private Address address;
    private String bdaType;
    private Country country;
    private String geoType;
    private ArrayList<Parent> parents;
    private Parent parent;
    private String validTo;
    private String hsudName;
    private String huluName;
    private String latitude;
    private String portFlag;
    private String timeZone;
    private String longitude;
    private String validFrom;
    private String restricted;
    private String description;
    private String dialingCode;
    private ArrayList<BdaLocation> bdaLocations;
    private boolean isDuskCity;
    private boolean isMaerskCity;
    private String olsonTimezone;
    private boolean extOwned;
    private boolean extExposed;
    private String facilityId;
    private String openingHours;
    private ArrayList<AlternateCode> alternateCodes;
    private String contactDetails;
    private FacilityDetail facilityDetail;
    private Object transportModes;
    private ArrayList<FacilityService> facilityServices;
    private ArrayList <AlternateNames> alternateNames;
    private String subCityParents;
    private String utcOffsetMinutes;
    private String workaroundReason;
    private String daylightSavingEnd;
    private String daylightSavingTime;
    private String daylightSavingStart;
    private String postalCodeMandatory;
    private String dialingCodeDescription;
    private String stateProvinceMandatory;
    private String daylightSavingShiftMinutes;
}
