package com.jibi.backendjibi.login.controllers;

import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.login.dto.LoginDto;
import com.jibi.backendjibi.login.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jibi/")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private LoginServices loginServices;
    @PostMapping("isAgent")
    public boolean checkAgent(@RequestBody LoginDto loginDto){
        return this.loginServices.checkPhone(loginDto);
    }
    @PostMapping("loginAgent")
    public AgentDto agentLogin(@RequestBody LoginDto loginDto){
        return this.loginServices.loginForAgent(loginDto);
    }
    @PostMapping("loginClient")
    public ClientDto clientLogin(@RequestBody LoginDto loginDto){
        return this.loginServices.loginForClient(loginDto);
    }
}
