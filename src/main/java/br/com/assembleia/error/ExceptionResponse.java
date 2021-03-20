package br.com.assembleia.error;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExceptionResponse {

  private String mensagem;

  public ExceptionResponse(LocalDate timestamp, String message, String details, String httpCodeMessage) {
    super();
    this.mensagem = message;
  }
  
}