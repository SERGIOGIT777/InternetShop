package com.example.internetshop.repository;

import com.example.internetshop.Entities.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Prices, Long> {
    @Query(value = "select c from Prices c where c.name=:name")
    List<Prices> findByName(@Param("name") String name);

    @Query(value = "select c from Prices c where upper(c.about) like concat('%', upper(?1), '%')")
    List<Prices> findByAbout(String about);

    @Modifying
    @Query(value = "update Prices c set c.name=:name, c.about=:about, c.price=:price," +
            "c.count=:count where c.id=:id")
    void updatePrice(@Param(value = "id") long id, @Param(value = "name") String name,
                     @Param(value = "about") String about,
                     @Param(value = "price") Double price,
                     @Param(value = "count") Integer count);

    @Query(value = "select c from Prices c where c.id = :id")
    Prices getId(@Param(value = "id") Long id);
}
