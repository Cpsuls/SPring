package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name="SH1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "Код_поста")
    private String kod_posta;
    @Column(name = "Уровень_воды")
    private String waterlevel;
    @Column(name = "Температура_воздуха")
    private String temperature;
    @Column(name = "Атмосферное_давление")
    private String pressure;
    @Column(name = "Скорость_ветра")
    private String windspeed;
    @Column(name = "Толщина_снежного_покрова")
    private String snowlevel;
    @Column(name = "Количество_осадков")
    private String kolichestvoosadkov;
    @Column(name = "Дата_время")
    private String date;
}
