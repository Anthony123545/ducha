package com.supervision.grassrootsregistration.domain;
import lombok.Data;
import java.util.Date;

@Data
public class DeviceRegister2 {
    private Long id;

    private Long registerId;

    private String carrierType;
    private String brandModel;
    private String secretLevel;
    private Date startDate;
    private String serialNumber;
    private String otherInformation;

    private String responsiblePerson;
    private Date manageStartDate;
    private Date manageEndDate;
    private Integer sortNo;
}
