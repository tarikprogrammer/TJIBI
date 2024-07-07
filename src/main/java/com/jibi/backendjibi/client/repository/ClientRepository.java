package com.jibi.backendjibi.client.repository;

import com.jibi.backendjibi.client.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    ClientEntity findByPhone(String phone);
}
