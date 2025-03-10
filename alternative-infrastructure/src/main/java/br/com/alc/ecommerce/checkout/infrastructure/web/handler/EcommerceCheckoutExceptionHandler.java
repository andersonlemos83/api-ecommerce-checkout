package br.com.alc.ecommerce.checkout.infrastructure.web.handler;

import br.com.alc.ecommerce.checkout.infrastructure.dto.error.ErrorResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Log4j2
@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class EcommerceCheckoutExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::buildMessage)
                .sorted(naturalOrder())
                .distinct()
                .collect(joining(", ", "", "."));
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatus((HttpStatus) status)
                .message(message)
                .build();
        log.debug("Outgoing from EcommerceCheckoutExceptionHandler: {}", generateJson(errorResponseDto));
        return handleExceptionInternal(ex, errorResponseDto, new HttpHeaders(), status, request);
    }

    private String buildMessage(FieldError fieldError) {
        return MessageFormat.format("O campo {0} {1}", fieldError.getField(), fieldError.getDefaultMessage());
    }
}