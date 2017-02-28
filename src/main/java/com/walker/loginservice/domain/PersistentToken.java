package com.walker.loginservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by rettwalker on 2/6/17.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentToken {

    @Id
    @NotNull
    @Column(name = "series")
    private String series;

    @NotNull
    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String userName;

    @Column(name = "last_used")
    private Timestamp lastUsed;


    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }
}
