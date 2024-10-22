package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    @Query("select s.id From Slot s where s.timeStart = ?1")
    Long findByTimeStart(LocalTime timeStart);
}
