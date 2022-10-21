package com.sushi.flower.controller;

import com.sushi.flower.model.MovieEntity;
import com.sushi.flower.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @PutMapping
    Mono<MovieEntity> createOrUpdateMovie(@RequestBody MovieEntity newMovie) {
        return movieRepository.save(newMovie);
    }

    @GetMapping(value = { "", "/" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MovieEntity> getMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/by-title")
    Mono<MovieEntity> byTitle(@RequestParam String title) {
        return movieRepository.findOneByTitle(title);
    }

    @DeleteMapping("{id}")
    Mono<Void> delete(@PathVariable String id) {
        return movieRepository.deleteById(id);
    }
}
