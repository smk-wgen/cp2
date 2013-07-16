#create database cp2;
use cp2;
drop table if exists user_commute;
drop table if exists user_address;
drop table if exists user;

# --- First database schema

# --- !Ups

create table user (
  id                        MEDIUMINT NOT NULL  primary key AUTO_INCREMENT,
  email                     varchar(255) not null,
  name                      varchar(255) not null,
  city                      varchar(255) not null,
  employer                  varchar(255) not null,
  imageUrl                  varchar(255) not null,
  gender                    varchar(6) not null,
  linkedInId                varchar(255) not null,
  cell                      varchar(55) not null
);

create table user_address (
  id                        mediumint not null primary key auto_increment,
  label                     varchar(100) not null,
  line1                     varchar(255) not null,
  line2                     varchar(255),
  city                      varchar(255) not null,
  state                     varchar(15) not null,
  zip                       int not null,
  user_id                   mediumint not null,
  foreign key(user_id)      references user(id)
);


create table user_commute (
  id                        mediumint not null primary key auto_increment,
  window_start                time not null,
  window_end                time not null,
  from_address              mediumint not null,
  to_address                mediumint not null,
  user_id                   mediumint not null,
  foreign key(from_address)   references user_address(id),
  foreign key(to_address)   references user_address(id),
  foreign key(user_id)      references user(id)
);


