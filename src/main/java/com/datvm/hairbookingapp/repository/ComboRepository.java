package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {

}
