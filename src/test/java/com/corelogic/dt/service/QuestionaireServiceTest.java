package com.corelogic.dt.service;

import com.corelogic.dt.builder.JSONParser;
import com.corelogic.dt.domain.Node;
import com.corelogic.dt.domain.NodeResponse;
import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectResponse;
import com.corelogic.dt.domain.QuestionaireResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionaireServiceTest {

    private QuestionaireService subject;

    @Mock
    private JSONParser mockJSONParser;

    @Before
    public void setUp() {
        subject = new QuestionaireService(mockJSONParser);
    }

    @Test
    public void retrieveProjectNames_returnsNames() {
        when(mockJSONParser.retrieveProjectNames())
                .thenReturn(Arrays.asList("Paint Projects", "Home Remodel Projects"));

        QuestionaireResponse actuals = subject.retrieveProjectNames();

        assertEquals(QuestionaireResponse.builder().projectResponses(Arrays.asList(ProjectResponse.builder().name("Paint Projects").build(),
                ProjectResponse.builder().name("Home Remodel Projects").build())).build(), actuals);
        verify(mockJSONParser).retrieveProjectNames();
    }


    @Test
    public void retrieveProject_returnsProjectNodeResponse() throws ProjectNotFoundException, FileNotFoundException {
        JSONParser parser = new JSONParser("dt-small.json");
        Node paintProjects = parser.parse();
        when(mockJSONParser.retrieveProject("Paint Projects")).thenReturn(paintProjects);

        QuestionaireResponse expected = QuestionaireResponse.builder().nodeResponse(buildNodeResponse(paintProjects)).build();

        QuestionaireResponse actual = subject.retrieveProject("Paint Projects");

        assertEquals(expected, actual);
        verify(mockJSONParser).retrieveProject("Paint Projects");
    }

    @Test(expected = ProjectNotFoundException.class)
    public void retrieveProject_whenInvalidProjectName_throwsProjectNotFoundException() throws ProjectNotFoundException {
        when(mockJSONParser.retrieveProject("Invalid Name")).thenReturn(null);
        subject.retrieveProject("Invalid Name");
    }

    private NodeResponse buildNodeResponse(Node node) {
        return NodeResponse
                .builder()
                .name(node.getName())
                .children(node.getChildren().stream()
                        .map(child -> buildNodeResponse(child)).collect(Collectors.toList())).build();
    }
}