

package com.kiet.StudentManagementSystem.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "students")
    public class Student {

        @Id
        private Long studentId;

        private String name;
        private String email;
        private String course;

        private Double programmingScore;
        private Double mathematicsScore;

        @ElementCollection
        @CollectionTable(
                name = "student_technical_skills",
                joinColumns = @JoinColumn(name = "student_id")
        )
        @Column(name = "skill")
        private List<String> technicalSkills = new ArrayList<>();

        // Default constructor (required by JPA)
        public Student() {
        }

        // Parameterized constructor
        public Student(Long studentId, String name, String email,
                       String course, Double programmingScore,
                       Double mathematicsScore, List<String> technicalSkills) {

            this.studentId = studentId;
            this.name = name;
            this.email = email;
            this.course = course;
            setProgrammingScore(programmingScore);
            setMathematicsScore(mathematicsScore);
            setTechnicalSkills(technicalSkills);
        }

        // Getters and Setters
        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public Double getProgrammingScore() {
            return programmingScore;
        }

        public void setProgrammingScore(Double programmingScore) {
            validateScore(programmingScore);
            this.programmingScore = programmingScore;
        }

        public Double getMathematicsScore() {
            return mathematicsScore;
        }

        public void setMathematicsScore(Double mathematicsScore) {
            validateScore(mathematicsScore);
            this.mathematicsScore = mathematicsScore;
        }

        public List<String> getTechnicalSkills() {
            return technicalSkills;
        }

        public void setTechnicalSkills(List<String> technicalSkills) {
            this.technicalSkills = technicalSkills == null
                    ? new ArrayList<>()
                    : new ArrayList<>(technicalSkills);
        }

        // Score validation
        private void validateScore(Double score) {
            if (score == null || score < 0 || score > 100) {
                throw new IllegalArgumentException(
                        "Score must be between 0 and 100"
                );
            }
        }

        // Calculate average
        @Transient
        public double getAverageScore() {
            return (programmingScore + mathematicsScore) / 2.0;
        }

        // Performance category
        @Transient
        public String getPerformanceCategory() {
            double average = getAverageScore();

            if (average >= 85) {
                return "Excellent";
            } else if (average >= 70) {
                return "Good";
            } else if (average >= 50) {
                return "Satisfactory";
            } else {
                return "Needs Improvement";
            }
        }

        // Identify stronger subject
        @Transient
        public String getStrongerSubject() {
            if (programmingScore > mathematicsScore) {
                return "Programming";
            } else if (mathematicsScore > programmingScore) {
                return "Mathematics";
            } else {
                return "Both are equal";
            }
        }

        // toString method
        @Override
        public String toString() {
            return "Student{" +
                    "studentId=" + studentId +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", course='" + course + '\'' +
                    ", programmingScore=" + programmingScore +
                    ", mathematicsScore=" + mathematicsScore +
                    ", technicalSkills=" + technicalSkills +
                    ", average=" + getAverageScore() +
                    '}';
        }
    }

