package com.akshaj.quizapp.model;

import lombok.Data;

import javax.persistence.*;

//when we use lombok we don't need to create setter and getter for every variable
@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
//    : This strategy indicates that the persistence provider
//       should use a database sequence to generate unique primary key values.
//      It requires additional select statements to get the next value from the sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String questionTitle;
    private String Option1;
    private String Option2;
    private String Option3;
    private String Option4;
    private String rightAnswer;
    private String difficultyLevel;
    private  String category;


}
