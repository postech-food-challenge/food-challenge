package br.com.fiap.postech.foodchallenge.adapters.controller

import br.com.fiap.postech.foodchallenge.application.domain.exceptions.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandlingController {

    @ExceptionHandler(CustomerAlreadyRegisteredException::class, ProductAlreadyExistsException::class)
    fun handleConflict(ex: RuntimeException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.CONFLICT)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> =
        ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
            .let { ResponseEntity(it, HttpStatus.BAD_REQUEST) }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleNotFound(ex: ProductNotFoundException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleInvalidDBRequest(ex: DataIntegrityViolationException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IllegalStateException::class)
    fun handleInternalErrors(ex: IllegalStateException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(InvalidCategoryException::class)
    fun handleInvalidCategoryException(ex: InvalidCategoryException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(NoProductsFoundException::class)
    fun handleNoProductsFoundException(ex: NoProductsFoundException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

}