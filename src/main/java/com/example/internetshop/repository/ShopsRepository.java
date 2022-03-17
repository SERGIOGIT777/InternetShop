package com.example.internetshop.repository;

import com.example.internetshop.Entities.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, Long> {
    @Query(value = "select c from Shops c where c.users.login=:login")
    List<Shops> findByName(@Param("login") String name);

    @Query(value = "select c from Shops c where c.status=:status")
    List<Shops> findByStatus(@Param("status") String status);

    @Query(value = "select c from Shops c where c.status=:status and c.users.login=:login")
    List<Shops> findByStatusLogin(@Param("status") String status, @Param("login") String login);

    @Modifying
    @Query(value = "update Shops c set c.status=:status where c.id=:id")
    void updateStatus(@Param(value = "id") long id, @Param(value = "status") String status);
}
