/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  martin
 * Created: 19-11-2016
 */

/*
drop database if exists dbConsultas;

create database dbConsultas;
use dbConsultas;

create table userType(
    id tinyint not null auto_increment primary key,
    name varchar(30) not null
);

create table typeQuery(
    id tinyint not null auto_increment primary key,
    name varchar(30) not null
);

create table user(
    id int not null auto_increment primary key,
    nick varchar(30),
    password tinyblob
);

create table alert(
    id int not null auto_increment primary key,
    ready bit,
    idUser int not null,
    date datetime not null,
    foreign key(idUser) references user(id)
);

create table message(
    id int not null auto_increment primary key,
    idUser int not null,
    date datetime not null,
    text varchar(255),
    foreign key(idUser) references user(id)
);

*/

drop database if exists dbConsultas;
create database dbConsultas;
use dbConsultas;

delimiter //
create function getAesKey() returns varchar(20)
begin return "powerx200"; end//
delimiter ;

create table userType(
    id tinyint not null auto_increment primary key,
    name varchar(30) not null
);

insert into userType values(null, 'Administrador');
insert into userType values(null, 'Alumno');

create table user(
    id int not null auto_increment primary key,
    nick varchar(30),
    password tinyblob,
    idType tinyint not null,
    foreign key(idType) references userType(id)
);

create table alert(
    id int not null auto_increment primary key,
    idUser int not null,
    dateTime datetime,
    foreign key(idUser) references user(id)
);

create table message(
    id int not null auto_increment primary key,
    idUser int not null,
    text varchar(255) not null,
    dateTime datetime,
    foreign key(idUser) references user(id)
);

