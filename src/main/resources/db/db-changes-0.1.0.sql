-- 0.1.0
CREATE USER alle;
alter ROLE alle PASSWORD 'haslo';
create DATABASE alledb owner alle ENCODING = 'UTF-8';

--create table