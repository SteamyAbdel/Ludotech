package fr.eni.ludotech.bll;

import fr.eni.ludotech.bo.Genre;
import fr.eni.ludotech.bo.Jeu;
import fr.eni.ludotech.dal.GenreRepository;
import fr.eni.ludotech.dal.JeuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class JeuService {

    private final JeuRepository jeuRepository;
    private final GenreRepository genreRepository;

    public JeuService(JeuRepository jeuRepository, GenreRepository genreRepository) {
        this.jeuRepository = jeuRepository;
        this.genreRepository = genreRepository;
    }

    public Jeu addJeu(Jeu jeu) {
        if (jeu == null) {
            throw new IllegalArgumentException("Jeu obligatoire");
        }
        Set<Genre> managedGenres = new HashSet<>();
        if (jeu.getGenres() != null) {
            for (Genre genre : jeu.getGenres()) {
                if (genre == null || genre.getId() == null) {
                    throw new IllegalArgumentException("Genre id obligatoire");
                }
                Genre managed = genreRepository.findById(genre.getId())
                    .orElseThrow(() -> new GenreNotFoundException(genre.getId()));
                managedGenres.add(managed);
            }
        }
        jeu.setGenres(managedGenres);
        return jeuRepository.save(jeu);
    }
}
