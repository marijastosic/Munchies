create TABLE if not exists authorities(
id int auto_increment primary key,
authority varchar(50) not NULL
);

create TABLE if not exists user(
id int auto_increment primary key,
email varchar(50) not NULL,
password varchar(50) not NULL,
authorities_id int,
foreign key(authorities_id) references authorities(id)
);

create table if not exists restaurant(
id binary(16) primary key not null,
email varchar(50) not null,
name varchar(50) not null,
address varchar(1000) not null,
phone_number varchar(1000) not null,
menu_url varchar(1000) not null,
delivery_time varchar(1000),
additional_charges varchar(1000)
);

create table if not exists group_order(
id binary(16) primary key not null,
restaurant_id binary(16),
url varchar(1000) not null,
timeout varchar(1000) not null,
creator varchar(1000) not null,
foreign key(restaurant_id) references restaurant(id)
);

create table if not exists orders(
id int auto_increment primary key,
employee_name varchar(1000) not null,
item_name varchar(1000) not null,
price double not null,
group_order_id binary(16),
foreign key(group_order_id) references group_order(id)
);