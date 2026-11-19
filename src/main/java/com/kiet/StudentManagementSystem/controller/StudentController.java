
package com.kiet.StudentManagementSystem.controller;

import com.kiet.StudentManagementSystem.entity.Student;
import com.kiet.StudentManagementSystem.service.StudentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Add a student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Search student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Search students by email
    @GetMapping("/email/{email}")
    public List<Student> getStudentsByEmail(@PathVariable String email) {
        return studentService.getStudentsByEmail(email);
    }

    // Search students by course
    @GetMapping("/course/{course}")
    public List<Student> getStudentsByCourse(@PathVariable String course) {
        return studentService.getStudentsByCourse(course);
    }

    // Update scores and skills
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully!";
    }

    // Display merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return studentService.getMeritList();
    }

    // Students with same technical skills
    @GetMapping("/same-skills")
    public Map<List<String>, List<Student>> getStudentsWithSameSkills() {
        return studentService.getStudentsWithSameSkills();
    }

    // Programming improvement students
    @GetMapping("/programming-improvement")
    public List<Student> getProgrammingImprovementStudents() {
        return studentService.getProgrammingImprovementStudents();
    }
}

