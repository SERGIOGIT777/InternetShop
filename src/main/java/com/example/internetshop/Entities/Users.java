package com.example.internetshop.Entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "logins")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле login должно быть заполнено")
    @Size(min=2, message = "Не меньше 2 знаков")
    private String login;
    @NotEmpty(message = "Поле password должно быть заполнено")
    @Size(min=2, message = "Пароль от 2 знаков")
    private String password;
    @NotEmpty(message = "Поле password должно быть заполнено")
    @Transient
    @Size(min=2, message = "Пароль от 2 знаков")
    private String confirm_password;
    @NotEmpty(message = "Поле role должно быть заполнено")
    private String authority;
    @NotEmpty(message = "Поле names должно быть заполнено")
    private String information;
    @NotEmpty(message = "Поле email должно быть заполнено")
    @Email(message = "Email invalid", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    public Long getId() {
        return id;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "confirm_password")
    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
