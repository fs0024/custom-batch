package com.trinet.bnftnwbandbatchprocessor.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
@lombok.Generated
public class BaseApiResponse {

  private String status ;
  private String statusCode ;

}
