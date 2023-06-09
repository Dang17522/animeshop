package com.anime.jpa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class hangngay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    private Long orderid;
    private Double sum;
    private Long count;
}
