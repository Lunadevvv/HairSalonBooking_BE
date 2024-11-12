package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<Salon, String> {
    @Query("SELECT MAX(s.id) FROM Salon s")
    String findLastId();

    @Query("Select Count(s) From Salon s Where s.isOpen = true")
    int countTotalSalons();
}
