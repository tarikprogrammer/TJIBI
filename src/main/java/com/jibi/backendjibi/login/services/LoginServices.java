package com.jibi.backendjibi.login.services;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import ch.qos.logback.core.net.server.Client;
import com.jibi.backendjibi.agent.dto.AgentDto;
import com.jibi.backendjibi.agent.entities.Agent;
import com.jibi.backendjibi.agent.repository.RepositoryAgent;
import com.jibi.backendjibi.client.dto.ClientDto;
import com.jibi.backendjibi.client.entities.ClientEntity;
import com.jibi.backendjibi.client.repository.ClientRepository;
import com.jibi.backendjibi.login.dto.LoginDto;
import com.jibi.backendjibi.login.dto.ResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {
    @Autowired
    private RepositoryAgent repositoryAgent;
    @Autowired
    private ClientRepository clientRepository;

    public boolean checkPhone(LoginDto loginDto){
        System.out.println("checkPhone "+loginDto);
        boolean status=false;
        Agent agent = repositoryAgent.findByPhone(loginDto.getPhone());
        if(agent!=null && agent.isAgent()){
            status=true;
        }
        return status;
    }
    public AgentDto loginForAgent(LoginDto loginDto){
        Agent agent =repositoryAgent.findByPhone(loginDto.getPhone());
        AgentDto response= new AgentDto();
        System.out.println("loginForAgent"+loginDto);
        System.out.println("AgentDto avant"+response);
        if(agent!=null){
            System.out.println("agent"+agent);
            System.out.println("password agent dto "+loginDto.getPassword());
            if(agent.getPassword().equals(loginDto.getPassword())){
                BeanUtils.copyProperties(agent,response);
                System.out.println("AgentDto apres"+response);
            }
        }
        return response;
    }
    public ClientDto loginForClient(LoginDto loginDto){
        ClientDto response = new ClientDto();
        ClientEntity client =clientRepository.findByPhone(loginDto.getPhone());

        if(client!=null){
            if(client.getPassword().equals(loginDto.getPassword())){
                BeanUtils.copyProperties(client,response);
            }
        }
        return response;
    }
}
