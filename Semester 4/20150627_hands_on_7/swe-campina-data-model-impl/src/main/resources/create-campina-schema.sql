CREATE TABLE campina.user (
	id INTEGER NOT NULL AUTO_INCREMENT,
	version INTEGER NOT NULL default 1,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE campina.order(
	id INTEGER NOT NULL auto_increment,
	version INTEGER NOT NULL default 1,
	order_date TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
	collect_date TIMESTAMP NOT NULL,
	user_id INTEGER NOT NULL,
	menu_entry_id INTEGER NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE campina.menu(
	id INTEGER NOT NULL auto_increment,
	version INTEGER NOT NULL default 1,
	day INTEGER NOT NULL,
	label VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE campina.menu_entry(
	id INTEGER NOT NULL auto_increment,
	version INTEGER NOT NULL default 1,
	ordinal INTEGER NOT NULL,
	label VARCHAR(255) NOT NULL,
	price DECIMAL(4,2) NOT NULL,
	menu_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);

commit;

ALTER TABLE campina.order 
ADD CONSTRAINT fk_menu_entry_id
FOREIGN KEY (menu_entry_id)
REFERENCES campina.menu_entry(id);

ALTER TABLE campina.order 
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES campina.user(id);

ALTER TABLE campina.menu_entry 
ADD CONSTRAINT fk_menu_id
FOREIGN KEY (menu_id)
REFERENCES campina.menu(id);

commit;