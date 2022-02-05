-- Table: public.faculties

DROP TABLE IF EXISTS public.admins;
DROP TABLE IF EXISTS public.login_info;
DROP TABLE IF EXISTS public.students;
DROP TABLE IF EXISTS public.groups;
DROP TABLE IF EXISTS public.faculties;
DROP TABLE IF EXISTS public.marks;
DROP TABLE IF EXISTS public.disciplines;
DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.faculties
(
    faculty_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    faculty_code character varying(10) COLLATE pg_catalog."default" NOT NULL,
    faculty_full_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT faculties_pkey PRIMARY KEY (faculty_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.faculties
    OWNER to postgres;

-- Table: public.groups

-- DROP TABLE IF EXISTS public.groups;

CREATE TABLE IF NOT EXISTS public.groups
(
    group_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    group_code character varying(6) COLLATE pg_catalog."default" NOT NULL,
    group_full_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    faculty integer NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id),
    CONSTRAINT fk_faculty_id FOREIGN KEY (faculty)
    REFERENCES public.faculties (faculty_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.groups
    OWNER to postgres;

-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

-- Table: public.students

-- DROP TABLE IF EXISTS public.students;

CREATE TABLE IF NOT EXISTS public.students
(
    student_id integer NOT NULL,
    "group" integer NOT NULL,
    faculty integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    patronymic character varying(30) COLLATE pg_catalog."default",
    email character varying(30) COLLATE pg_catalog."default",
    course integer NOT NULL,
    CONSTRAINT faculty_id FOREIGN KEY (faculty)
    REFERENCES public.faculties (faculty_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT group_id FOREIGN KEY ("group")
    REFERENCES public.groups (group_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT student_id FOREIGN KEY (student_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students
    OWNER to postgres;


-- Table: public.disciplines

-- DROP TABLE IF EXISTS public.disciplines;

CREATE TABLE IF NOT EXISTS public.disciplines
(
    discipline_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    discipline_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    discipline_hours integer,
    CONSTRAINT disciplines_pkey PRIMARY KEY (discipline_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.disciplines
    OWNER to postgres;


-- Table: public.marks

-- DROP TABLE IF EXISTS public.marks;

CREATE TABLE IF NOT EXISTS public.marks
(
    student_id integer NOT NULL,
    discipline_id integer NOT NULL,
    mark integer NOT NULL,
    CONSTRAINT discipline_id FOREIGN KEY (discipline_id)
    REFERENCES public.disciplines (discipline_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT student_id FOREIGN KEY (student_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.marks
    OWNER to postgres;


-- Table: public.admins

-- DROP TABLE IF EXISTS public.admins;

CREATE TABLE IF NOT EXISTS public.admins
(
    id integer NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    patronymic character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "id_FK" FOREIGN KEY (id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.admins
    OWNER to postgres;


-- Table: public.login_info

-- DROP TABLE IF EXISTS public.login_info;

CREATE TABLE IF NOT EXISTS public.login_info
(
    login character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(200) COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL,
    role character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT id_fk FOREIGN KEY (id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.login_info
    OWNER to postgres;


-- INSERT --
INSERT INTO public.faculties(faculty_code, faculty_full_name) VALUES ('NNI', 'Faculty of Law');
INSERT INTO public.faculties(faculty_code, faculty_full_name) VALUES ('ELIT', 'Faculty of Electronics and Information Technologies');
INSERT INTO public.faculties(faculty_code, faculty_full_name) VALUES ('UABS', 'Faculty of Economy');

INSERT INTO public.groups(group_code, group_full_name, faculty) VALUES ('KB-91', 'CYBERSECURITY', 2);
INSERT INTO public.groups(group_code, group_full_name, faculty) VALUES ('IN-91', 'DATA SCIENCE', 2);
INSERT INTO public.groups(group_code, group_full_name, faculty) VALUES ('P-01', 'INTERNATIONAL LAW', 1);


INSERT INTO public.users(status) VALUES ('STUDENT');
INSERT INTO public.users(status) VALUES ('STUDENT');
INSERT INTO public.users(status) VALUES ('STUDENT');
INSERT INTO public.users(status) VALUES ('STUDENT');
INSERT INTO public.users(status) VALUES ('ADMIN');


INSERT INTO public.students(student_id, "group", faculty, name, surname, patronymic, email, course)
VALUES (1, 1, 2, 'Grigory', 'Komarov', 'Ivanovich', 'grigory@mail.com', 2);
INSERT INTO public.students(student_id, "group", faculty, name, surname, patronymic, email, course)
VALUES (2, 1, 2, 'Ann', 'Borisova', 'Sergeevna', 'ann@mail.com', 1);
INSERT INTO public.students(student_id, "group", faculty, name, surname, patronymic, email, course)
VALUES (3, 2, 2, 'Yaroslav', 'Pavlov', 'Ivanovich', 'yaroslav@mail.com', 4);
INSERT INTO public.students(student_id, "group", faculty, name, surname, patronymic, email, course)
VALUES (4, 3, 1, 'Ivan', 'Glebov', 'Egorovich', 'ivan@mail.com', 3);


INSERT INTO public.login_info(login, password, id, role)
VALUES ('grigory@mail.com', '$2a$12$1nNZ70SEYtDIQtbldRxw2..iulgYqWhk79lO4oYmHENnM47QBMpzq', 1, 'USER');
INSERT INTO public.login_info(login, password, id, role)
VALUES ('ann@mail.com', '$2a$12$1nNZ70SEYtDIQtbldRxw2..iulgYqWhk79lO4oYmHENnM47QBMpzq', 2, 'USER');
INSERT INTO public.login_info(login, password, id, role)
VALUES ('yaroslav@mail.com', '$2a$12$1nNZ70SEYtDIQtbldRxw2..iulgYqWhk79lO4oYmHENnM47QBMpzq', 3, 'USER');
INSERT INTO public.login_info(login, password, id, role)
VALUES ('ivan@mail.com', '$2a$12$1nNZ70SEYtDIQtbldRxw2..iulgYqWhk79lO4oYmHENnM47QBMpzq', 4, 'USER');
INSERT INTO public.login_info(login, password, id, role)
VALUES ('admin@mail.com', '$2a$12$NY3s3dgTsmFRnBO390EnvOfBYPYtSgAECys2skyWWDrZLlfEF.e/u', 5, 'ADMIN');


INSERT INTO public.admins(id, name, surname, patronymic)
VALUES (5, 'Kostya', 'Parfilo', 'Ivanovich');


INSERT INTO public.disciplines(discipline_name, discipline_hours) VALUES ('English', 55);
INSERT INTO public.disciplines(discipline_name, discipline_hours) VALUES ('Math', 35);
INSERT INTO public.disciplines(discipline_name, discipline_hours) VALUES ('Java', 42);


INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (1, 1, 74);
INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (1, 2, 88);
INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (1, 3, 62);
INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (2, 1, 75);
INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (2, 3, 55);
INSERT INTO public.marks(student_id, discipline_id, mark) VALUES (3, 1, 99);