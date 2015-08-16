package br.ufjf.nenc.broadecos.auth.services;

import br.ufjf.nenc.broadecos.auth.model.bo.AuthClientDetail;
import br.ufjf.nenc.broadecos.auth.model.entity.Client;
import br.ufjf.nenc.broadecos.auth.repository.ClientRepository;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.*;

@Service
@Slf4j
public class ClientService implements ClientDetailsService {

    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = null;

        try {
            Client client = repository.findByClientId(clientId);
            checkClient(client);

            clientDetails = new AuthClientDetail(client);

        } catch (Throwable e){
            log.error("Failure loading access allowed client", e);
            throw new ClientRegistrationException(e.getMessage());
        }
        return clientDetails;
    }

    private void checkClient(Client client){
        checkArgument(client != null, "Client cannot be null.");
        checkArgument(client.hasAccessAllowed());
    }
}
