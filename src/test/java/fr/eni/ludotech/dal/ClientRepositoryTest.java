package fr.eni.ludotech.dal;

import fr.eni.ludotech.bo.Adresse;
import fr.eni.ludotech.bo.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void creationEtFindById() {
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Alice");
        client.setEmail("alice.dupont@example.test");

        Adresse adresse = new Adresse();
        adresse.setRue("10 rue des Jeux");
        adresse.setCodePostal("44000");
        adresse.setVille("Nantes");
        client.setAdresse(adresse);

        Client saved = clientRepository.save(client);

        assertThat(saved.getId()).isNotNull();

        Optional<Client> loaded = clientRepository.findById(saved.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getNom()).isEqualTo("Dupont");
        assertThat(loaded.get().getPrenom()).isEqualTo("Alice");
        assertThat(loaded.get().getAdresse()).isNotNull();
        assertThat(loaded.get().getAdresse().getVille()).isEqualTo("Nantes");
    }
}
