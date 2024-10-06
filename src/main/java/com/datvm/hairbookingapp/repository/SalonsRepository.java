package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Salons;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonsRepository extends JpaRepository<Salons, Long> {


    Salons findSalonsBySalonId(Long salonId);
    List<Salons> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE Salons s SET s.salonAddress = ?2, s.salonName = ?3, s.description = ?4, " +
            "s.openingHours = ?5, s.phoneNumber = ?6 WHERE s.salonId = ?1")
    int updateSalonsBySalonId(Long salonId, String salonAddress, String salonName, String description,
                              String openingHours, String phoneNumber);
    @Transactional
    void deleteSalonsBySalonId(Long salonId);

}
