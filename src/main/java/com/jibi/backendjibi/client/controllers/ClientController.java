package com.jibi.backendjibi.client.controllers;

import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.client.dto.PaiementDto;
import com.jibi.backendjibi.client.dto.TransactionDto;
import com.jibi.backendjibi.client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/jibi/client/")
@CrossOrigin("*")
public class ClientController {
    @Autowired
    private ClientService clientService;
    /*--------- create client ----------*/
    @PostMapping("create")
    public boolean createClient(@RequestBody ClientDto clientDto){
        return this.clientService.createClient(clientDto);
    }
    /*--------- verify phone number  ----------*/
    @PostMapping("verify")
    public boolean verifyPhone(@RequestBody ClientDto clientDto){
        return this.clientService.verify(clientDto);
    }
    /*-------------- send email ----------------*/
    @PostMapping("sendEmail")
    public void sendEmail(@RequestBody ClientDto clientDto){
        this.clientService.sendEmail(clientDto);
    }

    /*--------------upload photo -------------------*/
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


    /*--------------payer une recharge -------------------------------------------*/
    @PostMapping("payer")
    public ClientDto payer(@RequestBody PaiementDto paiementDto){
        return this.clientService.payerRecharge(paiementDto);
    }


    /*---------------------transactions ---------------------------------*/

    @PostMapping("sendTo")
    public ClientDto sendTo(@RequestBody TransactionDto transactionDto){
        return this.clientService.transaction(transactionDto);
    }


    /*------------------ change cover -------------------*/
    @PostMapping("cover")
    public ClientDto changeCover(@RequestBody ClientDto clientDto){
        return this.clientService.changeCover(clientDto);
    }

    /*---------------- change password -----------------------*/

    @PostMapping("changePassword")
    public  boolean changePassword(@RequestBody ClientDto clientDto){
        return this.clientService.changePassword(clientDto);
    }

}
