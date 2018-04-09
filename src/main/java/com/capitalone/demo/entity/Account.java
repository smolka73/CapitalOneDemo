package com.capitalone.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*- DROP TABLE bank_account;
DROP TABLE bank_account;

CREATE TABLE bank_account
(
  id SERIAL PRIMARY KEY,
  full_name  VARCHAR(128) NOT NULL,
  balance integer NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_modified_by VARCHAR(128) NOT NULL,
    last_modified_at TIMESTAMP NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE bank_account
  OWNER TO vista;


-*/

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bank_account")
public class Account {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer accountNumber;

  @Column(name = "balance")
  @Min(message = "Balance must be >= 0", value = 0)
  private int balance;

  @Column(name = "full_name", nullable = false, length = 128)
  private String fullName;

  @Column(name = "created_at", nullable = false)
  private Date createdDate = new Date();

  @Column(name = "last_modified_by", nullable = false, length = 128)
  @LastModifiedBy
  private String lastModifiedBy;

  @Column(name = "last_modified_at", nullable = false)
  @LastModifiedDate
  private Date lastModifiedDate = new Date();

  public Account() {

  }

  public Account(final String fullName, int balance) {
    this.fullName = this.lastModifiedBy = fullName;
    this.balance = balance;

  }

  public Account(Account account) {
    this.accountNumber = account.accountNumber;
    this.fullName = this.lastModifiedBy = account.fullName;
    this.balance = account.balance;
    this.createdDate = account.createdDate;
    this.lastModifiedDate = account.lastModifiedDate;

  }

  public Integer getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Integer accountNumber) {
    this.accountNumber = accountNumber;
  }

  public int getBalance() {
    return balance;
  }

  public Date getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  public void setLastModifiedDate(final Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public void setLastModifiedBy(final Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return this.lastModifiedBy;
  }

  public void setLastModifiedBy(final String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Date getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(final Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(final String fullName) {
    this.fullName = this.lastModifiedBy = fullName;
  }

  public void deposit(int amount) {
    balance += amount;
  }

  public void withdraw(int amount) {
    balance -= amount;
  }

  /*-
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Account)) return false;
  
      Account that = (Account) o;
  
      if (balance != that.balance) return false;
      if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
          return false;
      return  fullName==null ? that.fullName==null : fullName.equals(that.fullName);
          }
  
  @Override
  public int hashCode() {
      int result = accountNumber != null ? accountNumber.hashCode() : 0;
      result = 31 * result + balance;
      result = fullName == null ? result : 31 * result + fullName.hashCode();
      return result;
  }
  
  @Override
  public String toString() {
      return "AccountEntity{" +
              "accountNumber=" + accountNumber +
              ", balance=" + balance +
              '}';
  }-*/
}