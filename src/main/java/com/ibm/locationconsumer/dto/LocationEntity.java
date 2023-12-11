package com.ibm.locationconsumer.dto;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "locations")
public class LocationEntity {

    @Id
    private Integer id;
    @Column("location_type")
    private String locationType;
    @Column("location_name")
    private String locationName;
    @Column("location_code")
    private String locationCode;
    @Column("location_code_type")
    private String locationCodeType;
    @Column("location")
    private String location;
}
