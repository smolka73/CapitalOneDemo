package com.capitalone.demo.repository;


import org.junit.Before;
import org.junit.Test;

import com.capitalone.demo.entity.Account;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

public class AccountEntityTest {


    Account accountEntity;
    Account initalizedEntity;

    
    @Before
    public void setUp() {
        accountEntity = new Account();
        initalizedEntity = new Account("test", 5);
    }


    @Test
    public void initialBalanceShouldBeZero() {
        assertThat(accountEntity.getBalance(), is(0));
    }
        
    
    @Test
    public void shouldDeposit() {
        accountEntity.deposit(10);
        assertThat(accountEntity.getBalance(), is(10));
    }


    @Test
    public void shouldWithdraw() {
        accountEntity.withdraw(10);
        assertThat(accountEntity.getBalance(), is(-10));
    }
    
    @Test
    public void hasDatesAssigned() {
    	assertTrue(accountEntity.getCreatedDate().getTime()- new Date().getTime() < 60L * 1000);
    	assertTrue(accountEntity.getLastModifiedDate().getTime()- new Date().getTime() < 60L * 1000);
    }
    
    @Test
    public void checkModifier() {
    	assertThat(initalizedEntity.getFullName(), is("test"));
    	assertThat(initalizedEntity.getFullName(), is(initalizedEntity.getLastModifiedBy()));
    }

}
