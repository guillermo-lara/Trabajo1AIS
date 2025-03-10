package es.codeurjc.web.nitflex.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.repository.FilmRepository;
import es.codeurjc.web.nitflex.repository.UserRepository;
import es.codeurjc.web.nitflex.service.FilmService;
import es.codeurjc.web.nitflex.utils.ImageUtils;

//Cuando se guarda una película (sin imagen) y con un título válido utilizando 
//FilmService, se guarda en el repositorio

public class FilmTestUnitUtils {

    @Test
    public void testSaveAFilm() {

        CreateFilmRequest createFilmRequest = new CreateFilmRequest("Star Wars", "Guerra en el espacio", 1977, "16");

        Film film = new Film("Star Wars", "Guerra en el espacio", 1977, "16");

        FilmDTO filmDTO = new FilmDTO(null, "Star Wars", "Guerra en el espacio", 1977, "16", null, null);

        FilmRepository filmRepository = mock(FilmRepository.class);
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        UserRepository userRepository = mock(UserRepository.class);

        ImageUtils imageUtils = mock(ImageUtils.class);

        FilmMapper filmMapper = mock(FilmMapper.class);
        when(filmMapper.toDomain(createFilmRequest)).thenReturn(film);
        when(filmMapper.toDTO(film)).thenReturn(filmDTO);

        FilmService filmService = new FilmService(filmRepository, userRepository, imageUtils, filmMapper);

        FilmDTO result = filmService.save(createFilmRequest);

        assertNotNull(result);
        assertEquals("Star Wars", result.title());
        verify(filmMapper).toDomain(createFilmRequest);
        verify(filmRepository).save(film);
        verify(filmMapper).toDTO(film);

    }

}
