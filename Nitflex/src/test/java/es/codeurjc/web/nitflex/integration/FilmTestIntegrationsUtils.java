package es.codeurjc.web.nitflex.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.repository.FilmRepository;
import es.codeurjc.web.nitflex.service.FilmService;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class FilmTestIntegrationsUtils {
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void testAFilm() {

        FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

        CreateFilmRequest createfilmRequest = new CreateFilmRequest("Test Movie", "Test Synopsis", 2024, "PG-13");

        FilmDTO filmDTO = filmService.save(createfilmRequest);
        assertNotNull(filmDTO);

        Film film = filmMapper.toDomain(filmDTO);

        Optional<Film> filmFromRep = filmRepository.findById(film.getId());
        assertTrue(filmFromRep.isPresent());
        assertEquals("Test Movie", filmFromRep.get().getTitle());

    }
}
