package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Salon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
    Salon findBySalonId(Long salonId);
    List<Salon> findAll();
    @Transactional
    void deleteBySalonId(Long salonId);
}
