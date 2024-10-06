package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.entity.Salons;
import com.datvm.hairbookingapp.service.SalonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salon")
public class SalonsController {
    @Autowired
    private SalonsService salonsService;
    @PostMapping("create")
    public Salons create(@RequestBody Salons salon) {
        return salonsService.createSalons(salon);
    }
    @GetMapping("/find")
    public Salons findSalonsById(@RequestParam("id") Long id) {
        return salonsService.findSalonsBySalonId(id);
    }
    @GetMapping("/get")
    public List<Salons> getAllSalons() {
        return salonsService.findAllSalons();
    }
    @PutMapping("/update")
    public int updateSalonsById(@RequestBody Salons salon, @RequestParam("id") Long id) {
        return salonsService.updateSalonsBySalonId(id, salon);
    }
    @DeleteMapping("/delete")
    public void deleteSalonsById(@RequestParam("id") Long id) {
        salonsService.deleteSalonsBySalonId(id);
    }
}
