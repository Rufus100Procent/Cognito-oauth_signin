package se.distansakademin.oauth_0.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


    @Document(collection = "users")
    public class User implements Serializable, UserDetails {

        @Id
        private String id;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        public String refreshToken;
        public String AccessToken;
        public String imageUrl;



        public enum Provider { LOCAL, GOOGLE }
        private Provider provider;


        private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

        public User() {
            accountNonExpired = true;
            accountNonLocked = true;
            credentialsNonExpired = true;
            enabled = true;
            provider = Provider.LOCAL;
            this.authorities = defaultAuthorities();
        }

        public User(String username, String password) {
            this();

            this.username = username;
            this.password = password;
        }

        public User(OAuth2AuthenticationToken token) {
            this();

            username = token.getPrincipal().getAttribute("email");
            password = null;

            if (token.getAuthorizedClientRegistrationId().equals("google")){
                provider = Provider.GOOGLE;
            }

        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }




        /* --extends UserDetails -- */

        @Override
        public boolean isAccountNonExpired() {
            return accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        private Collection<GrantedAuthority> defaultAuthorities(){

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            return authorities;
        }


    }
