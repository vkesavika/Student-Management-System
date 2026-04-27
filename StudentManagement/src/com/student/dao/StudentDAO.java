package com.student.dao;

import com.student.model.Student;
import java.util.List;

/**
 * Data-Access-Object contract for Student CRUD operations.
 */
public interface StudentDAO {

    /** Persist a new student and return the generated id. */
    int addStudent(Student student);

    /** Return all students, ordered by id. */
    List<Student> getAllStudents();

    /** Return the student with the given id, or null if not found. */
    Student getStudentById(int id);

    /** Update an existing student's details. Returns true on success. */
    boolean updateStudent(Student student);

    /** Delete the student with the given id. Returns true on success. */
    boolean deleteStudent(int id);
}
