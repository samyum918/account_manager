create table if not exists bank_account (
    id int(11) NOT NULL AUTO_INCREMENT,
    account_number varchar(255) NOT NULL,
    account_balance decimal(18,2) NOT NULL,
    PRIMARY KEY(id)
);

create table if not exists transfer_history (
    id int(11) NOT NULL AUTO_INCREMENT,
    from_account_number varchar(255) NOT NULL,
    to_account_number varchar(255) NOT NULL,
    amount decimal(18,2) NOT NULL,
    create_time datetime NULL,
    PRIMARY KEY(id)
);

insert into bank_account values(1, 12345678, 1000000);
insert into bank_account values(2, 88888888, 1000000);