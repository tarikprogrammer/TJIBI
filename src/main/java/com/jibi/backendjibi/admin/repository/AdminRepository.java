package com.jibi.backendjibi.admin.repository;

import com.jibi.backendjibi.admin.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
