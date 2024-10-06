package com.datvm.hairbookingapp.controller;

import com.datvm.hairbookingapp.entity.Category;
import com.datvm.hairbookingapp.entity.Services;
import com.datvm.hairbookingapp.service.ServicesService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;
    @GetMapping("/get")
    public List<Services> getAllServices() {
        return servicesService.getAllServices();
    }
    @GetMapping("/find")
    public Services findServicesById(@RequestParam("id") Long id) {
        return servicesService.findServicesByServiceId(id);
    }
    @PutMapping("/update")
    public int updateServiceById(@RequestBody Services service,@RequestParam("categoryId") Long categoryId, Long serviceId) {
        return servicesService.updateServicesByServiceId(service, categoryId, serviceId);
    }
    @PostMapping("/create")
    public Services createService(@RequestBody Services service ,@RequestParam("categoryId") Long categoryId) {
        return servicesService.createService(service, categoryId);
    }
    @DeleteMapping("/delete")
    public void deleteServiceById(@RequestParam("id") Long serviceId) {
        servicesService.deleteServicesByServiceId(serviceId);
    }
}
