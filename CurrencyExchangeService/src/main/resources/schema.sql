create table EXCHANGE_RATE_MASTER
(
   id int auto_increment primary key,
   FROM_CURRENCY varchar(255) not null, 
   TO_CURRENCY   varchar(255) not null,
   EXCHANGE_RATE NUMBER(16,3) not null
);

create table AUDITLOG
(
   id int auto_increment primary key,
   TABLENAME varchar(255) not null, 
   OPERATION   varchar(255) not null,
   DATA   varchar(1000)
);
