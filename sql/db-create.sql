SET GLOBAL time_zone = '+2:00'; 
USE final;


DROP TABLE IF exists `request_from_manager`;
DROP TABLE IF EXISTS `own_request`;
DROP TABLE IF EXISTS `request`;
DROP TABLE IF EXISTS `rooms` ;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `mail` varchar(30) NOT NULL,
  `role` tinyint NOT NULL,
   PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB;


CREATE TABLE `rooms` (
  `number` int NOT NULL AUTO_INCREMENT,
  `status` int NOT NULL,
  `number_of_places` int NOT NULL,
  `class_apartment` varchar(20) NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `room_number` int DEFAULT NULL,
  `time_start` timestamp DEFAULT NULL,
  `time_end` timestamp DEFAULT NULL,
  `sucsess` boolean ,
  `operation` boolean NOT NULL ,
  `payed_status` boolean NOT NULL,
  PRIMARY KEY (`id`,`id_user`),
  UNIQUE KEY `room_number` (`room_number`),
  KEY `request_users_id_fk` (`id_user`),
  CONSTRAINT `request_rooms_number_fk` FOREIGN KEY (`room_number`) REFERENCES `rooms` (`number`),
  CONSTRAINT `request_users_id_fk` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `own_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `number_of_places` int NOT NULL,
  `class_apartment` varchar(10) not null,
  `time_start` timestamp DEFAULT NULL,
  `time_end` timestamp DEFAULT NULL,
   `operation` boolean NOT NULL ,
  `payed_status` boolean NOT NULL,
  PRIMARY KEY (`id`,`id_user`),
  KEY `own_request_users_id_fk` (`id_user`),
  CONSTRAINT `own_request_users_id_fk` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table `request_from_manager`(
   `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `room_number` int DEFAULT NULL,
  `time_start`  timestamp DEFAULT NULL,
  `time_end` timestamp DEFAULT NULL,
  `wait` boolean NOT NULL,
  `agree` boolean NOT NULL,
  PRIMARY KEY (`id`,`id_user`),
  UNIQUE KEY `room_number` (`room_number`),
  KEY `request_from_manager_id_fk` (`id_user`),
  CONSTRAINT `request_rooms_fk` FOREIGN KEY (`room_number`) REFERENCES `rooms` (`number`),
  CONSTRAINT `request_users_fk` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE

)ENGINE=InnoDB DEFAULT CHARSET=latin1;




insert into users ( login , pass, first_name , last_name , mail ,role) values( 'admin', 123 , 'Admin' ,'Admin' ,"admin@gmail.com",	 0);
insert into users ( login , pass, first_name , last_name ,mail  , role) values( 'baseev', 123 , 'bogdan' ,'aseev',"aseevbogdan6@gmail.com", 1);
insert into users ( login , pass, first_name , last_name ,mail  , role) values( 'client', 123 , 'Client' ,'Client',"aseevbogdan6@gmail.com", 1);
  

insert into rooms ( status , number_of_places, class_apartment , price) values ( 0, 2 , "Standart", 1000);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 0 ,2 , "Standart", 1000);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 0,4 , "Luxure", 3500);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 0,2 , "Standart", 1000);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 0,3 , "Medium", 2500);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 0,2 , "Standart", 1000);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 2,4 , "Luxure", 3500);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 3 ,2 , "Standart", 1000);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 3,3 , "Medium", 2500);
insert into rooms ( status , number_of_places, class_apartment , price) values ( 4,1 , "Standart", 1000);