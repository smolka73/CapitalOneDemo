package com.capitalone.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  @SuppressWarnings("deprecation")
  public void index() throws Exception {
    this.mockMvc.perform(get("/swagger")).andExpect(status().isMovedTemporarily()).andExpect(redirectedUrl("swagger-ui.html"));
  }

  @SuppressWarnings("deprecation")
  @Test
  public void redirectToSwaggerUI() throws Exception {
    this.mockMvc.perform(get("/swagger")).andExpect(status().isMovedTemporarily()).andExpect(redirectedUrl("swagger-ui.html"));
  }
}
