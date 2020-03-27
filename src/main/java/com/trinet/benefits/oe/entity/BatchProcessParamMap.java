package com.trinet.benefits.oe.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BATCH_PROCESS_PARAM_MAP")
@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BatchProcessParamMap {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "batch_process_id")
    private BatchProcess batchProcess ;

    @ManyToOne
    @JoinColumn(name = "batch_param_id")
    private BatchParam batchParam ;

    @ManyToOne
    @JoinColumn(name = "batch_process_step_id")
    private BatchProcessStep batchProcessStep ;
}
