package com.example.internetshop.repository;

import com.example.internetshop.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Modifying
    @Query(value = "update Users c set c.login=:login, c.password=:password, c.authority=:authority," +
            "c.information=:information, c.email=:email where c.id=:id")
    void updateUsers(@Param(value = "id") long id, @Param(value = "login") String login,
                           @Param(value = "password") String password,
                           @Param(value = "authority") String authority,
                           @Param(value = "information") String information,
                           @Param(value = "email") String email);

    @Query(value = "select c from Users c where c.id = :id")
    Users getId(@Param(value = "id") Long id);

    @Query(value = "select c from Users c where c.login = :login")
    List<Users> findByLogin(@Param("login") String login);
}
