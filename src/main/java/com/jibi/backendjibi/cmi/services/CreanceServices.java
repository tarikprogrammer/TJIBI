package com.jibi.backendjibi.cmi.services;

import com.jibi.backendjibi.cmi.dto.CreanceDto;
import com.jibi.backendjibi.cmi.entities.Creance;
import com.jibi.backendjibi.cmi.repository.CreanceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreanceServices {
    @Autowired
    private CreanceRepository creanceRepository;



    /*-------------------- show all creance ------------------------*/

    public List<CreanceDto> creances(){
        List<Creance>creances=creanceRepository.findAll();
        List<CreanceDto>creanceDtos=new ArrayList<>();

        for(Creance creance : creances){
            CreanceDto creanceDto =new CreanceDto();
            BeanUtils.copyProperties(creance,creanceDto);
            creanceDtos.add(creanceDto);
        }


        return creanceDtos;

    }


}
