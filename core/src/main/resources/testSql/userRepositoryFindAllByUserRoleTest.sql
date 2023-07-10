insert into users(uuid, username, email, password, confirm_token, confirm_token_expired_at, confirmed, user_role)
values('1b575ebf-ec2d-4b28-9432-0cc4434894a5', 'some user', 'someEmail@gmail.com', 'some password', '577f9036-6bed-47be-bd54-9b2a627a4c3a', NOW(), false, 'MANAGEMENT' );

insert into users(uuid, username, email, password, confirm_token, confirm_token_expired_at, confirmed, user_role)
values('1b575ebf-ec2d-4b28-9432-0cc4434894a6', 'some user1', 'someEmail1@gmail.com', 'some password1', '577f9036-6bed-47be-bd54-9b2a627a4c31', NOW(), false, 'ADMIN' );