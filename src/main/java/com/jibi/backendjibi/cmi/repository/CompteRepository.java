package com.jibi.backendjibi.cmi.repository;

import com.jibi.backendjibi.cmi.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Long> {
 Compte findByRib(String rib);
}
