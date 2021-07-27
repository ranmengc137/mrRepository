drop table if exists transaction_info;

create table transaction_info (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `xid` varchar(32) ,
    `status` int not null ,
    `class_name` varchar(64) not null ,
    `commit_method_name` varchar(32) not null ,
    `cancel_method_name` varchar(32) not null,
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;;