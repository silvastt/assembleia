package br.com.assembleia.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(ErroInternoException.class)
  public final ResponseEntity<ExceptionResponse> erroInternoException(final ErroInternoException ex, final WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(),
        request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ObjetoNaoEncontradoException.class)
  public final ResponseEntity<ExceptionResponse> objetoNaoEncontratoException(final ObjetoNaoEncontradoException ex, final WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(),
            request.getDescription(false), HttpStatus.NOT_FOUND.getReasonPhrase());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

}