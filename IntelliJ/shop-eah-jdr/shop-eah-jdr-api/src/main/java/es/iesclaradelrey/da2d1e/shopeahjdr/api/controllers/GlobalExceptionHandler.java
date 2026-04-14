package es.iesclaradelrey.da2d1e.shopeahjdr.api.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.InsufficientStockException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.InvalidUnitsException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.ProductNotInCartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail productNotFound() {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "Product not found"
        );
    }

    @ExceptionHandler(InvalidUnitsException.class)
    public ProblemDetail invalidUnits() {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Units must be greater than 0"
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ProblemDetail insufficientStock() {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Not enough stock available"
        );
    }

    @ExceptionHandler(ProductNotInCartException.class)
    public ProblemDetail productNotInCart() {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Product not in cart"
        );
    }
}
