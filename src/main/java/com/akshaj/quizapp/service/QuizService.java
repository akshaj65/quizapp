package com.akshaj.quizapp.service;

import com.akshaj.quizapp.dao.QuestionDao;
import com.akshaj.quizapp.dao.QuizDao;
import com.akshaj.quizapp.model.Question;
import com.akshaj.quizapp.model.QuestionWrapper;
import com.akshaj.quizapp.model.Quiz;
import com.akshaj.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(numQ,category);
        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return  new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz= quizDao.findById(id); //data might come or not so use optional
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        if(quiz.isPresent()){
            List<Question> questionFromDb = quiz.get().getQuestions(); //we need to use .get() when we are using Optional


            for (Question q:questionFromDb) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionForUser.add(qw);
            }
            return new ResponseEntity<>(questionForUser,HttpStatus.OK);
        }

        return new ResponseEntity<>(questionForUser,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz= quizDao.findById(id).get();
        int right=0;
        int i=0;
        List<Question> questions= quiz.getQuestions();
        responses.sort(Comparator.comparingInt(Response::getId)); // we can eliminate this operation if we are sorting the responses list at client side
        questions.sort((q1, q2) -> q1.getId()- q2.getId() ); //sort in ascending order
        for (Response response:responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                 right++;
            }
            i++;
        }
        return  new ResponseEntity<>(right,HttpStatus.OK);
    }
}
