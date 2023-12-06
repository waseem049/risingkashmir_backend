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
@Table(name = "news_location")
public class LocationOfNews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String locationName;
}
