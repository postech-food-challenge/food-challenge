package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.application.domain.exceptions.ProductNotFoundException
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandlingController {

    @ExceptionHandler(CustomerAlreadyRegisteredException::class)
    fun handleCustomerAlreadyRegistered(exception: CustomerAlreadyRegisteredException): ResponseEntity<String> {
        return ResponseEntity(exception.message, CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = mutableMapOf<String, String?>()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage
        }
        return ResponseEntity(errors, BAD_REQUEST)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(exception: ProductNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(exception.message, NOT_FOUND)
    }

}