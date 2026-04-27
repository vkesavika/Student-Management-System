package com.student.model;

public class Student {

    private int    id;
    private String name;
    private String email;
    private int    age;
    private String course;

    // ---------- Constructors ----------

    public Student() {}

    public Student(String name, String email, int age, String course) {
        this.name   = name;
        this.email  = email;
        this.age    = age;
        this.course = course;
    }

    public Student(int id, String name, String email, int age, String course) {
        this.id     = id;
        this.name   = name;
        this.email  = email;
        this.age    = age;
        this.course = course;
    }

    // ---------- Getters & Setters ----------

    public int    getId()     { return id; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public int    getAge()    { return age; }
    public String getCourse() { return course; }

    public void setId(int id)          { this.id     = id; }
    public void setName(String name)   { this.name   = name; }
    public void setEmail(String email) { this.email  = email; }
    public void setAge(int age)        { this.age    = age; }
    public void setCourse(String c)    { this.course = c; }

    // ---------- Helpers ----------

    @Override
    public String toString() {
        return String.format(
            "Student{id=%d, name='%s', email='%s', age=%d, course='%s'}",
            id, name, email, age, course);
    }
}
