package com.example.demo.Controller;

import com.example.demo.Model.Question;
import com.example.demo.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestion")
    public List<Question> getAllQuestion(){
        return (List<Question>) questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable("category") String category){
        return (List<Question>) questionService.getQuestionCategory(category);
    }

    @PostMapping("add/")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping()
    public String deleteQuestion(int id)
    {
        return questionService.deleteQuestion(id);
    }

}
