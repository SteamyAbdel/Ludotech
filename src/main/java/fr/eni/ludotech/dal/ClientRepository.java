package fr.eni.ludotech.dal;

import fr.eni.ludotech.bo.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
