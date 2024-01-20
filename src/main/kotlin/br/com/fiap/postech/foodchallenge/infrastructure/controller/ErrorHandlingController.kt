package br.com.fiap.postech.foodchallenge.infrastructure.controller

import br.com.fiap.postech.foodchallenge.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidCpfException
import br.com.fiap.postech.foodchallenge.domain.exceptions.InvalidParameterException
import br.com.fiap.postech.foodchallenge.domain.exceptions.NoObjectFoundException
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductAlreadyExistsException
import br.com.fiap.postech.foodchallenge.domain.exceptions.ProductNotFoundException
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

    @ExceptionHandler(ProductNotFoundException::class, NoObjectFoundException::class)
    fun handleNotFound(ex: RuntimeException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleInvalidDBRequest(ex: DataIntegrityViolationException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IllegalStateException::class)
    fun handleInternalErrors(ex: IllegalStateException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(InvalidParameterException::class)
    fun handleInvalidCategoryException(ex: InvalidParameterException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(InvalidCpfException::class)
    fun handleInvalidCpf(ex: InvalidCpfException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

}