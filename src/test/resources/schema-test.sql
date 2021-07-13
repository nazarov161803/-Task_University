CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT         NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(50)  NOT NULL,
    course_description VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS teachers
(
    teacher_id      SERIAL PRIMARY KEY,
    teacher_name    VARCHAR(50)  NOT NULL,
    teacher_surname VARCHAR(50) NOT NULL
);
