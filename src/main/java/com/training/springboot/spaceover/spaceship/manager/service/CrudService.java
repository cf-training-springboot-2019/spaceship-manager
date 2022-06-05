package com.training.springboot.spaceover.spaceship.manager.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T> {

    Page<T> findAll(T entitySample, Pageable pageRequest);

    List<T> findAll();

    T findBydId(Long id);

    T save(T entity);

    T update(T entity);

    void deleteById(Long id);

}