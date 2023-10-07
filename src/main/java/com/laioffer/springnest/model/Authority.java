package com.laioffer.springnest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// the table name in the database is "authority".
// for User Authorization, Database Representation, Flexibility, and Integration with Security Frameworks
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    private String username;
    private String authority;

    public Authority() {}

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public Authority setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public Authority setAuthority(String authority) {
        this.authority = authority;
        return this;
    }
}
