use scheduler;

drop table if exists scheduler,member;

create table member
(
    id         bigint       not null auto_increment primary key,
    name       varchar(50)  not null,
    email      varchar(100) not null,
    createdAt  datetime     not null default current_timestamp,
    modifiedAt datetime     not null default current_timestamp on update current_timestamp
);


create table scheduler
(
    id         bigint       not null auto_increment primary key,
    task       varchar(255) not null,
    password   varchar(60)  not null,
    createdAt  datetime     not null default current_timestamp,
    modifiedAt datetime     not null default current_timestamp on update current_timestamp,
    member_id  bigint       not null,
    foreign key (member_id) references member (id) on DELETE cascade
);