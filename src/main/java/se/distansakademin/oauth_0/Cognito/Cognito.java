package se.distansakademin.oauth_0.Cognito;

import se.distansakademin.oauth_0.Model.User;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cognito {

    private final CognitoIdentityProviderClient client;

    private static final String clientId = "";
    private static final String userPool = "";
    public static User loggedInUser;

    public Cognito() {
        this.client = getCognitoIdentityProviderClient();
    }

    public static CognitoIdentityProviderClient getCognitoIdentityProviderClient() {
        var credentialProvider = ProfileCredentialsProvider.create();

        return CognitoIdentityProviderClient.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(credentialProvider)
                .build();
    }

    public static void Logout() {
        loggedInUser = null;
    }

    public static boolean Register(String userName, String password, String email) {
        AttributeType userAttrs = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        List<AttributeType> userAttrsList = new ArrayList<>();
        userAttrsList.add(userAttrs);

        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(userAttrsList)
                    .username(userName)
                    .clientId(clientId)
                    .password(password)
                    .build();

            getCognitoIdentityProviderClient().signUp(signUpRequest);
            System.out.println("User has been signed up. Please check your email for the confirmation code.");
            return true;
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    public static boolean ConfirmUser(String confirmationCode, String userName) {
        System.out.println("Confirmation Code: " + confirmationCode);

        try {
            ConfirmSignUpRequest signUpRequest = ConfirmSignUpRequest.builder()
                    .confirmationCode(confirmationCode)
                    .username(userName)
                    .clientId(clientId)
                    .build();

            getCognitoIdentityProviderClient().confirmSignUp(signUpRequest);
            System.out.println(userName + " was confirmed");
            return true;
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return false;
        }
    }
    public static boolean Login(String username, String password) {
        InitiateAuthResponse response = initiateAuth(username, password);
        if ( response != null) {
            String refreshToken = response.authenticationResult().refreshToken();
            String accessToken = response.authenticationResult().accessToken();
            User user = new User(username, refreshToken);
            loggedInUser = user;
            return true;
        }

        return false;
    }

    public static boolean ChangePassword(String oldPassword, String newPassword) {
        try {
            ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
                    .previousPassword(oldPassword)
                    .proposedPassword(newPassword)
                    .accessToken(loggedInUser.getRefreshToken())
                    .build();

            getCognitoIdentityProviderClient().changePassword(changePasswordRequest);
            System.out.println("Password has been changed successfully");
            return true;
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    public static InitiateAuthResponse initiateAuth(String userName, String password) {
        try {
            Map<String, String> authParameters = new HashMap<>();
            authParameters.put("USERNAME", userName);
            authParameters.put("PASSWORD", password);

            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .authParameters(authParameters)
                    .authFlow(AuthFlowType.REFRESH_TOKEN_AUTH) // Use REFRESH_TOKEN_AUTH flow
                    .clientId(clientId) // Specify your client ID
                    .build();

            InitiateAuthResponse response = getCognitoIdentityProviderClient().initiateAuth(authRequest);
            System.out.println("Result Challenge is: " + response.challengeName());
            return response;

        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return null;
    }

    public static boolean DeleteUser(String username) {
        try {
            AdminDeleteUserRequest deleteUserRequest = AdminDeleteUserRequest.builder()
                    .username(username)
                    .userPoolId(userPool)
                    .build();

            getCognitoIdentityProviderClient().adminDeleteUser(deleteUserRequest);
            System.out.println("User has been deleted successfully");
            return true;
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return false;
        }
    }


}
