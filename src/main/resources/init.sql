-- Initialize the H2 database schema for the Pet Care Clinic

-- Drop existing tables if they exist
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS shelters;

-- Create the Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    street VARCHAR(255),
    zip VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    breedPreferences VARCHAR(255),
    profilePicture VARCHAR(255),
    budget DOUBLE,
    openToAdoptions BOOLEAN
);

-- Create the Shelters table
CREATE TABLE shelters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255)
);

-- Create the Pets table
CREATE TABLE pets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    age INT,
    vaccinated BOOLEAN,
    birthDate DATE,
    photoUrl VARCHAR(255),
    shelter_id BIGINT,
    FOREIGN KEY (shelter_id) REFERENCES shelters(id)
);

-- Create the Applications table
CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    pet_id BIGINT,
    status VARCHAR(20),
    submissionDate DATE,
    notes VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);

-- Create the Appointments table
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    shelter_id BIGINT,
    pet_id BIGINT,
    appointmentDate DATE,
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (shelter_id) REFERENCES shelters(id),
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);

-- Create the News table
CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(5000) NOT NULL,
    publishDate DATE,
    shelter_id BIGINT,
    FOREIGN KEY (shelter_id) REFERENCES shelters(id)
);

-- Create the Translations table
CREATE TABLE translations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    language VARCHAR(255),
    key VARCHAR(255),
    value VARCHAR(255)
);

-- Insert sample data into the tables

-- Users
INSERT INTO users (username, password, role, firstName, lastName, street, zip, city, country, breedPreferences, profilePicture, budget, openToAdoptions)
VALUES ('john_doe', 'password123', 'REGULAR', 'John', 'Doe', '123 Main St', '12345', 'Cityville', 'Countryland', 'Labrador', 'john_profile.png', 1000.00, true);

INSERT INTO users (username, password, role, firstName, lastName, street, zip, city, country, breedPreferences, profilePicture, budget, openToAdoptions)
VALUES ('shelter_admin', 'password456', 'SHELTER', 'Jane', 'Smith', '456 Shelter Ave', '54321', 'Sheltertown', 'Countryland', NULL, 'jane_profile.png', 0.00, false);

-- Shelters
INSERT INTO shelters (name, address)
VALUES ('Happy Paws Shelter', '789 Animal St, Petville, Countryland');

-- Pets
INSERT INTO pets (name, description, age, vaccinated, birthDate, photoUrl, shelter_id)
VALUES ('Buddy', 'Friendly dog', 3, true, '2020-01-01', 'buddy.png', 1);

-- Applications
INSERT INTO applications (user_id, pet_id, status, submissionDate, notes)
VALUES (1, 1, 'PENDING', '2024-01-01', 'Looking forward to adopting Buddy.');

-- Appointments
INSERT INTO appointments (user_id, shelter_id, pet_id, appointmentDate, status)
VALUES (1, 1, 1, '2024-02-01', 'Scheduled');

-- News
INSERT INTO news (title, content, publishDate, shelter_id)
VALUES ('New Year Adoption Drive', 'Join us for our New Year adoption drive and find your new best friend!', '2024-01-01', 1);

-- Translations
INSERT INTO translations (language, key, value)
VALUES ('en', 'welcome_message', 'Welcome to the Pet Care Clinic!');
