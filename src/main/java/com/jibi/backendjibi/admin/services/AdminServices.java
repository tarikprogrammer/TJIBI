package com.jibi.backendjibi.admin.services;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.jibi.backendjibi.admin.repository.AdminRepository;
import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.agent.entities.Agent;
import com.jibi.backendjibi.agent.repository.RepositoryAgent;
import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.client.entities.ClientEntity;
import com.jibi.backendjibi.client.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServices {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RepositoryAgent repositoryAgent;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JavaMailSender mailSender;

    /*------------show clients ------------------*/
    public List<ClientDto> showClients(){
        List<ClientEntity> client=clientRepository.findAll();
        List<ClientDto>clientDtos=new ArrayList<>();

        for(ClientEntity client1 :client){
            ClientDto clientDto=new ClientDto();
            BeanUtils.copyProperties(client1,clientDto);
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }
    /*------------show Agents ------------------*/

    public List<AgentDto> showAgents(){
        List<Agent>agents=repositoryAgent.findAll();
        List<AgentDto>agentDtos=new ArrayList<>();
        for(Agent agent:agents){
            AgentDto agentDto=new AgentDto();
            BeanUtils.copyProperties(agent,agentDto);
            agentDtos.add(agentDto);
        }
        return agentDtos;

    }

    /*------------ delete client ---------------*/
    public void deleteClient(String phone){
        ClientEntity client =clientRepository.findByPhone(phone);
        if(client!=null){
            clientRepository.delete(client);
        }

    }


    /*----------------------send Email ------------------------*/

    public void sendEmail(AgentDto agentDto){
        System.out.println("send Email"+agentDto);
        String to=agentDto.getEmail();
        String msg = "Cher Agent, " + agentDto.getFirstName() + " " + agentDto.getLastName() + ",\n\n" +
                "Nous avons le plaisir de vous informer que votre compte sur notre plateforme a été activé avec succès.\n\n" +
                "Vous pouvez dès à présent accéder à tous les services et avantages offerts par notre plateforme.\n\n" +
                "Si vous avez des questions ou besoin d'assistance, n'hésitez pas à nous contacter à l'adresse suivante : support@TJIBI.com.\n\n" +
                "Bien cordialement,\n\n" +
                "TJIBI";
        String subject = "Activation de votre compte sur TJIBI";
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("tarikbelaid12334@gmail.com");
        message.setText(msg);
        message.setSubject(subject);
        message.setTo(to);
        mailSender.send(message);
    }


    /*------------------ delete agent ----------------*/

    public void deleteAgent(String phone){
        Agent agent=repositoryAgent.findByPhone(phone);

        if(agent!=null){
            repositoryAgent.delete(agent);
        }
    }


    /*--------------------- activate account for agent -------------*/

    public boolean activateAgent(String phone){
        Agent agent=repositoryAgent.findByPhone(phone);
        AgentDto agentDto=new AgentDto();
        boolean status=false;
        if(agent!=null){
            agent.setAgent(true);
            BeanUtils.copyProperties(agent,agentDto);
            Agent update=repositoryAgent.save(agent);
            if(update!=null){
                this.sendEmail(agentDto);
                status=true;
            }
        }
        return status;
    }

}
