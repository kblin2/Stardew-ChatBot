package springai.aiproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionController {
    @GetMapping("/ask")
    ResponseEntity<String> askQuestion(@RequestParam String greeting) {
        System.out.println(greeting);
        return ResponseEntity.ok(greeting);
    }
}
