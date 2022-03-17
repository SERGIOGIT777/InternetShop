package com.example.internetshop.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "shops")
public class Shops {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_price")
    private Prices prices;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_logins")
    private Users users;

    @NotNull(message = "Поле цена товара должно быть заполнено")
    private double price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата не может быть пустой")
    private LocalDate dates;

    @NotNull(message = "Поле количества товара должно быть заполнено")
    private int counts;

    @NotEmpty(message = "Поле должно быть заполнено")
    private String address;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(max = 13, message = "Номер телефона состоит из 10 или 13 символов")
    private String phone;

    @NotEmpty(message = "Поле должно быть заполнено")
    private String status;

    public Long getId() {
        return id;
    }


    @Column(name = "id_price")
    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices shops) {
        this.prices = shops;
    }

    @Column(name = "id_logins")
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "dates")
    public LocalDate getDates() {
        return dates;
    }

    public void setDates(LocalDate dates) {
        this.dates = dates;
    }

    @Column(name = "counts")
    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
