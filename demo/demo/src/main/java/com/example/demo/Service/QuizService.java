package com.example.demo.Service;

import com.example.demo.Dao.QuestionDAO;
import com.example.demo.Dao.QuizDao;
import com.example.demo.Model.Question;
import com.example.demo.Model.QuestionWrapper;
import com.example.demo.Model.Quiz;
import com.example.demo.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<String> createQuize(String category, int numQ, String title) {
        Quiz quiz = new Quiz();
        List<Question> questions =questionDAO.findQuestionsByCategory(category,numQ);
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Sucess", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionFromUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getCategory(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionFromUser.add(qw);
        }
        return new ResponseEntity<>(questionFromUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calcResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response : responses){
            if(response.getResponses().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
