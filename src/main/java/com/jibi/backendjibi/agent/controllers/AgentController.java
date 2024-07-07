package com.jibi.backendjibi.agent.controllers;

import ch.qos.logback.core.net.server.Client;
import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.agent.repository.RepositoryAgent;
import com.jibi.backendjibi.agent.services.AgentService;
import com.jibi.backendjibi.client.dto.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jibi/")
@CrossOrigin("*")
public class AgentController {
 @Autowired
 private AgentService agentService;
    private RepositoryAgent repositoryAgent;


    @PostMapping("verify")
      public boolean verify(@RequestBody AgentDto agentDto){
          return agentService.verify(agentDto);
      }

   /*----- create agent ---------*/
    @PostMapping("createAgent")
    public AgentDto createAgent(@RequestBody AgentDto agentDto){
        return this.agentService.createAgent(agentDto);
    }
    /*---------- send Email ------------------------*/
   @PostMapping("sendEmail")
   public void send(@RequestBody AgentDto agentDto){
     this.agentService.sendEmail(agentDto);
   }

    /*--------------- change cover for agent ------------------*/
    @PostMapping("upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        System.out.println("for testttt");
        try {
            String uploadDir = "C:\\Users\\pc\\Desktop\\revision controle\\frontend-jibi\\src\\assets\\agentImages";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(new File(uploadDir + File.separator + file.getOriginalFilename()));
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }


    @PostMapping("updateCover")
    public AgentDto updateCover(@RequestBody AgentDto agentDto){
        return this.agentService.changeCover(agentDto);
    }


    /*---------- update profile ---------*/
    @PostMapping("updateProfile")
    public AgentDto update(@RequestBody AgentDto agentDto){
        return this.agentService.update(agentDto);
    }

    /*----------update password ----------*/
    @PostMapping("updatePass")
    public boolean updatePass(@RequestBody AgentDto agentDto){
        return this.agentService.updatePassword(agentDto);
    }

    /*---------------show clients-------------*/
    @GetMapping("showClients")
    public List<ClientDto>clientDtos(){
        return this.agentService.showClients();
    }

    /*---------------------manage account for client---------------*/

    @PostMapping("search")
    public ClientDto search(@RequestBody ClientDto clientDto){
        return this.agentService.searchClient(clientDto);
    }

    @PostMapping("changeAccount")
    public boolean changeAccount(@RequestBody ClientDto clientDto){
        return this.agentService.changeAccount(clientDto);
    }
}
