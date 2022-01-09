package br.com.assembleia.error;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExceptionResponse {

  private String mensagem;

  public ExceptionResponse(
      final LocalDate timestamp,
      final String message,
      final String details,
      final String httpCodeMessage) {
    super();
    this.mensagem = message;
  }
  
}