package se.distansakademin.oauth_0.Cognito;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

public class createUserPool {

    public static String createPool(CognitoIdentityProviderClient cognitoClient, String userPoolName ) {

        try {
            CreateUserPoolResponse response = cognitoClient.createUserPool(
                    CreateUserPoolRequest.builder()
                            .poolName(userPoolName)
                            .build()
            );
            return response.userPool().id();

        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

    public static void createPoolClient ( CognitoIdentityProviderClient cognitoClient,
                                          String clientName,
                                          String userPoolId ) {

        try {

            CreateUserPoolClientResponse response = cognitoClient.createUserPoolClient(
                    CreateUserPoolClientRequest.builder()
                            .clientName(clientName)
                            .userPoolId(userPoolId)
                            .build()
            );

            System.out.println("User pool " + response.userPoolClient().clientName() + " created. ID: " + response.userPoolClient().clientId());

        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }



}
