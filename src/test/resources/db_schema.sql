
TRUNCATE bank_account RESTART IDENTITY;

  
INSERT INTO bank_account (full_name, balance, created_at, last_modified_at, last_modified_by) VALUES ('first todo', 100, to_date('1993-09-01', 'YYYY-MM-DD'),to_date('1993-09-01', 'YYYY-MM-DD'),'first todo');
INSERT INTO bank_account (full_name, balance, created_at, last_modified_at, last_modified_by) VALUES ('second todo', 200, to_date('1993-09-01', 'YYYY-MM-DD'),to_date('1993-09-01', 'YYYY-MM-DD'),'second todo');
INSERT INTO bank_account (full_name, balance, created_at, last_modified_at, last_modified_by) VALUES ('third todo', 200, to_date('1963-09-01', 'YYYY-MM-DD'),to_date('1993-09-01', 'YYYY-MM-DD'), 'third todo');