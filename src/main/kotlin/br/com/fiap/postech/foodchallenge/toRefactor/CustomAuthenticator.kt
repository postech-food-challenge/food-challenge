package br.com.fiap.postech.foodchallenge.toRefactor

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class CustomAuthenticator {

    @Throws(AuthenticationException::class)

    fun authenticate(token: String?): Authentication {
        val apiGatewayUrl : String = "https://cjcspbqydl.execute-api.us-east-1.amazonaws.com/dev/auth"
        val client = RestTemplate();
        val header = HttpHeaders()
        header.set("Authorization", token)

        try {
            val requestEntity = RequestEntity<Any>(header, HttpMethod.GET, URI.create(apiGatewayUrl))

            val responseEntity = client.exchange(requestEntity, String::class.java)

            return UsernamePasswordAuthenticationToken(responseEntity.body, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            throw BadCredentialsException("Invalid token")
        }
    }
}