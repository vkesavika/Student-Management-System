package com.student.dao;

import com.student.db.DatabaseConnection;
import com.student.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link StudentDAO}.
 * All SQL is executed via PreparedStatements to prevent SQL injection.
 */
public class StudentDAOImpl implements StudentDAO {

    // ── SQL constants ────────────────────────────────────────────────────
    private static final String SQL_INSERT =
        "INSERT INTO students (name, email, age, course) VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
        "SELECT id, name, email, age, course FROM students ORDER BY id";

    private static final String SQL_SELECT_BY_ID =
        "SELECT id, name, email, age, course FROM students WHERE id = ?";

    private static final String SQL_UPDATE =
        "UPDATE students SET name=?, email=?, age=?, course=? WHERE id=?";

    private static final String SQL_DELETE =
        "DELETE FROM students WHERE id=?";
    // ─────────────────────────────────────────────────────────────────────

    @Override
    public int addStudent(Student student) {
        int generatedId = -1;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt   (3, student.getAge());
            ps.setString(4, student.getCourse());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) generatedId = keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("addStudent error: " + e.getMessage());
        }
        return generatedId;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("getAllStudents error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Student getStudentById(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("getStudentById error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateStudent(Student student) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt   (3, student.getAge());
            ps.setString(4, student.getCourse());
            ps.setInt   (5, student.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("updateStudent error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("deleteStudent error: " + e.getMessage());
            return false;
        }
    }

    // ── Helper ───────────────────────────────────────────────────────────

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt   ("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt   ("age"),
            rs.getString("course")
        );
    }
}
