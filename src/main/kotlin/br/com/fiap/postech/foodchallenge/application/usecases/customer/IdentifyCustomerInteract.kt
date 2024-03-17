package br.com.fiap.postech.foodchallenge.application.usecases.customer

import br.com.fiap.postech.foodchallenge.domain.entities.CPF
import br.com.fiap.postech.foodchallenge.domain.entities.Customer
import br.com.fiap.postech.foodchallenge.infrastructure.controller.customer.IdentifyCustomerRequest
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.*

class IdentifyCustomerInteract() {
    val CLIENT_ID = "1p39740rbmbbbe7tdb9pf7btva"
    val USER_POOL_ID = "us-east-1_HDpjACtVG"
    val ACCESS_KEY = "AKIA6GBME6DRGLW5X3PN"
    val SECRET_KEY = "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"
    val PASSWORD = "PASSWORDLESS"
    val CHALLENGE_ANSWER = "FT_USERPOOL_ANSWER"


    fun identify(dto: IdentifyCustomerRequest): Customer {
        val awsCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)

        val credentialsProvider: AwsCredentialsProvider = StaticCredentialsProvider.create(awsCredentials)
        val cognitoClient = CognitoIdentityProviderClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(credentialsProvider)
            .build()

        val isCpfAlreadyRegistered = isCpfAlreadyRegistered(cognitoClient, dto.cpf)

        if (!isCpfAlreadyRegistered) {
            signUp(cognitoClient, CLIENT_ID, dto)
        }

        val authorization = initiateAuth(cognitoClient, CLIENT_ID, dto.cpf)
        val authenticationResult =
            respondToAuthChallenge(cognitoClient, dto.cpf, authorization!!.session())

        return Customer(
            CPF(dto.cpf),
            authenticationResult.idToken()
        )
    }

    fun isCpfAlreadyRegistered(cognitoClient: CognitoIdentityProviderClient, cpf: String): Boolean {
        try {
            val filter = "username = \"$cpf\""
            val usersRequest: ListUsersRequest = ListUsersRequest.builder()
                .userPoolId(USER_POOL_ID)
                .filter(filter)
                .build()

            val response: ListUsersResponse = cognitoClient.listUsers(usersRequest)
            return !response.users().isEmpty()
        } catch (e: CognitoIdentityProviderException) {
            System.err.println(e.awsErrorDetails().errorMessage())
            return false
        }
    }

    fun signUp(
        identityProviderClient: CognitoIdentityProviderClient, clientId: String?, dto: IdentifyCustomerRequest) {
        val email: AttributeType = AttributeType.builder()
            .name("email")
            .value(dto.email)
            .build()

        val name: AttributeType = AttributeType.builder()
            .name("name")
            .value(dto.name)
            .build()

        val userAttrsList: MutableList<AttributeType> = ArrayList<AttributeType>()
        userAttrsList.add(email)
        userAttrsList.add(name)

        try {
            val signUpRequest: SignUpRequest = SignUpRequest.builder()
                .userAttributes(userAttrsList)
                .username(dto.cpf)
                .clientId(clientId)
                .password(PASSWORD)
                .build()

            identityProviderClient.signUp(signUpRequest)
        } catch (e: CognitoIdentityProviderException) {
            System.err.println(e.awsErrorDetails().errorMessage())
            throw e
        }
    }

    fun respondToAuthChallenge(
        identityProviderClient: CognitoIdentityProviderClient,
        userName: String, session: String?
    ): AuthenticationResultType {
        val challengeResponses: MutableMap<String, String> = HashMap()

        challengeResponses["USERNAME"] = userName
        challengeResponses["ANSWER"] = CHALLENGE_ANSWER

        val respondToAuthChallengeRequest: RespondToAuthChallengeRequest = RespondToAuthChallengeRequest.builder()
            .challengeName(ChallengeNameType.CUSTOM_CHALLENGE)
            .clientId(CLIENT_ID)
            .challengeResponses(challengeResponses)
            .session(session)
            .build()

        val respondToAuthChallengeResult: RespondToAuthChallengeResponse = identityProviderClient
            .respondToAuthChallenge(respondToAuthChallengeRequest)

        return respondToAuthChallengeResult.authenticationResult()
    }

    fun initiateAuth(
        identityProviderClient: CognitoIdentityProviderClient,
        clientId: String?, userName: String
    ): InitiateAuthResponse? {
        try {
            val authParameters: MutableMap<String, String> = HashMap()
            authParameters["USERNAME"] = userName
            authParameters["PASSWORD"] = PASSWORD

            val authRequest: InitiateAuthRequest = InitiateAuthRequest.builder()
                .clientId(clientId)
                .authParameters(authParameters)
                .authFlow(AuthFlowType.CUSTOM_AUTH)
                .build()

            val response: InitiateAuthResponse = identityProviderClient.initiateAuth(authRequest)
            return response
        } catch (e: CognitoIdentityProviderException) {
            System.err.println(e.awsErrorDetails().errorMessage())
            System.exit(1)
        }

        return null
    }

}
