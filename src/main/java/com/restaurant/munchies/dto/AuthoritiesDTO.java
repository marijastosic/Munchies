package com.restaurant.munchies.dto;

import com.restaurant.munchies.entities.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class AuthoritiesDTO {
    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    private String authority;
     private List<User> userList;

    public AuthoritiesDTO() {
    }

    public AuthoritiesDTO(Integer id, String authority, List<User> userList) {
        this.id = id;
        this.authority = authority;
        this.userList = userList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
