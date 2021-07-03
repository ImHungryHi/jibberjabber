package com.javafanatics.jibberjabber.account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getHandle();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    // Create an MD5 hash for a static random gravatar
    public String getMD5Hash(String email) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] data = messageDigest.digest(user.getEmail().getBytes());
            StringBuffer buffer = new StringBuffer();

            for (int x = 0; x < data.length; x++) {
                buffer.append(Integer.toHexString(data[x] & 0xFF | 0x100).substring(1, 3));
            }

            return buffer.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            // Nope, Chuck Testa!
        }

        // Return nothing in case of errors
        return null;
    }
}
