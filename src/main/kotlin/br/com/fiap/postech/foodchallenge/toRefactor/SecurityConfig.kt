package br.com.fiap.postech.foodchallenge.toRefactor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http {
//            authorizeRequests {
//                authorize("/v1/customers/identify/**", permitAll)
//                authorize("v1/orders/**", authenticated)
//            }

            authorizeRequests {
                authorize("/**", permitAll)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            csrf { disable() }
        }

        http.addFilterBefore(MyCustomFilter(), BasicAuthenticationFilter::class.java)

        return http.build()
    }
}