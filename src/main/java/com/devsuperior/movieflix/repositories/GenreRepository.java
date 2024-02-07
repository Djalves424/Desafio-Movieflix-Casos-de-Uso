package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT obj FROM Genre obj "
            + "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Genre> searchByName(String name);
}
