package com.corelogic.dt.builder;

import com.corelogic.dt.domain.Node;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JSONParser {

    private File file;

    public JSONParser(@Value(value = "${json.file.name}") String filename) throws FileNotFoundException {
        try {
            file = new File(getClass().getClassLoader().getResource(filename).getFile());
        } catch (Exception e) {
            throw new FileNotFoundException(filename + " not found");
        }
    }

    public Node parse() {
        Node projects = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            projects = mapper.readValue(file, Node.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<String> retrieveProjectNames() {
        Node projects = parse();
        return projects.getChildren().stream().map(project -> project.getName()).collect(Collectors.toList());
    }


    public Node retrieveProject(String projectName) {
        Node projects = parse();
        return projects.getChildren().stream().filter(project -> project.getName().equals(projectName))
                .findFirst().orElse(null);
    }
}
