package se.distansakademin.oauth_0.Model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    private String username;

    private String refreshToken;
    private String AccessToken;
    public User(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
