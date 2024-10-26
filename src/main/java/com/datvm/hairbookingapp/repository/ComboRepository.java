package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    @Query("Select c From Combo c Where NOT c.id = ?1")
    List<Combo> findAllRemainCombo(Long id);
}
