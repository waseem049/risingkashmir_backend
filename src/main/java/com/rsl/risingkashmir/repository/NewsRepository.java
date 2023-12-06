package com.rsl.risingkashmir.repository;

import com.rsl.risingkashmir.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

}
