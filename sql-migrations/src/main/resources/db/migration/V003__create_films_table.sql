Create Table films (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  genre varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  picture_url varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table film_user_xref(
  id int(11) NOT NULL AUTO_INCREMENT,
  film_id int(11) NOT NULL,
  user_id int(11) NOT NULL,
  PRIMARY KEY (id),
  foreign key (film_id) references films(id),
  foreign key (user_id) references users(id)
)