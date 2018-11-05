package com.restaurant.munchies.dto;

import com.restaurant.munchies.entities.Authorities;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    private Integer id;
    @NotNull(message = "Email must not be null")
    @Size(min = 1, max = 50)
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    private String email;
    @NotNull (message = "Password must not be null")
    private String password;
    @NotNull(message = "Password must not be null")
    private String confirmPassword;
    private Authorities authoritiesId;

    public UserDTO() {
    }

    public UserDTO(Integer id, String email, String password, String confirmPassword, Authorities authoritiesId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.authoritiesId = authoritiesId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Authorities getAuthoritiesId() {
        return authoritiesId;
    }

    public void setAuthoritiesId(Authorities authoritiesId) {
        this.authoritiesId = authoritiesId;
    }
}
