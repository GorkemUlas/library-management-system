package com.lms.backend.library.controller;

import com.lms.backend.library.dto.AdminSummaryDto;
import com.lms.backend.library.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/summary")
    public AdminSummaryDto getSummary() {
        return adminService.getSummary();
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin dashboard";
    }
}

