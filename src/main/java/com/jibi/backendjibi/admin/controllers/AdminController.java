package com.jibi.backendjibi.admin.controllers;

import com.jibi.backendjibi.admin.services.AdminServices;
import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.client.dto.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/jibi/admin")
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @GetMapping("/agents")
    public List<AgentDto> agents(){
        return this.adminServices.showAgents();
    }

    @GetMapping("/clients")
    public List<ClientDto> clients(){
        return this.adminServices.showClients();
    }

    @PostMapping("/delete/client")
    public  void deleteClient(@RequestBody String phone){
        this.adminServices.deleteClient(phone);
    }

    @PostMapping("/delete/agent")
    public  void deleteAgent(@RequestBody String phone){
        this.adminServices.deleteAgent(phone);
    }

    @PostMapping("/activate")
    public boolean activateAgent(@RequestBody String phone){
        return this.adminServices.activateAgent(phone);
    }
}
