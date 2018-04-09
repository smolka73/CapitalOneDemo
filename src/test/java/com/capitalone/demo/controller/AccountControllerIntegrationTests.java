package com.capitalone.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capitalone.demo.DemoApplication;
import com.capitalone.demo.config.PostgreRepositoryConfig;
import com.capitalone.demo.entity.Account;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PostgreRepositoryConfig.class)

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase
@ActiveProfiles("postgresql")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SqlGroup({ @Sql(scripts = "classpath:db_schema.sql") })

public class AccountControllerIntegrationTests {
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private ObjectMapper mapper;

  @LocalServerPort
  private int port;

  private String URL;

  @Before
  public void before() {
    this.URL = "http://localhost:" + this.port;

  }

  @Test
  public void _findAllAccounts() throws JsonParseException, JsonMappingException, IOException {
    final String response = this.restTemplate.getForObject(this.URL + "/accounts", String.class);
    final List<Account> accounts = Arrays.asList(this.mapper.readValue(response, Account[].class));

    assertThat(accounts.size()).isEqualTo(3);
  }

  @Test
  public void findOne() throws JsonParseException, JsonMappingException, IOException {
    final String response = this.restTemplate.getForObject(this.URL + "/accounts/1", String.class);
    final Account account = this.mapper.readValue(response, Account.class);

    assertThat(account.getAccountNumber()).isEqualTo(1);
    assertThat(account.getFullName()).isEqualToIgnoringCase("first todo");
    assertThat(account.getCreatedDate()).isBefore(new Date());
    assertThat(account.getLastModifiedBy()).isEqualToIgnoringCase(account.getFullName());
  }

  @Test
  public void addAccount() {
    final Account newAccount = new Account("This is a new todo", 500);

    final ResponseEntity<Account> todo = this.restTemplate.postForEntity(this.URL + "/accounts", newAccount, Account.class);
    assertThat(todo.getHeaders().getLocation().toString()).isEqualTo(this.URL + "/accounts/4");
  }

  @Test
  public void updateAccountName() throws JsonProcessingException {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    final HttpEntity<String> entity = new HttpEntity<String>("{\"name\":\"This is an updated account\"}", headers);
    final Map<String, String> urlVariables = new HashMap<String, String>();
    urlVariables.put("accountNumber", "1");
    final ResponseEntity<Account> responseEntity = this.restTemplate.exchange(this.URL + "/accounts/{accountNumber}/name", HttpMethod.PUT, entity, Account.class, urlVariables);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getAccountNumber()).isEqualTo(1L);
    assertThat(responseEntity.getBody().getFullName()).isEqualTo("This is an updated account");
  }

  @Test
  public void deleteAccount() {
    this.restTemplate.delete(this.URL + "/accounts/2");
    final ResponseEntity<Account> responseEntity = this.restTemplate.getForEntity(this.URL + "/accounts/2", Account.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

  }
}
