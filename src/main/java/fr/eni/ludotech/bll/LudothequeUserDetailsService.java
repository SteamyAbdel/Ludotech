package fr.eni.ludotech.bll;

import fr.eni.ludotech.bo.Utilisateur;
import fr.eni.ludotech.dal.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LudothequeUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public LudothequeUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + username));

        return User.builder()
            .username(utilisateur.getUsername())
            .password(utilisateur.getMotDePasse())
            .roles(utilisateur.getRole())
            .disabled(!utilisateur.isActif())
            .build();
    }
}

