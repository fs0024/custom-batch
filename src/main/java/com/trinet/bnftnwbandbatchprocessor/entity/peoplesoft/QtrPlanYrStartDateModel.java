package com.trinet.bnftnwbandbatchprocessor.entity.peoplesoft;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author dshah
 *
 * 
 */
@SqlResultSetMapping(name = "qtrPlanYrStartDate", entities = @EntityResult(entityClass = QtrPlanYrStartDateModel.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "planYrStartDate", column = "T2_PLNYR_START_DT") }))
@Entity
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class QtrPlanYrStartDateModel {

	@Id
	@Column(name="id")
	private Integer id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name = "T2_PLNYR_START_DT")
	private LocalDate planYrStartDate;

}
