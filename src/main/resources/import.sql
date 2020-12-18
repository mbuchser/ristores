--INSERT INTO menu(id, firstname, lastname) VALUES (nextval('hibernate_sequence'), 'John', 'Irving');
INSERT INTO menu(id, menuTitle, menuContent, menuDate) VALUES (nextval('hibernate_sequence'), 'Menu 1', 'Entrecôte gratinée au beurre aux herbes, frites, bouquet de légumes', '15.12.2020');
INSERT INTO menu(id, menuTitle, menuContent, menuDate) VALUES (nextval('hibernate_sequence'), 'Menu 2', 'Tortelloni aux tomates et à la mozzarella, sauce tomate épicée', '15.12.2020');
INSERT INTO menu(id, menuTitle, menuContent, menuDate) VALUES (nextval('hibernate_sequence'), 'Menu 1', 'Escalope de porc panée (CH), Frites, Pois et carottes', '16.12.2020');
INSERT INTO menu(id, menuTitle, menuContent, menuDate) VALUES (nextval('hibernate_sequence'), 'Menu 2', 'Risotto aux champignons avec des noisettes grillées', '16.12.2020');

INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Peter Meier', 1);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Barbara Forster', 2);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Michael Müller', 3);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Emily Moresmo', 3);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Daniel Donato', 4);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Maria Santiago', 4);
INSERT INTO guest(id, guestName, menu_id) VALUES (nextval('hibernate_sequence'), 'Sandra Domenica', 1);
