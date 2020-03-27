 /**
 * 
 */
package com.trinet.benefits.oe.model;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
/**
 * @author imistry1
 *
 * 
 */
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchProcessTaskStepDetails {
    
    
    @JsonProperty("batchProcessTaskId")
    private Integer batchProcessTaskId;
    
    @JsonProperty("batchProcessId")
    private Integer batchProcessId;
    
    @JsonProperty("finalStatus")
    private String finalStatus;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @JsonProperty("startDate")
    private LocalDate startDate;
    
    @JsonProperty("trackingNumber")
    private String trackingNumber;
    
    @JsonIgnore
    @JsonProperty("batchProcessStepId")
    private Integer batchProcessStepId;
    
    @JsonIgnore
    @JsonProperty("stepName")
    private String stepName;
    
    @JsonIgnore
    @JsonProperty("stepDescription")
    private String stepDescription;
    
    @JsonIgnore
    @JsonProperty("taskStepStatus")
    private String taskStepStatus;
    
    @JsonIgnore
    @JsonProperty("stepPrepared")
    private Character stepPrepared;
    
}