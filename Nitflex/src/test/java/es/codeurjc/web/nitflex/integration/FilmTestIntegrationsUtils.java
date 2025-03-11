package es.codeurjc.web.nitflex.integration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;
import es.codeurjc.web.nitflex.dto.film.FilmSimpleDTO;
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

    private FilmMapper filmMapper;
    private CreateFilmRequest createfilmRequest;
    private FilmDTO filmDTO;
    private Film film;

    @BeforeEach
    private void init(){
        filmMapper = Mappers.getMapper(FilmMapper.class);
        createfilmRequest = new CreateFilmRequest("Test Movie", "Test Synopsis", 2024, "PG-13");
    }

    //Cuando se añade una película con un título válido mediante FilmService, se guarda 
    //en la base de datos y se devuelve la película creada
    @Test
    public void add_a_title_and_save_in_repository_returns_a_created_film() {
        createfilmRequest = new CreateFilmRequest("Test Movie", "Test Synopsis", 2024, "PG-13");
        filmDTO = filmService.save(createfilmRequest);
        assertNotNull(filmDTO);
        film = filmMapper.toDomain(filmDTO);

        Optional<Film> filmFromRep = filmRepository.findById(film.getId());
        assertTrue(filmFromRep.isPresent());
        assertEquals("Test Movie", filmFromRep.get().getTitle());

    }

    //Cuando se actualiza los campos 'title' y 'synopsis' de una película (con imagen) y 
    //con un título válido mediante FilmService, se guardan los cambios en la base de 
    //datos y la imagen no cambia
    @Test
    public void update_title_and_synopsis_save_in_database() {
        createfilmRequest = new CreateFilmRequest("Updated Title", "Updated Synopsis", 2024, "PEGI3");
        filmDTO = filmService.save(createfilmRequest);
        assertNotNull(filmDTO);
        film = filmMapper.toDomain(filmDTO);

        FilmSimpleDTO updateSimpleDTO=new FilmSimpleDTO(filmDTO.id(), filmDTO.title(),filmDTO.synopsis(), filmDTO.releaseYear(), filmDTO.ageRating());
        filmService.update(film.getId(), updateSimpleDTO);
        
        Optional<Film> updatedFilm = filmRepository.findById(film.getId());
        assertTrue(updatedFilm.isPresent());
        assertEquals("Updated Title", updatedFilm.get().getTitle());
        assertEquals("Updated Synopsis", updatedFilm.get().getSynopsis());
        
        assertEquals(film.getPosterFile(), updatedFilm.get().getPosterFile());
    }
}