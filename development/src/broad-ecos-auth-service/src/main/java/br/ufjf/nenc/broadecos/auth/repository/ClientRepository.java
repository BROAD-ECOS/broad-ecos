package br.ufjf.nenc.broadecos.auth.repository;

import br.ufjf.nenc.broadecos.auth.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findByClientId(String clientId);
}
