create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      integer,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Milk', 80),
       ('Bread', 25),
       ('Cheese', 300);