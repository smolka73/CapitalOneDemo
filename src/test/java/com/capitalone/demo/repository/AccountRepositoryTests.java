package com.capitalone.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capitalone.demo.config.PostgreRepositoryConfig;
import com.capitalone.demo.entity.Account;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PostgreRepositoryConfig.class)
@ActiveProfiles("postgresql")
@Transactional
public class AccountRepositoryTests {

  @Autowired
  private EntityManager entityManager;

  // @PersistenceContext
  // EntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  private Account firstAccount;
  private Account secondAccount;
  private int found = 0;

  @Before
  public void before() {
    found = this.accountRepository.findAll().size();
    this.firstAccount = new Account("First Test Account", 1000);
    this.entityManager.persist(this.firstAccount);
    this.secondAccount = new Account("Second Test Account", 3000);
    this.entityManager.persist(this.secondAccount);
  }

  @Test
  public void findAllTodos() {
    final List<Account> todos = this.accountRepository.findAll();

    assertThat(todos.size()).isEqualTo(found + 2);
  }

  @Test
  public void findOneTodo() {
    assertThat(this.accountRepository.findOneByFullName(this.firstAccount.getFullName()).getFullName()).isEqualTo(this.firstAccount.getFullName());
  }

  @Test
  public void addNewTodo() {
    final Account newAccount = new Account("My New Account", 400);
    this.accountRepository.save(newAccount);
    final Account newTodoFromDb = this.accountRepository.findOneByFullName(newAccount.getFullName());
    assertThat(newAccount.getFullName()).isEqualTo(newTodoFromDb.getFullName());
  }

  @Test
  public void updateExistingTodo() {
    this.firstAccount.setFullName("Updated First Account");
    this.accountRepository.save(this.firstAccount);
    assertThat(this.accountRepository.findOneByFullName("Updated First Account")).isNotNull();
  }

  @Test
  public void deleteTodo() {
    this.accountRepository.delete(this.firstAccount);
    assertThat(this.accountRepository.findOneByFullName(this.firstAccount.getFullName())).isNull();
  }
}