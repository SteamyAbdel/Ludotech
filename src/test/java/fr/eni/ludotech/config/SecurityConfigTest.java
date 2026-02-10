package fr.eni.ludotech.config;

import fr.eni.ludotech.bo.Utilisateur;
import fr.eni.ludotech.dal.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SecurityConfigTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @BeforeEach
    public void setUp() {
        utilisateurRepository.deleteAll();
        utilisateurRepository.save(new Utilisateur(
            null,
            "employe",
            passwordEncoder.encode("employe123"),
            "EMPLOYE",
            true
        ));
    }

    @Test
    public void testPasswordEncoderBean_ShouldExist() {
        assertThat(passwordEncoder).isNotNull();
    }

    @Test
    public void testUserDetailsServiceBean_ShouldExist() {
        assertThat(userDetailsService).isNotNull();
    }

    @Test
    public void testUserDetailsServiceLoadsFromDb() {
        UserDetails employe = userDetailsService.loadUserByUsername("employe");

        assertThat(employe).isNotNull();
        assertThat(employe.getUsername()).isEqualTo("employe");
        assertThat(employe.getAuthorities()).extracting("authority")
                .contains("ROLE_EMPLOYE");
        assertThat(passwordEncoder.matches("employe123", employe.getPassword())).isTrue();
    }
}
