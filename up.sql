CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    house_number BIGINT NOT NULL,
    street VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postcode VARCHAR(255) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS patients (
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL, -- SERIAL is an alias for BIGINT UNSIGNED AUTOINCREMENT NOT NULL
    title VARCHAR(5) NOT NULL, -- A maxium of 5 characters for a title seems reasonable
    first_name VARCHAR(255) NOT NULL, -- A maxium of 255 characters for a first_name seems reasonable
    last_name VARCHAR(255) NOT NULL, -- A maxium of 255 characters for a last_name seems reasonable
    dob DATE NOT NULL,
    phone_number VARCHAR(15) NOT NULL, -- 15 is the maximum length in theory: https://en.wikipedia.org/wiki/Telephone_numbering_plan#International_numbering_plan
    address_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY (address_id)
        REFERENCES addresses(id)
	ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appointments (
    date DATE NOT NULL,
    partner VARCHAR(255) NOT NULL,
    patient_id BIGINT UNSIGNED, -- this is allowed to be null so that blank appointments can be booked, as specified now done as user 0
    start TIME NOT NULL,
    duration INT NOT NULL,
    complete BOOL NOT NULL,
    PRIMARY KEY(date,partner,start),
    FOREIGN KEY(patient_id)
        REFERENCES patients(id)
	ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS treatments (
    name VARCHAR(255) NOT NULL,
    cost BIGINT UNSIGNED NOT NULL,
    treatment_type ENUM('CHECKUP','HYGIENE','REPAIR'),
    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS treatment_applications(
    treatment_name VARCHAR(255) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    partner VARCHAR(255) NOT NULL,
    count INT NOT NULL,
    PRIMARY KEY(treatment_name,appointment_date,appointment_time,partner,count),
    FOREIGN KEY(treatment_name)
        REFERENCES treatments(name)
	ON DELETE CASCADE,
    FOREIGN KEY(appointment_date,partner,appointment_time)
        REFERENCES appointments(date,partner,start)
	ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS treatment_plans (
    name VARCHAR(255) NOT NULL,
    cost BIGINT UNSIGNED NOT NULL,
    checkups INT UNSIGNED NOT NULL,
    hygiene_visits INT UNSIGNED NOT NULL,
    repairs INT UNSIGNED NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS patient_plans (
    patient_id BIGINT UNSIGNED NOT NULL,
    plan_name VARCHAR(255) NOT NULL,
    used_checkups INT UNSIGNED NOT NULL,
    used_hygiene_visits INT UNSIGNED NOT NULL,
    used_repairs INT UNSIGNED NOT NULL,
    start_date DATE NOT NULL,
    PRIMARY KEY(patient_id,plan_name),
    FOREIGN KEY(patient_id)
        REFERENCES patients(id)
	ON DELETE CASCADE,
    FOREIGN KEY(plan_name)
        REFERENCES treatment_plans(name)
	ON DELETE CASCADE
);

INSERT INTO addresses (id,house_number,street,district,city,postcode) VALUES (1,0,'','','','');
INSERT INTO patients (id,title,first_name,last_name,dob,phone_number,address_id) VALUES (1,'','RESERVED','',CURDATE(),'', LAST_INSERT_ID() );

INSERT INTO treatment_plans (name, cost, checkups, hygiene_visits, repairs) VALUES ('Dental Repair', 3600, 2, 4, 2);
INSERT INTO treatment_plans (name, cost, checkups, hygiene_visits, repairs) VALUES ('Maintenance Plan', 1500, 2, 2, 0);
INSERT INTO treatment_plans (name, cost, checkups, hygiene_visits, repairs) VALUES ('NHS', 0, 0, 0, 0);
INSERT INTO treatment_plans (name, cost, checkups, hygiene_visits, repairs) VALUES ('NHS Kids', 0, 2, 2, 6);
INSERT INTO treatment_plans (name, cost, checkups, hygiene_visits, repairs) VALUES ('Oral', 2100, 2, 4, 0);
