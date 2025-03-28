package es.codeurjc.web.nitflex.dto.film;

import es.codeurjc.web.nitflex.dto.review.ReviewSimpleDTO;
import es.codeurjc.web.nitflex.dto.user.UserSimpleDTO;
import es.codeurjc.web.nitflex.model.Film;
import es.codeurjc.web.nitflex.model.Review;
import es.codeurjc.web.nitflex.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T13:09:07+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class FilmMapperImpl implements FilmMapper {

    @Override
    public FilmDTO toDTO(Film film) {
        if ( film == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String synopsis = null;
        int releaseYear = 0;
        String ageRating = null;
        List<ReviewSimpleDTO> reviews = null;
        List<UserSimpleDTO> usersThatLiked = null;

        id = film.getId();
        title = film.getTitle();
        synopsis = film.getSynopsis();
        releaseYear = film.getReleaseYear();
        ageRating = film.getAgeRating();
        reviews = reviewListToReviewSimpleDTOList( film.getReviews() );
        usersThatLiked = userListToUserSimpleDTOList( film.getUsersThatLiked() );

        FilmDTO filmDTO = new FilmDTO( id, title, synopsis, releaseYear, ageRating, reviews, usersThatLiked );

        return filmDTO;
    }

    @Override
    public List<FilmDTO> toDTOs(Collection<Film> films) {
        if ( films == null ) {
            return null;
        }

        List<FilmDTO> list = new ArrayList<FilmDTO>( films.size() );
        for ( Film film : films ) {
            list.add( toDTO( film ) );
        }

        return list;
    }

    @Override
    public Film toDomain(FilmDTO filmDTO) {
        if ( filmDTO == null ) {
            return null;
        }

        Film film = new Film();

        film.setId( filmDTO.id() );
        film.setTitle( filmDTO.title() );
        film.setSynopsis( filmDTO.synopsis() );
        film.setReleaseYear( filmDTO.releaseYear() );
        film.setAgeRating( filmDTO.ageRating() );
        if ( film.getReviews() != null ) {
            List<Review> list = reviewSimpleDTOListToReviewList( filmDTO.reviews() );
            if ( list != null ) {
                film.getReviews().addAll( list );
            }
        }
        if ( film.getUsersThatLiked() != null ) {
            List<User> list1 = userSimpleDTOListToUserList( filmDTO.usersThatLiked() );
            if ( list1 != null ) {
                film.getUsersThatLiked().addAll( list1 );
            }
        }

        return film;
    }

    @Override
    public List<FilmDTO> toDTO(List<Film> all) {
        if ( all == null ) {
            return null;
        }

        List<FilmDTO> list = new ArrayList<FilmDTO>( all.size() );
        for ( Film film : all ) {
            list.add( toDTO( film ) );
        }

        return list;
    }

    @Override
    public Film toDomain(CreateFilmRequest film) {
        if ( film == null ) {
            return null;
        }

        Film film1 = new Film();

        film1.setTitle( film.title() );
        film1.setSynopsis( film.synopsis() );
        film1.setReleaseYear( film.releaseYear() );
        film1.setAgeRating( film.ageRating() );

        return film1;
    }

    @Override
    public CreateFilmRequest toCreateFilmRequest(Film film) {
        if ( film == null ) {
            return null;
        }

        String title = null;
        String synopsis = null;
        int releaseYear = 0;
        String ageRating = null;

        title = film.getTitle();
        synopsis = film.getSynopsis();
        releaseYear = film.getReleaseYear();
        ageRating = film.getAgeRating();

        CreateFilmRequest createFilmRequest = new CreateFilmRequest( title, synopsis, releaseYear, ageRating );

        return createFilmRequest;
    }

    @Override
    public FilmSimpleDTO toFilmSimpleDTO(Film updatedFilm) {
        if ( updatedFilm == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String synopsis = null;
        int releaseYear = 0;
        String ageRating = null;

        id = updatedFilm.getId();
        title = updatedFilm.getTitle();
        synopsis = updatedFilm.getSynopsis();
        releaseYear = updatedFilm.getReleaseYear();
        ageRating = updatedFilm.getAgeRating();

        FilmSimpleDTO filmSimpleDTO = new FilmSimpleDTO( id, title, synopsis, releaseYear, ageRating );

        return filmSimpleDTO;
    }

    protected UserSimpleDTO userToUserSimpleDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserSimpleDTO userSimpleDTO = new UserSimpleDTO( id, name, email );

        return userSimpleDTO;
    }

    protected ReviewSimpleDTO reviewToReviewSimpleDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        Long id = null;
        String text = null;
        int score = 0;
        Date created_at = null;
        UserSimpleDTO user = null;

        id = review.getId();
        text = review.getText();
        score = review.getScore();
        created_at = review.getCreated_at();
        user = userToUserSimpleDTO( review.getUser() );

        ReviewSimpleDTO reviewSimpleDTO = new ReviewSimpleDTO( id, text, score, created_at, user );

        return reviewSimpleDTO;
    }

    protected List<ReviewSimpleDTO> reviewListToReviewSimpleDTOList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewSimpleDTO> list1 = new ArrayList<ReviewSimpleDTO>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewToReviewSimpleDTO( review ) );
        }

        return list1;
    }

    protected List<UserSimpleDTO> userListToUserSimpleDTOList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserSimpleDTO> list1 = new ArrayList<UserSimpleDTO>( list.size() );
        for ( User user : list ) {
            list1.add( userToUserSimpleDTO( user ) );
        }

        return list1;
    }

    protected User userSimpleDTOToUser(UserSimpleDTO userSimpleDTO) {
        if ( userSimpleDTO == null ) {
            return null;
        }

        User user = new User();

        if ( userSimpleDTO.id() != null ) {
            user.setId( userSimpleDTO.id() );
        }
        user.setName( userSimpleDTO.name() );
        user.setEmail( userSimpleDTO.email() );

        return user;
    }

    protected Review reviewSimpleDTOToReview(ReviewSimpleDTO reviewSimpleDTO) {
        if ( reviewSimpleDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewSimpleDTO.id() );
        review.setText( reviewSimpleDTO.text() );
        review.setScore( reviewSimpleDTO.score() );
        review.setUser( userSimpleDTOToUser( reviewSimpleDTO.user() ) );

        return review;
    }

    protected List<Review> reviewSimpleDTOListToReviewList(List<ReviewSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Review> list1 = new ArrayList<Review>( list.size() );
        for ( ReviewSimpleDTO reviewSimpleDTO : list ) {
            list1.add( reviewSimpleDTOToReview( reviewSimpleDTO ) );
        }

        return list1;
    }

    protected List<User> userSimpleDTOListToUserList(List<UserSimpleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<User> list1 = new ArrayList<User>( list.size() );
        for ( UserSimpleDTO userSimpleDTO : list ) {
            list1.add( userSimpleDTOToUser( userSimpleDTO ) );
        }

        return list1;
    }
}
