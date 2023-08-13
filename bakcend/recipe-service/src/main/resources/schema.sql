
DROP TABLE IF EXISTS recipe;

CREATE TABLE recipe(
    id uuid default uuid() not null primary key ,
    title varchar(32) not null,
    body varchar(512) not null,
    created_by varchar(64) not null,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP not null
);