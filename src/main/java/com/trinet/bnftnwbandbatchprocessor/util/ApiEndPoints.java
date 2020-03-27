package com.trinet.bnftnwbandbatchprocessor.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@lombok.Generated
public class ApiEndPoints {

    @Value("${api.base.url}")
    private String apiUrl;

    @Value("${api.batchconfig.url}")
    private String batchConfigUrl ;


    @Value("${api.batchtask.url}")
    private String batchTaskUrl ;

    @Value("${api.updateitem.url}")
    private String updateItemUrl ;

    @Value("${api.getitems.url}")
    private String getItemsUrl ;


    @Value("${api.updatetaskstepstatus.url}")
    private String updateTaskStepStatusUrl ;

    @Value("${api.batchTaskParamValue.url}")
    private String batchTaskParamValue;

    @Value("${api.partiontaskitems.url}")
    private String partitionItemUrl ;


    @Value("${api.batchtaskprepared.url}")
    private String batchTaskPreparedUrl ;

    @Value("${api.gettaskitems.url}")
    private String getTaskItemsUrl ;

    @Value("${api.updatePrepFlag.url}")
    private String updatePrepFlagUrl ;


    @Value("${api.updateTaskStatus.url}")
    private String updateTaskStatusUrl ;


    @Value("${api.unlocktask.url}")
    private  String unlocktaskUrl ;

    @Value("${api.batchConfigAppId.url}")
    private String batchConfigAppId;

}
