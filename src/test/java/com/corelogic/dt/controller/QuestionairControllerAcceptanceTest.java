package com.corelogic.dt.controller;

import com.corelogic.dt.DTServiceApplication;
import com.corelogic.dt.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {DTServiceApplication.class})
public class QuestionairControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void retrieveProjectNames_returnProjectNames() throws Exception {
        mockMvc.perform(get("/api/questionaire/project/names")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-names.json")));
    }

    @Test
    public void retrieveProject_returnProject() throws Exception {
        mockMvc.perform(get("/api/questionaire/project/Paint Projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-small.json")));
    }

    @Test
    public void retrieveProject_withInvalidProjectName_throwsProjectNotFoundException() throws Exception {
        mockMvc.perform(get("/api/questionaire/project/Landscape Projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-notfound.json")));
    }
}