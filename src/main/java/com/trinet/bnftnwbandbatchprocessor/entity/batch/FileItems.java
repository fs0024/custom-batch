/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.entity.batch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="FILE_ITEMS")
@Entity
public class FileItems implements Serializable{

	private static final long serialVersionUID = -1687156528593120974L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@OneToOne
	@JoinColumn(name = "TASK_ITEMS_ID", referencedColumnName = "id")
	private TaskItem taskItem;
	
	
	@Column(name="Value")
	private String value;

}
