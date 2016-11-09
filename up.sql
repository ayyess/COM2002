CREATE TABLE address(
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    house_number BIGINT NOT NULL,
    street VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postcode VARCHAR(255) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE patient(
    id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL, -- SERIAL is an alias for BIGINT UNSIGNED AUTOINCREMENT NOT NULL
    title VARCHAR(5) NOT NULL, -- A maxium of 5 characters for a title seems reasonable
    forname VARCHAR(255) NOT NULL, -- A maxium of 255 characters for a forname seems reasonable
    surname VARCHAR(255) NOT NULL, -- A maxium of 255 characters for a surname seems reasonable
    dob DATE NOT NULL,
    phone_number VARCHAR(15) NOT NULL, -- 15 is the maximum length in theory: https://en.wikipedia.org/wiki/Telephone_numbering_plan#International_numbering_plan
    address_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY ( id ),
    FOREIGN KEY (address_id)
        REFERENCES address(id)
);

CREATE TABLE appointment(
    date DATE NOT NULL,
    practitioner VARCHAR(255) NOT NULL,
    patient_id BIGINT UNSIGNED, -- this is allowed to be null so that balnk appointments can be booked, as specified
    start TIME NOT NULL,
    duration INT NOT NULL,
    PRIMARY KEY(date,practitioner,start),
    FOREIGN KEY(patient_id)
        REFERENCES patient(id)
);

CREATE TABLE treatment(
    name VARCHAR(255) NOT NULL,
    cost BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE treatment_applications(
    treatment_name VARCHAR(255) NOT NULL,
    appointment_date DATE NOT NULL,
    practitioner VARCHAR(255) NOT NULL,
    PRIMARY KEY(treatment_name,appointment_date,practitioner),
    FOREIGN KEY(treatment_name)
        REFERENCES treatment(name),
    FOREIGN KEY(appointment_date,practitioner)
        REFERENCES appointment(date,practitioner)
);

CREATE TABLE treatment_plan(
    name VARCHAR(255) NOT NULL,
    cost BIGINT UNSIGNED NOT NULL,
    checkups INT UNSIGNED NOT NULL,
    hygiene_visits INT UNSIGNED NOT NULL,
    repairs INT UNSIGNED NOT NULL
);

CREATE TABLE patient_plan(
    patient_id BIGINT UNSIGNED NOT NULL,
    plan_name VARCHAR(255) NOT NULL,
    used_checkups INT UNSIGNED NOT NULL,
    used_hygiene_visits INT UNSIGNED NOT NULL,
    used_repairs INT UNSIGNED NOT NULL,
    PRIMARY KEY(patient_id,plan_name),
    FOREIGN KEY(patient_id)
        REFERENCES patient(id),
    FOREIGN KEY(plan_name)
        REFERENCES treatment_plan(name)
);
