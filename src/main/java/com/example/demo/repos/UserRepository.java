package com.example.demo.repos;

import com.example.demo.models.DatasDB;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT d FROM User d WHERE CONCAT(d.name, '', d.id) LIKE %?1%")
    List<User> search(String keyword);
}
