package com.rsl.risingkashmir;

import com.rsl.risingkashmir.model.News;
import com.rsl.risingkashmir.repository.NewsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RisingKashmirApplication implements CommandLineRunner {

	private final NewsRepository newsRepository;

	public RisingKashmirApplication(NewsRepository newsRepository){
		this.newsRepository = newsRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RisingKashmirApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		News news = new News();
		news.setNewsTitle("News Title added statically");
		news.setNewsDescription("News Description Created Statically");
	}
}
