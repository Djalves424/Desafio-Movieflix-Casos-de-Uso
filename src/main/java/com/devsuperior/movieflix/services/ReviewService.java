package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewDTO insert(ReviewDTO dto) {

        User entity = authService.authenticated();
        Review review = new Review();

        try {
            review.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
            review.setText(dto.getText());
            review.setUser(entity);
            repository.save(review);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Movie not found");
        }
        return new ReviewDTO(review);
    }
}
