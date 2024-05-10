package com.example.demo.repos;

import com.example.demo.models.DatasDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DatasDBRepository extends JpaRepository<DatasDB, Long> {
    @Query("SELECT d FROM DatasDB d WHERE CONCAT(d.kod_posta, '', d.waterlevel, '', d.temperature, '', d.windspeed, '', d.pressure, '', d.snowlevel, '', d.kolichestvoosadkov, '', d.date) LIKE %?1%")
    List<DatasDB> search(String keyword);
}