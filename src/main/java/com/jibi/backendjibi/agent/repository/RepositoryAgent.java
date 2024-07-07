package com.jibi.backendjibi.agent.repository;

import com.jibi.backendjibi.agent.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  RepositoryAgent  extends JpaRepository<Agent,Long> {
    Agent findByPhone(String phone);
}
