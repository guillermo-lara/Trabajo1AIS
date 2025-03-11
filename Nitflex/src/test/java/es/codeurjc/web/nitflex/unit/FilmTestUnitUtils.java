package es.codeurjc.web.nitflex.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.codeurjc.web.nitflex.dto.film.CreateFilmRequest;
import es.codeurjc.web.nitflex.dto.film.FilmDTO;
import es.codeurjc.web.nitflex.dto.film.FilmMapper;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.repository.FilmRepository;
import es.codeurjc.web.nitflex.repository.UserRepository;
import es.codeurjc.web.nitflex.service.FilmService;
import es.codeurjc.web.nitflex.utils.ImageUtils;


public class FilmTestUnitUtils {
    private FilmRepository filmRepository;
    private FilmDTO filmDTO;
    private Film film;
    private CreateFilmRequest createFilmRequest;

    @BeforeEach
    private void init(){
        
        createFilmRequest = new CreateFilmRequest("Star Wars", "Guerra en el espacio", 1977, "16");
        film = new Film("Star Wars", "Guerra en el espacio", 1977, "16");
        filmDTO = new FilmDTO(null, "Star Wars", "Guerra en el espacio", 1977, "16", null, null);
        filmRepository = mock(FilmRepository.class);
    }
    
    //Cuando se guarda una película (sin imagen) y con un título válido utilizando 
    //FilmService, se guarda en el repositorio

    @Test
    public void when_save_film_without_image_save_in_repository() {

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

    //Cuando se borra una película que no existe utilizando FilmService, no se elimina 
    //del repositorio y se obtiene una excepción
    @Test
    public void when_delete_an_nonexistent_film_throws_an_exception(){

        when(filmRepository.findById(anyLong())).thenReturn(null);

        UserRepository userRepository = mock(UserRepository.class);
        ImageUtils imageUtils = mock(ImageUtils.class);
        FilmMapper filmMapper = mock(FilmMapper.class);

        FilmService filmService = new FilmService(filmRepository, userRepository, imageUtils, filmMapper);

        assertThrows(NullPointerException.class, () -> {
            filmService.delete(1L);
        });

        verify(filmRepository, never()).deleteById(anyLong());
    }

}