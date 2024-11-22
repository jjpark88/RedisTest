package org.example.domain.repository;

import org.example.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Client, Long> {
}
