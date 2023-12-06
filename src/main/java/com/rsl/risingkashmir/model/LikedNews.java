package com.rsl.risingkashmir.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "liked_news")
public class LikedNews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news_id;

    public News getNews_id() {
        return news_id;
    }

    public void setNews_id(News news_id) {
        this.news_id = news_id;
    }
}
