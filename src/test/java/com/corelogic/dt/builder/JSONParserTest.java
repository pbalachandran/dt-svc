package com.corelogic.dt.builder;

import com.corelogic.dt.domain.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class JSONParserTest {

    private JSONParser subject;

    @Test(expected = FileNotFoundException.class)
    public void parse_opensFile_throwsExceptionOnInvalidFilename() throws FileNotFoundException {
        subject = new JSONParser("invalid file");
    }

    @Test
    public void parse_returnsProjectsNode() throws FileNotFoundException {
        subject = new JSONParser("dt.json");
        Node actual = subject.parse();

        assertNotNull(actual);
    }

    @Test
    public void retrieveProjectNames_returnsProjectNames() throws FileNotFoundException {
        subject = new JSONParser("dt.json");
        List<String> actual = subject.retrieveProjectNames();

        assertEquals(Arrays.asList("Paint Projects", "Home Remodel Projects"), actual);
    }

    @Test
    public void retrieveProject_forProjectName_returnsProjectNode() throws FileNotFoundException {
        JSONParser parser = new JSONParser("dt-small.json");
        Node expected = parser.parse();

        subject = new JSONParser("dt.json");
        Node actual = subject.retrieveProject("Paint Projects");

        assertEquals(expected, actual);
    }
}