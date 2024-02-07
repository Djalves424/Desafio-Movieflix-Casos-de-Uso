package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public List<ReviewDTO> findAll() {
        List<Review> findAllReviews = reviewRepository.findAll();
        List<ReviewDTO> dto = findAllReviews.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
        return dto;
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie entity = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieDetailsDTO> findByGenre(List<Long> genreId, Pageable pageable) {
        List<Genre> genres = genreRepository.findAllById(genreId);
        if (genres.isEmpty()) return movieRepository.searchAll(PageRequest.of(0, 5, Sort.by("title"))).map(MovieDetailsDTO::new);
        Genre genre = genres.get(0);
        Page<Movie> movies = movieRepository.findByGenreId(genre, pageable);
        return movies.map(MovieDetailsDTO::new);
    }
}
