package com.example.internetshop.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "price")
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле название товара должно быть заполнено")
    private String name;
    @NotEmpty(message = "Поле описание должно быть заполнено")
    @Size(min=6, max = 100, message = "Не меньше 6 и не больше 100 знаков ")
    private String about;
    @NotNull(message = "Поле цена должно быть заполнено")
    private Double price;
    @NotNull(message = "Поле количество товара должно быть заполнено")
    private Integer count;

    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "about")
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
