/**
 * 
 */
package com.trinet.benefits.oe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author imistry1
 *
 * 
 */
@SqlResultSetMapping(name = "batchProcessAndTaskMap", entities = @EntityResult(entityClass = BatchProcessAndTaskModel.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "processId", column = "processId"),
		@FieldResult(name = "name", column = "name"),
		@FieldResult(name = "appId", column = "appId"),
		@FieldResult(name = "batchProcessTaskId", column = "batchProcessTaskId")
}
))

@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BatchProcessAndTaskModel {
		
	@Id
	private Integer id;
	
	@Column(name="processId")
	private Integer processId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="appId")
	private String appId;
	
	@Column(name="batchProcessTaskId")
	private Integer batchProcessTaskId;
	

}
