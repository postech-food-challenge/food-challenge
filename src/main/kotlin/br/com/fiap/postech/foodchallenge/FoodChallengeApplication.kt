package br.com.fiap.postech.foodchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodChallengeApplication

fun main(args: Array<String>) {
	runApplication<FoodChallengeApplication>(*args)
}
