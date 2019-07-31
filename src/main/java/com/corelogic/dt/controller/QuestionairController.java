package com.corelogic.dt.controller;

import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectNotFoundResponse;
import com.corelogic.dt.domain.QuestionaireResponse;
import com.corelogic.dt.service.QuestionaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questionaire")
public class QuestionairController {

    private QuestionaireService questionaireService;

    @Autowired
    public QuestionairController(QuestionaireService questionaireService) {
        this.questionaireService = questionaireService;
    }

    @GetMapping(value = "/project/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionaireResponse> retrieveProjectNames() {
        return ResponseEntity.ok(questionaireService.retrieveProjectNames());
    }

    @GetMapping(value = "/project/{projectName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionaireResponse> retrieveProject(@PathVariable String projectName) throws ProjectNotFoundException {
        return ResponseEntity.ok(questionaireService.retrieveProject(projectName));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<QuestionaireResponse> projectNotFound(ProjectNotFoundException exception) {
        return ResponseEntity.ok(
                QuestionaireResponse
                        .builder()
                        .projectNotFoundResponse(
                                ProjectNotFoundResponse
                                        .builder()
                                        .message(exception.getMessage())
                                        .build())
                        .build());
    }
}