package com.rsl.risingkashmir.controller;

import com.rsl.risingkashmir.exception.ResourceNotFoundException;
import com.rsl.risingkashmir.model.News;
import com.rsl.risingkashmir.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/home/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    // create a news
    @PostMapping
    public News createNews(@RequestBody News news){
        return newsRepository.save(news);
    }

    // update the news
    @PutMapping("{id}")
    public ResponseEntity<News> updateNews(@PathVariable Integer id, @RequestBody News newsDetails){
        News updateNews = newsRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("News not found bearing id: "+id));

        updateNews.setNewsTitle(newsDetails.getNewsTitle());
        updateNews.setNewsDescription(newsDetails.getNewsDescription());

        newsRepository.save(updateNews);

        return ResponseEntity.ok(updateNews);
    }
}
