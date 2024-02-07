package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> findAll(String name) {
        List<Genre> genres = genreRepository.searchByName(name);
        List<GenreDTO> dto = genres.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
        return dto;
    }
}
