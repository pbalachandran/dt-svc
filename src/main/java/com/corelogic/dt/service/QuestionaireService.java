package com.corelogic.dt.service;

import com.corelogic.dt.builder.JSONParser;
import com.corelogic.dt.domain.Node;
import com.corelogic.dt.domain.NodeResponse;
import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectResponse;
import com.corelogic.dt.domain.QuestionaireResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionaireService {

    private JSONParser parser;

    @Autowired
    public QuestionaireService(JSONParser jsonParser) {
        this.parser = jsonParser;
    }

    public QuestionaireResponse retrieveProjectNames() {
        List<String> projectNames = parser.retrieveProjectNames();
        List<ProjectResponse> projectResponses = projectNames.stream().map(projectName -> ProjectResponse.builder().name(projectName).build()).collect(Collectors.toList());
        return QuestionaireResponse
                .builder()
                .projectResponses(projectResponses)
                .build();
    }

    public QuestionaireResponse retrieveProject(String projectName) throws ProjectNotFoundException {
        Node project = parser.retrieveProject(projectName);
        if (project == null) {
            throw new ProjectNotFoundException("Could not find " + projectName);
        }
        return QuestionaireResponse.builder().nodeResponse(buildNodeResponse(project)).build();
    }

    private NodeResponse buildNodeResponse(Node node) {
        return NodeResponse
                .builder()
                .name(node.getName())
                .children(node.getChildren().stream()
                        .map(child -> buildNodeResponse(child)).collect(Collectors.toList())).build();

    }
}
