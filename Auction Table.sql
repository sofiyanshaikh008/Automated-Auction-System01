mysql> create database auction;
Query OK, 1 row affected (0.03 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| auction            |
| db                 |
| db2                |
| information_schema |
| mysql              |
| org                |
| performance_schema |
| project            |
| sprint3            |
| sprint4            |
| sys                |
+--------------------+
11 rows in set (0.00 sec)

mysql> use auction
Database changed

mysql> use auction
Database changed
mysql> create table Admin
    -> (
    -> adminId int unique not null,
    -> adminName varchar(25) not null,
    -> AdminUsername varchar(25) unique not null,
    -> AdminPassword varchar(25) not null
    -> );
Query OK, 0 rows affected (0.03 sec)

mysql> create table Buyer
    -> (
    -> buyerId int primary key auto_increment,
    -> buyerName varchar(25) not null,
    -> buyerUsername varchar(25) unique not null,
    -> buyerPassword varchar(25) not null
    -> );
Query OK, 0 rows affected (0.03 sec)

mysql> create table Seller
    -> (
    -> sellerId int primary key auto_increment,
    -> sellerName varchar(25) not null,
    -> sellerUsername varchar(25) unique not null,
    -> sellerPassword varchar(25) not null
    -> );
Query OK, 0 rows affected (0.01 sec)
status varchar not null,' at line 6
mysql> create table product
    -> (
    -> ProId int primary key auto_increment,
    -> proName varchar(25) not null,
    -> sellerId int not null,
    -> price int not null,
    -> quantity int not null,
    -> status boolean not null,
    -> category varchar(25) not null,
    -> foreign key(sellerId) references Seller(sellerId)
    -> );
Query OK, 0 rows affected (0.06 sec)
mysql> create table SoldProduct
    -> (
    -> spId int primary key auto_increment,
    -> buyerId int not null,
    -> sellerId int not null,
    -> proName varchar(25) not null,
    -> price int not null,
    -> quantity int not null,
    -> category varchar(25) not null,
    -> date date not null,
    -> foreign key(buyerId) references Buyer(buyerId),
    -> foreign key(sellerId) references Seller(sellerId)
    -> );
Query OK, 0 rows affected (0.03 sec)
