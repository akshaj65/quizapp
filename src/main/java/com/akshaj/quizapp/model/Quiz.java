package com.akshaj.quizapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) annotation specifies that the primary key values should be generated by the database using the identity strategy.
//    By using GenerationType.IDENTITY, you don't need to manually assign values to the primary key field when inserting new records.
//    The database will take care of generating and assigning the unique values automatically.
    private Integer Id;
    private String title;


//    By using the @ManyToMany annotation and properly configuring the relationship, '
//    you can establish a many-to-many association between the Question entity and another entity, allowing
//    each Question object to be associated with multiple instances of the other entity, and vice versa.
    @ManyToMany
    private List<Question> questions;
}
