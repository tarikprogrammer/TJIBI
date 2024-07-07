package com.jibi.backendjibi.cmi.repository;

import com.jibi.backendjibi.cmi.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepo extends JpaRepository<Paiement,Long> {
}
