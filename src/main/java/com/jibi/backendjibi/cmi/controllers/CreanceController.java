package com.jibi.backendjibi.cmi.controllers;

import com.jibi.backendjibi.cmi.dto.CreanceDto;
import com.jibi.backendjibi.cmi.services.CreanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/jibi")
public class CreanceController {
    @Autowired
    private CreanceServices creanceServices;

    @GetMapping("/creances")
    public List<CreanceDto> creances(){
        return this.creanceServices.creances();
    }
}
