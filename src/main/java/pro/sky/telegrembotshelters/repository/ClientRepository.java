package pro.sky.telegrembotshelters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrembotshelters.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
