package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {
    @Query("SELECT MAX(m.id) FROM Manager m")
    String findLastId();

    @Query("select s.code from Staff s join Manager m on m.staff.id = s.id where m.id = ?1")
    String findStaffCodeByManagerId(String id);
}
