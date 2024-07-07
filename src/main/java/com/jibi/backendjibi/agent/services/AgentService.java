package com.jibi.backendjibi.agent.services;

import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.agent.entities.Agent;
import com.jibi.backendjibi.agent.repository.RepositoryAgent;
import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.client.entities.ClientEntity;
import com.jibi.backendjibi.client.repository.ClientRepository;
import com.jibi.backendjibi.cmi.entities.Compte;
import com.jibi.backendjibi.cmi.repository.CompteRepository;
import com.jibi.backendjibi.shared.GeneratePassword;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AgentService {
    @Autowired
    private RepositoryAgent repoAgent;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteRepository compteRepository;
    private String password;

    /*----------------- creation Agent----------*/
    public AgentDto createAgent(AgentDto agentDto){
        AgentDto response=new AgentDto();
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDto,agent);
        this.password=GeneratePassword.generate().toString();
        agent.setPassword(this.password);
        agent.setAgent(false);
        Agent agentCreated=repoAgent.save(agent);
        BeanUtils.copyProperties(agentCreated,response);

        return response;
    }

    /*--------------- verify phone number-------------------*/
    public boolean verify(AgentDto agentDto){
        Agent agent = new Agent();
        boolean status=false;
        BeanUtils.copyProperties(agentDto,agent);
        String phone=agent.getPhone();
        Agent isExist=repoAgent.findByPhone(phone);
        ClientEntity client=clientRepository.findByPhone(agentDto.getPhone());
        if(isExist!=null){
            status=true;
        }
        if(client!=null){
            status=true;
        }
        return status;
    }

    /*----------------- send email ------------------*/
    public void sendEmail(AgentDto agentDto){
        System.out.println("send Email"+agentDto);
        String to=agentDto.getEmail();
        String passwordUser=this.password;
        String msg = "Cher " + agentDto.getFirstName() + " " + agentDto.getLastName() + ",\n\n" +
                "Nous sommes ravis de vous accueillir parmi nous ! Votre inscription sur notre plateforme a été complétée avec succès.\n\n" +
                "Détails de votre inscription :\n\n" +
                "Nom complet : " + agentDto.getFirstName() + " " + agentDto.getLastName() + "\n" +
                "Adresse e-mail : " + agentDto.getEmail() + "\n" +
                "Votre mot de passe : " + passwordUser + "\n\n" +
                "Nous vous remercions pour votre confiance. Vous pouvez désormais profiter de tous les avantages et services que notre plateforme offre.\n\n" +
                "Si vous avez des questions ou besoin d'assistance, n'hésitez pas à nous contacter à l'adresse suivante : support@TJIBI.com.\n\n" +
                "Bien cordialement,\n\n" +
                "TJIBI";
        String subject="TJIBI's Registration";

        SimpleMailMessage message=new SimpleMailMessage();
       message.setFrom("tarikbelaid12334@gmail.com");
       message.setText(msg);
       message.setSubject(subject);
       message.setTo(to);
       mailSender.send(message);
    }


    /*----------------------- changer cover de Agent ---------------------------------*/
    public AgentDto changeCover(AgentDto agentDto){
        Agent agent=repoAgent.findByPhone(agentDto.getPhone());
        AgentDto response=new AgentDto();
        if(agent!=null){
            agent.setPhoto(agentDto.getPhoto());
            Agent updatedCover=repoAgent.save(agent);
            BeanUtils.copyProperties(updatedCover,response);
        }

        return response;
    }


    /*-------------------- update agent --------------------------------*/
    public AgentDto update(AgentDto agentDto){
        Agent agent=repoAgent.findByPhone(agentDto.getPhone());
        AgentDto response=new AgentDto();
        if(agent!=null){
            agent.setFirstName(agentDto.getFirstName());
            agent.setLastName(agentDto.getLastName());
            agent.setEmail(agentDto.getEmail());
            agent.setPassword(agentDto.getPassword());
            Agent updated=repoAgent.save(agent);
            BeanUtils.copyProperties(updated,response);
        }
        return response;
    }



    /*----------------- update password ----------------------*/

    public boolean updatePassword(AgentDto agentDto){
        boolean status=false;
        Agent agent=repoAgent.findByPhone(agentDto.getPhone());
        AgentDto response=new AgentDto();
        if(agent!=null){
           agent.setPassword(agentDto.getPassword());
            Agent updated=repoAgent.save(agent);
            status=true;
        }
        return status;
    }



    /*--------------------------- show clinets --------------------------*/
    public List<ClientDto> showClients(){
        List<ClientEntity>clients=clientRepository.findAll();
        List<ClientDto>clientDtos=new ArrayList<>();
        for(ClientEntity client:clients){
            ClientDto clientDto=new ClientDto();
            BeanUtils.copyProperties(client,clientDto);
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }

    /*-------------------------------- search a client --------------------------*/

    public ClientDto searchClient(ClientDto clientDto){
        ClientEntity client =clientRepository.findByPhone(clientDto.getPhone());
        ClientDto response =new ClientDto();
        if(client!=null){
            BeanUtils.copyProperties(client,response);
        }
        return response;
    }


    /*------------------------------- change account for client -------------------*/
    public boolean changeAccount(ClientDto clientDto){
        ClientEntity client =clientRepository.findByPhone(clientDto.getPhone());
        double plafond=0;
        double balance=0;
        boolean status=false;
        if(client!=null){
            client.setAccount(clientDto.getAccount());

            switch (clientDto.getAccount()){
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
                case "Account 3000$":
                    plafond = 3000;
                    balance = 3000;
                    break;
                default:
                    plafond = client.getCompte().getPlafond();
                    balance = client.getCompte().getBalance();
            }
            Compte compte=client.getCompte();
            compte.setPlafond(plafond);
            compte.setBalance(balance);
            Compte update=compteRepository.save(compte);
            if(update!=null){
                status=true;
            }
        }
        return status;
    }
}
