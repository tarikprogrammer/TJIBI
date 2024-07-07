package com.jibi.backendjibi.cmi.services;

import com.jibi.backendjibi.cmi.dto.CompteDto;
import com.jibi.backendjibi.cmi.entities.Compte;
import com.jibi.backendjibi.cmi.repository.CompteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteServices {
    @Autowired
    private CompteRepository compteRepository;

    /*----------------- creation du compte  de client -------*/

    public CompteDto createAccount(CompteDto compteDto){
        Compte compte = new Compte();
        BeanUtils.copyProperties(compteDto,compte);
        Compte create=compteRepository.save(compte);
        CompteDto response = new CompteDto();
        BeanUtils.copyProperties(create,response);
        return response;
    }
}
