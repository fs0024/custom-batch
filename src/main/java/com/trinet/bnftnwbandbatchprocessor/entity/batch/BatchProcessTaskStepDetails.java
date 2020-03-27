 /**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.entity.batch;

 import com.fasterxml.jackson.annotation.JsonAutoDetect;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import com.fasterxml.jackson.annotation.JsonIgnore;
 import com.fasterxml.jackson.annotation.JsonProperty;
 import lombok.EqualsAndHashCode;
 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.*;
 import java.time.LocalDate;

 /**
  * @author imistry1
  *
  *
  */
 @SqlResultSetMapping(name = "batchProcessTaskStepMap", entities = @EntityResult(entityClass = BatchProcessTaskStepDetails.class, fields = {
         @FieldResult(name = "id", column = "id"),
         @FieldResult(name = "batchProcessTaskId", column = "batchProcessTaskId"),
         @FieldResult(name = "batchProcessId", column = "batchProcessId"),
         @FieldResult(name = "finalStatus", column = "finalStatus"),
         @FieldResult(name = "startDate", column = "startDate"),
         @FieldResult(name = "trackingNumber", column = "trackingNumber"),
         @FieldResult(name = "batchProcessStepId", column = "batchProcessStepId"),
         @FieldResult(name = "stepName", column = "stepName"),
         @FieldResult(name = "stepDescription", column = "stepDescription"),
         @FieldResult(name = "taskStepStatus", column = "taskStepStatus"),
         @FieldResult(name = "stepPrepared", column = "stepPrepared")
 }
 ))


 @JsonAutoDetect
 @Getter
 @Setter
 @lombok.Generated
 @Entity
 @EqualsAndHashCode(onlyExplicitlyIncluded = true)
 public class BatchProcessTaskStepDetails {

     @Id
     @JsonProperty("id")
     @Column(name="id")
     private Integer id;


     @JsonProperty("batchProcessTaskId")
     @Column(name="batchProcessTaskId")
     private Integer batchProcessTaskId;

     @JsonProperty("batchProcessId")
     @Column(name="batchProcessId")
     private Integer batchProcessId;

     @JsonProperty("finalStatus")
     @Column(name="finalStatus")
     private String finalStatus;

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
     @JsonProperty("startDate")
     @Column(name="startDate")
     private LocalDate startDate;

     @JsonProperty("trackingNumber")
     @Column(name="trackingNumber")
     private String trackingNumber;

     @JsonIgnore
     @JsonProperty("batchProcessStepId")
     @Column(name="batchProcessStepId")
     private Integer batchProcessStepId;

     @JsonIgnore
     @JsonProperty("stepName")
     @Column(name="stepName")
     private String stepName;

     @JsonIgnore
     @JsonProperty("stepDescription")
     @Column(name="stepDescription")
     private String stepDescription;

     @JsonIgnore
     @JsonProperty("taskStepStatus")
     @Column(name="taskStepStatus")
     private String taskStepStatus;

     @JsonIgnore
     @JsonProperty("stepPrepared")
     @Column(name="stepPrepared")
     private Character stepPrepared;

 }