create table facility
(
    id             varchar(255) not null,
    name           varchar(255),
    parking_lot_id varchar(255),
    primary key (id)
);
create table parked_car
(
    id                      varchar(255) not null,
    car_plate_no            varchar(255),
    parking_lot_id          varchar(255),
    visitor_parking_card_id varchar(255),
    primary key (id)
);
create table parking_lot
(
    id       varchar(255) not null,
    capacity integer,
    primary key (id)
);
create table resident
(
    id          varchar(255) not null,
    facility_id varchar(255),
    user_id     varchar(255),
    primary key (id)
);
create table resident_visitor_parking_cards
(
    resident_entity_id       varchar(255) not null,
    visitor_parking_cards_id varchar(255) not null
);
create table user_entity_roles
(
    user_entity_id varchar(255) not null,
    roles          varchar(255)
);
create table users
(
    id       varchar(255) not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table visitor_parking_card
(
    id                  varchar(255) not null,
    assigned_since      timestamp,
    is_available        boolean      not null,
    parked_car_plate_no varchar(255),
    parking_card_nr     varchar(255),
    parking_lot_id      varchar(255),
    primary key (id)
);
alter table facility
    add constraint UKllc37k5i68mi0cgsq4s5r22oa unique (name);
alter table parked_car
    add constraint UKjrtkxfbpv343dq2cmsjgj5mon unique (car_plate_no);
alter table resident
    add constraint UK10g38xyvtm0mpln486nvhbqfp unique (user_id);
alter table resident_visitor_parking_cards
    add constraint UK_htj73d5iefqob9tmp1hafvlqs unique (visitor_parking_cards_id);
alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table visitor_parking_card
    add constraint UKn8g6bpiygn3n2dde9xochxm8y unique (parking_card_nr);
alter table facility
    add constraint FKkhda6bb0tgi5rasc7q16isddc foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKlps583k9p4c3okprvgc7i8bm7 foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKmrlf7hocisdb9rpdxjcokn2av foreign key (visitor_parking_card_id) references visitor_parking_card;
alter table resident
    add constraint FK1nbu8wstd1ws3h0ea1anwi5c3 foreign key (facility_id) references facility;
alter table resident
    add constraint FKgci4ia179xf375mvi0brc5y5a foreign key (user_id) references users;
alter table resident_visitor_parking_cards
    add constraint FKi0ft5lt7k5x2e7eim2ffpr6om foreign key (visitor_parking_cards_id) references visitor_parking_card;
alter table resident_visitor_parking_cards
    add constraint FKafhkqba6hdg1x1udg98rbqo6a foreign key (resident_entity_id) references resident;
alter table user_entity_roles
    add constraint FK80w28k99mayei90r6mycds2em foreign key (user_entity_id) references users;
create table facility
(
    id             varchar(255) not null,
    name           varchar(255),
    parking_lot_id varchar(255),
    primary key (id)
);
create table parked_car
(
    id                      varchar(255) not null,
    car_plate_no            varchar(255),
    parking_lot_id          varchar(255),
    visitor_parking_card_id varchar(255),
    primary key (id)
);
create table parking_lot
(
    id       varchar(255) not null,
    capacity integer,
    primary key (id)
);
create table resident
(
    id          varchar(255) not null,
    facility_id varchar(255),
    user_id     varchar(255),
    primary key (id)
);
create table resident_visitor_parking_cards
(
    resident_entity_id       varchar(255) not null,
    visitor_parking_cards_id varchar(255) not null
);
create table user_entity_roles
(
    user_entity_id varchar(255) not null,
    roles          varchar(255)
);
create table users
(
    id       varchar(255) not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table visitor_parking_card
(
    id                  varchar(255) not null,
    assigned_since      timestamp,
    is_available        boolean      not null,
    parked_car_plate_no varchar(255),
    parking_card_nr     varchar(255),
    parking_lot_id      varchar(255),
    primary key (id)
);
alter table facility
    add constraint UKllc37k5i68mi0cgsq4s5r22oa unique (name);
alter table parked_car
    add constraint UKjrtkxfbpv343dq2cmsjgj5mon unique (car_plate_no);
alter table resident
    add constraint UK10g38xyvtm0mpln486nvhbqfp unique (user_id);
alter table resident_visitor_parking_cards
    add constraint UK_htj73d5iefqob9tmp1hafvlqs unique (visitor_parking_cards_id);
alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table visitor_parking_card
    add constraint UKn8g6bpiygn3n2dde9xochxm8y unique (parking_card_nr);
alter table facility
    add constraint FKkhda6bb0tgi5rasc7q16isddc foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKlps583k9p4c3okprvgc7i8bm7 foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKmrlf7hocisdb9rpdxjcokn2av foreign key (visitor_parking_card_id) references visitor_parking_card;
alter table resident
    add constraint FK1nbu8wstd1ws3h0ea1anwi5c3 foreign key (facility_id) references facility;
alter table resident
    add constraint FKgci4ia179xf375mvi0brc5y5a foreign key (user_id) references users;
alter table resident_visitor_parking_cards
    add constraint FKi0ft5lt7k5x2e7eim2ffpr6om foreign key (visitor_parking_cards_id) references visitor_parking_card;
alter table resident_visitor_parking_cards
    add constraint FKafhkqba6hdg1x1udg98rbqo6a foreign key (resident_entity_id) references resident;
alter table user_entity_roles
    add constraint FK80w28k99mayei90r6mycds2em foreign key (user_entity_id) references users;
create table facility
(
    id             varchar(255) not null,
    name           varchar(255),
    parking_lot_id varchar(255),
    primary key (id)
);
create table parked_car
(
    id                      varchar(255) not null,
    car_plate_no            varchar(255),
    parking_lot_id          varchar(255),
    visitor_parking_card_id varchar(255),
    primary key (id)
);
create table parking_lot
(
    id       varchar(255) not null,
    capacity integer,
    primary key (id)
);
create table resident
(
    id          varchar(255) not null,
    facility_id varchar(255),
    user_id     varchar(255),
    primary key (id)
);
create table resident_visitor_parking_cards
(
    resident_entity_id       varchar(255) not null,
    visitor_parking_cards_id varchar(255) not null
);
create table user_entity_roles
(
    user_entity_id varchar(255) not null,
    roles          varchar(255)
);
create table users
(
    id       varchar(255) not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table visitor_parking_card
(
    id                  varchar(255) not null,
    assigned_since      timestamp,
    is_available        boolean      not null,
    parked_car_plate_no varchar(255),
    parking_card_nr     varchar(255),
    parking_lot_id      varchar(255),
    primary key (id)
);
alter table facility
    add constraint UKllc37k5i68mi0cgsq4s5r22oa unique (name);
alter table parked_car
    add constraint UKjrtkxfbpv343dq2cmsjgj5mon unique (car_plate_no);
alter table resident
    add constraint UK10g38xyvtm0mpln486nvhbqfp unique (user_id);
alter table resident_visitor_parking_cards
    add constraint UK_htj73d5iefqob9tmp1hafvlqs unique (visitor_parking_cards_id);
alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table visitor_parking_card
    add constraint UKn8g6bpiygn3n2dde9xochxm8y unique (parking_card_nr);
alter table facility
    add constraint FKkhda6bb0tgi5rasc7q16isddc foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKlps583k9p4c3okprvgc7i8bm7 foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKmrlf7hocisdb9rpdxjcokn2av foreign key (visitor_parking_card_id) references visitor_parking_card;
alter table resident
    add constraint FK1nbu8wstd1ws3h0ea1anwi5c3 foreign key (facility_id) references facility;
alter table resident
    add constraint FKgci4ia179xf375mvi0brc5y5a foreign key (user_id) references users;
alter table resident_visitor_parking_cards
    add constraint FKi0ft5lt7k5x2e7eim2ffpr6om foreign key (visitor_parking_cards_id) references visitor_parking_card;
alter table resident_visitor_parking_cards
    add constraint FKafhkqba6hdg1x1udg98rbqo6a foreign key (resident_entity_id) references resident;
alter table user_entity_roles
    add constraint FK80w28k99mayei90r6mycds2em foreign key (user_entity_id) references users;
create table facility
(
    id             varchar(255) not null,
    name           varchar(255),
    parking_lot_id varchar(255),
    primary key (id)
);
create table parked_car
(
    id                      varchar(255) not null,
    car_plate_no            varchar(255),
    parking_lot_id          varchar(255),
    visitor_parking_card_id varchar(255),
    primary key (id)
);
create table parking_lot
(
    id       varchar(255) not null,
    capacity integer,
    primary key (id)
);
create table resident
(
    id          varchar(255) not null,
    facility_id varchar(255),
    user_id     varchar(255),
    primary key (id)
);
create table resident_visitor_parking_cards
(
    resident_entity_id       varchar(255) not null,
    visitor_parking_cards_id varchar(255) not null
);
create table user_entity_roles
(
    user_entity_id varchar(255) not null,
    roles          varchar(255)
);
create table users
(
    id       varchar(255) not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table visitor_parking_card
(
    id                  varchar(255) not null,
    assigned_since      timestamp,
    is_available        boolean      not null,
    parked_car_plate_no varchar(255),
    parking_card_nr     varchar(255),
    parking_lot_id      varchar(255),
    primary key (id)
);
alter table facility
    add constraint UKllc37k5i68mi0cgsq4s5r22oa unique (name);
alter table parked_car
    add constraint UKjrtkxfbpv343dq2cmsjgj5mon unique (car_plate_no);
alter table resident
    add constraint UK10g38xyvtm0mpln486nvhbqfp unique (user_id);
alter table resident_visitor_parking_cards
    add constraint UK_htj73d5iefqob9tmp1hafvlqs unique (visitor_parking_cards_id);
alter table users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table visitor_parking_card
    add constraint UKn8g6bpiygn3n2dde9xochxm8y unique (parking_card_nr);
alter table facility
    add constraint FKkhda6bb0tgi5rasc7q16isddc foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKlps583k9p4c3okprvgc7i8bm7 foreign key (parking_lot_id) references parking_lot;
alter table parked_car
    add constraint FKmrlf7hocisdb9rpdxjcokn2av foreign key (visitor_parking_card_id) references visitor_parking_card;
alter table resident
    add constraint FK1nbu8wstd1ws3h0ea1anwi5c3 foreign key (facility_id) references facility;
alter table resident
    add constraint FKgci4ia179xf375mvi0brc5y5a foreign key (user_id) references users;
alter table resident_visitor_parking_cards
    add constraint FKi0ft5lt7k5x2e7eim2ffpr6om foreign key (visitor_parking_cards_id) references visitor_parking_card;
alter table resident_visitor_parking_cards
    add constraint FKafhkqba6hdg1x1udg98rbqo6a foreign key (resident_entity_id) references resident;
alter table user_entity_roles
    add constraint FK80w28k99mayei90r6mycds2em foreign key (user_entity_id) references users;
