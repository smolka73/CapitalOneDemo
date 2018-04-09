#!/bin/bash

TEST=`gosu postgres postgres --single <<- EOSQL
   SELECT 1 FROM pg_database WHERE datname='$DB_NAME';
EOSQL`

echo "******CREATING DOCKER DATABASE******"
if [[ $TEST == "1" ]]; then
    # database exists
    # $? is 0
    exit 0
else
gosu postgres postgres --single -jE <<- EOSQL
   CREATE ROLE $DB_USER WITH LOGIN ENCRYPTED PASSWORD '${DB_PASS}' CREATEDB;
EOSQL

gosu postgres postgres --single -jE <<- EOSQL
   CREATE DATABASE $DB_NAME WITH OWNER $DB_USER TEMPLATE template0 ENCODING 'UTF8';
EOSQL
echo "******DATABASE DONE******"

gosu postgres postgres --single -jE $DB_NAME <<- EOSQL
   GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;
   CREATE SCHEMA $SCHEMA AUTHORIZATION $DB_USER;
   CREATE TABLE $SCHEMA.bank_account
(
  id serial NOT NULL,
  full_name character varying(128) NOT NULL,
  balance integer NOT NULL,
  created_at timestamp without time zone NOT NULL,
  last_modified_by character varying(128) NOT NULL,
  last_modified_at timestamp without time zone NOT NULL,
  CONSTRAINT bank_account_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);

ALTER TABLE $SCHEMA.bank_account OWNER TO $DB_USER;

EOSQL
echo "******GRANT DONE******"




echo "******TABLE DONE******"

fi

echo ""
echo "******DOCKER DATABASE CREATED******"
