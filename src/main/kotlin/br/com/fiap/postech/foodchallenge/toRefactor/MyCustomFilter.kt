package br.com.fiap.postech.foodchallenge.toRefactor

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MyCustomFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (token != null) {
            try {
                val auth = CustomAuthenticator().authenticate(token)
                val context: SecurityContext = SecurityContextHolder.createEmptyContext()
                context.authentication = auth
                request.setAttribute("cpf", auth.principal)
            } catch (bce: BadCredentialsException) {
                bce.printStackTrace()
                response.sendError(HttpStatus.SC_FORBIDDEN, "Invalid token")
                return
            }
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return path.contains("/v1/customers/identify")
    }
}