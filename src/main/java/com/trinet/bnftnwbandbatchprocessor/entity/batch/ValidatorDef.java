package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the VALIDATOR_DEF database table.
 * 
 */
@SqlResultSetMapping(name = "validatorDefMap", entities = @EntityResult(entityClass = ValidatorDef.class, fields = {
		@FieldResult(name = "id", column = "id"),
		@FieldResult(name = "def", column = "def"),
		@FieldResult(name = "sequence", column = "sequence"),
		@FieldResult(name = "value", column = "value"),
		@FieldResult(name = "fileValidatorId", column = "fileValidatorId")
}
))


@Entity
@Table(name="VALIDATOR_DEF")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ValidatorDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="def")
	private String def;

	@Column(name="sequence")
	private Integer sequence;

	@Column(name="value")
	private String value;
	
	@Column(name="fileValidatorId")
	private Integer fileValidatorId;


	//bi-directional many-to-one association to FileValidator
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="file_validator_id")
	private FileValidator fileValidator;
*/
}