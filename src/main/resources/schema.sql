CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT         NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id SERIAL NOT NULL,
    course_id  SERIAL NOT NULL,
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course UNIQUE (student_id, course_id)
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
    teacher_name    VARCHAR(50) NOT NULL,
    teacher_surname VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS lessons
(
    lesson_id  SERIAL PRIMARY KEY,
    teacher_id INT       NOT NULL,
    class_room INT       NOT NULL,
    time_start timestamp NOT NULL,
    time_end   timestamp NOT NULL,
    FOREIGN KEY (teacher_id) references teachers (teacher_id)
        on delete cascade
);





