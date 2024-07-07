package com.jibi.backendjibi.client.services;
import ch.qos.logback.core.net.server.Client;
import com.jibi.backendjibi.agent.entities.Agent;
import com.jibi.backendjibi.agent.repository.RepositoryAgent;
import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.client.dto.PaiementDto;
import com.jibi.backendjibi.client.dto.TransactionDto;
import com.jibi.backendjibi.client.entities.ClientEntity;
import com.jibi.backendjibi.client.repository.ClientRepository;
import com.jibi.backendjibi.cmi.dto.CompteDto;
import com.jibi.backendjibi.cmi.entities.Compte;
import com.jibi.backendjibi.cmi.entities.Paiement;
import com.jibi.backendjibi.cmi.entities.Transaction;
import com.jibi.backendjibi.cmi.repository.CompteRepository;
import com.jibi.backendjibi.cmi.repository.PaiementRepo;
import com.jibi.backendjibi.cmi.repository.TransactionRepository;
import com.jibi.backendjibi.cmi.services.CompteServices;
import com.jibi.backendjibi.shared.GeneratePassword;
import com.jibi.backendjibi.shared.GenerateRib;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteServices compteServices;
    @Autowired
    private JavaMailSender mailSender;
    private String rib;
    private String password;
    @Autowired
    private RepositoryAgent repositoryAgent;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaiementRepo paiementRepo;

    /*------------- creation du client --------------*/
    public boolean createClient(ClientDto clientDto) {
        boolean status=false;
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(clientDto, clientEntity);
        String compteType = clientEntity.getAccount();
        double plafond = 0;
        double balance = 0;
        CompteDto compteDto = new CompteDto();
        Compte compte = new Compte();
        switch (compteType) {
            case "Account 20$":
                plafond = 20;
                balance = 20;
                break;
            case "Account 500$":
                plafond = 500;
                balance = 500;
                break;
            case "Account 2000$":
                plafond = 2000;
                balance = 2000;
                break;
            case "Account 4000$":
                plafond = 4000;
                balance = 4000;
                break;
            case "Account 5000$":
                plafond = 5000;
                balance = 5000;
                break;
            case "Account 10000$":
                plafond = 10000;
                balance = 10000;
                break;
            default:
                plafond = 0;
                balance = 0;
        }
        compteDto.setBalance(balance);
        compteDto.setPlafond(plafond);
        this.rib = GenerateRib.generate().toString();
        this.password= GeneratePassword.generate().toString();
        compteDto.setRib(this.rib);
        compteDto.setClient(clientEntity);
        BeanUtils.copyProperties(compteDto, compte);
        clientEntity.setPassword(this.password);
        ClientEntity created = clientRepository.save(clientEntity);
        CompteDto compteDto1 = compteServices.createAccount(compteDto);
        if(created!=null && compteDto1!=null){
            status=true;
        }
        ClientDto response = new ClientDto();
        BeanUtils.copyProperties(created, response);
        return status;

    }

    /*-------------------- verification phone number --------------------*/
    public boolean verify(ClientDto clientDto) {
        boolean status = false;
        ClientEntity isExist = clientRepository.findByPhone(clientDto.getPhone());
        Agent agent=repositoryAgent.findByPhone(clientDto.getPhone());
        if (isExist != null) {
            status = true;
        }
        if(agent!=null){
            status=true;
        }
        return status;
    }

    /*--------------------- send Email -----------------------*/

    public void sendEmail(ClientDto clientDto) {
        String to = clientDto.getEmail();
        String passwordUser = this.password;
        String ribUser=this.rib;
        String msg = "Cher " + clientDto.getFirstName() + " " + clientDto.getLastName() + ",\n\n" +
                "Nous sommes ravis de vous accueillir parmi nous ! Votre inscription sur notre plateforme a été complétée avec succès.\n\n" +
                "Détails de votre inscription :\n\n" +
                "Nom complet : " + clientDto.getFirstName() + " " + clientDto.getLastName() + "\n" +
                "Adresse e-mail : " + clientDto.getEmail() + "\n" +
                "Rib : " + ribUser + "\n" +
                "Votre mot de passe : " + passwordUser + "\n\n" +
                "Nous vous remercions pour votre confiance. Vous pouvez désormais profiter de tous les avantages et services que notre plateforme offre.\n\n" +
                "Si vous avez des questions ou besoin d'assistance, n'hésitez pas à nous contacter à l'adresse suivante : support@TJIBI.com.\n\n" +
                "Bien cordialement,\n\n" +
                "TJIBI";
        String subject = "TJIBI's Registration";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tarikbelaid12334@gmail.com");
        message.setText(msg);
        message.setSubject(subject);
        message.setTo(to);
        mailSender.send(message);
    }



    /*----------------------payer une recharge ------------------------*/
    public ClientDto payerRecharge(PaiementDto paiementDto){
        ClientEntity client=clientRepository.findByPhone(paiementDto.getPhone());
        ClientDto reponse=new ClientDto();
        PaiementDto paiementDto1=new PaiementDto();
        if(client!=null){
            if(client.getCompte().getBalance()>=paiementDto.getAmount()){
                double solde=client.getCompte().getBalance()-paiementDto.getAmount();
                Compte compte=client.getCompte();
                compte.setBalance(solde);
                Compte update=compteRepository.save(compte);
                client.setCompte(update);
                BeanUtils.copyProperties(client,reponse);
                paiementDto1.setImage(paiementDto.getImage());
                paiementDto1.setAmount(paiementDto.getAmount());
                paiementDto1.setPhone(paiementDto.getPhone());
                Paiement paiement=new Paiement();
                BeanUtils.copyProperties(paiementDto1,paiement);
                paiement.setClientId(client.getId());
                paiement.setClient(client);
                Paiement create=paiementRepo.save(paiement);

                List<PaiementDto>paiementDtos=new ArrayList<>();
                List<Paiement>paiements=paiementRepo.findAll();
                for(Paiement paiement1:paiements){
                    if(paiement1.getClientId()==client.getId()){
                        PaiementDto paiementDto2=new PaiementDto();
                        BeanUtils.copyProperties(paiement1,paiementDto2);
                        paiementDtos.add(paiementDto2);
                    }
                }
                List<TransactionDto>transactionDtos=new ArrayList<>();
                for (Transaction transaction:client.getTransactions()){
                    TransactionDto transactionDto=new TransactionDto();
                    BeanUtils.copyProperties(transaction,transactionDto);
                    transactionDtos.add(transactionDto);
                }

                reponse.setTransactionDto(transactionDtos);
                reponse.setPaiementDto(paiementDtos);
            }
        }
        return reponse;
    }

    /*-------------------------transactions ---------------------------------------*/
    public ClientDto transaction(TransactionDto transactionDto){
       Compte compte =compteRepository.findByRib(transactionDto.getRibTo());
       Compte compte1=compteRepository.findByRib(transactionDto.getRibFrom());
       ClientDto reponse=new ClientDto();
       ClientEntity client=new ClientEntity();
       ClientEntity client1=new ClientEntity();
       TransactionDto transactionDto1=new TransactionDto();
        List<TransactionDto>transactionDtos=new ArrayList<>();
       if(compte!=null && compte1!=null){
           double solde=compte.getBalance()+transactionDto.getAmount();
           double soldeOut=compte1.getBalance()-transactionDto.getAmount();
           if(solde<compte.getPlafond() && compte1.getBalance()>=transactionDto.getAmount()){
                client=clientRepository.findById(compte1.getIdClient()).get();
                client1=clientRepository.findById(compte.getIdClient()).get();
               compte.setBalance(solde);
               compte1.setBalance(soldeOut);
            Compte updateTo =compteRepository.save(compte);
            Compte updateFrom=compteRepository.save(compte1);
            client.setCompte(updateFrom);
            BeanUtils.copyProperties(client,reponse);
            transactionDto1.setSender(client.getFirstName()+" "+client.getLastName());
            transactionDto1.setReceiver(client1.getFirstName()+" "+client1.getLastName());
            transactionDto1.setAmount(transactionDto.getAmount());
            transactionDto1.setRibTo(transactionDto.getRibTo());
            transactionDto1.setRibFrom(transactionDto.getRibFrom());
            Transaction transactionEntiy=new Transaction();
            BeanUtils.copyProperties(transactionDto1,transactionEntiy);
            transactionEntiy.setClient(client);
            transactionEntiy.setClientSender(client.getId());
            transactionEntiy.setClientReceiver(client1.getId());
            Transaction transaction=transactionRepository.save(transactionEntiy);
            if(transaction!=null){
                List<Transaction>transactions=transactionRepository.findAll();
                for(Transaction transaction1:transactions){
                    if((transaction1.getRibFrom().equals(client.getCompte().getRib()) && transaction1.getRibTo().equals(client1.getCompte().getRib())) || (transaction1.getRibFrom().equals(client1.getCompte().getRib()) && transaction1.getRibTo().equals(client.getCompte().getRib()))){
                        TransactionDto transactionDto2=new TransactionDto();
                        BeanUtils.copyProperties(transaction1,transactionDto2);
                        transactionDtos.add(transactionDto2);
                    }

                }
                reponse.setTransactionDto(transactionDtos);

                List<PaiementDto>paiementDtos=new ArrayList<>();
                for(Paiement paiement:client.getPaiements()){
                    PaiementDto paiementDto=new PaiementDto();
                    BeanUtils.copyProperties(paiement,paiementDto);
                    paiementDtos.add(paiementDto);
                }
                reponse.setPaiementDto(paiementDtos);
            }
           }
       }
       return reponse;
    }



    /*------------------------ change cover for client -----------------------*/

    public ClientDto changeCover(ClientDto clientDto){
        ClientEntity client=clientRepository.findByPhone(clientDto.getPhone());
        ClientDto reponse=new ClientDto();
        if(client!=null){
            client.setPhoto(clientDto.getPhoto());
            ClientEntity updated=clientRepository.save(client);
            BeanUtils.copyProperties(updated,reponse);
        }
        return reponse;
    }

    /*------------------------ change password ----------------------------------*/

    public boolean changePassword(ClientDto clientDto){
        ClientEntity client=clientRepository.findByPhone(clientDto.getPhone());
        boolean status=false;

        if(client!=null){
            client.setPassword(clientDto.getPassword());
            ClientEntity updated=clientRepository.save(client);
           if(updated!=null){
               status=true;
           }
        }

        return status;
    }
}
