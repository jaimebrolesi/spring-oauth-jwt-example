create table oauth_client_details
(
  client_id               varchar(256) not null constraint oauth_client_details_pkey
    primary key,
  resource_ids            varchar(256),
  client_secret           varchar(256),
  scope                   varchar(256),
  authorized_grant_types  varchar(256),
  web_server_redirect_uri varchar(256),
  authorities             varchar(256),
  access_token_validity   integer,
  refresh_token_validity  integer,
  additional_information  varchar(4096),
  autoapprove             varchar(256)
);

create table users
(
  id       bigint not null primary key,
  age      integer,
  password varchar(255),
  salary   bigint,
  username varchar(255)
);

