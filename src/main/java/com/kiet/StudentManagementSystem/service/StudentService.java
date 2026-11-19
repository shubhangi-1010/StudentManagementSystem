
package com.kiet.StudentManagementSystem.service;

import com.kiet.StudentManagementSystem.entity.Student;
import com.kiet.StudentManagementSystem.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Add student
    public Student addStudent(Student student) {

        if (studentRepository.existsById(student.getStudentId())) {
            throw new IllegalArgumentException("Student ID already exists!");
        }

        return studentRepository.save(student);
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Search by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Student not found!"));
    }

    // Search by email
    public List<Student> getStudentsByEmail(String email) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    // Search by course
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getCourse().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    // Update scores and technical skills
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existing = getStudentById(id);

        existing.setProgrammingScore(updatedStudent.getProgrammingScore());
        existing.setMathematicsScore(updatedStudent.getMathematicsScore());
        existing.setTechnicalSkills(updatedStudent.getTechnicalSkills());

        return studentRepository.save(existing);
    }

    // Delete student
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    // Merit list - descending order of average score
    public List<Student> getMeritList() {
        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Student::getAverageScore)
                        .reversed())
                .collect(Collectors.toList());
    }

    // Students with same technical skills
    public Map<List<String>, List<Student>> getStudentsWithSameSkills() {

        return studentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(student -> {
                    List<String> skills = new ArrayList<>(
                            student.getTechnicalSkills());
                    skills.replaceAll(String::toLowerCase);
                    Collections.sort(skills);
                    return skills;
                }));
    }

    // Programming needs improvement but mathematics >= 75
    public List<Student> getProgrammingImprovementStudents() {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getProgrammingScore() < 50
                        && s.getMathematicsScore() >= 75)
                .collect(Collectors.toList());
    }
}

