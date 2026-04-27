-- ============================================================
--  Student Management System – Database Schema
-- ============================================================

CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- Students table
CREATE TABLE IF NOT EXISTS students (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)        NOT NULL,
    email       VARCHAR(150) UNIQUE NOT NULL,
    age         INT                 NOT NULL,
    course      VARCHAR(100)        NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data
INSERT INTO students (name, email, age, course) VALUES
    ('Alice Johnson',  'alice@example.com',  20, 'Computer Science'),
    ('Bob Smith',      'bob@example.com',    22, 'Mathematics'),
    ('Carol White',   'carol@example.com',  21, 'Physics'),
    ('David Brown',   'david@example.com',  23, 'Engineering'),
    ('Eva Martinez',  'eva@example.com',    19, 'Chemistry');
