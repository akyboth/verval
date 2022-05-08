package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static Service service;
    private static HomeworkXMLRepository homeworkXmlRepo;
    private static GradeXMLRepository gradeXmlRepo;
    private static StudentXMLRepository studentXmlRepo;

    @BeforeAll
    static void init() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        homeworkXmlRepo = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        gradeXmlRepo = new GradeXMLRepository(gradeValidator, "grades.xml");
        studentXmlRepo = new StudentXMLRepository(studentValidator, "students.xml");
        service = new Service(studentXmlRepo, homeworkXmlRepo, gradeXmlRepo);
    }

    @Test
    void findAllStudents() {
        Iterable<Student> students1 = studentXmlRepo.findAll();
        Iterable<Student> students2 = service.findAllStudents();
        assertEquals(students1, students2);
    }

    @Test
    void saveStudent() {
        studentXmlRepo.delete("8");
        Iterable<Student> students = studentXmlRepo.findAll();
        int counter1 = 0;
        for (Student i:students) {
            ++counter1;
        }
        studentXmlRepo.save(new Student("8", "Ionel", 250));

        int counter2 = 0;
        for (Student i:students) {
            ++counter2;
        }

        assertEquals(counter1, counter2 - 1);
    }

    @Test
    void deleteStudent() {
        studentXmlRepo.save(new Student("8", "Ionel", 250));
        Iterable<Student> students1 = studentXmlRepo.findAll();
        int counter1 = 0;
        for (Student i:students1) {
            ++counter1;
        }
        studentXmlRepo.delete("8");
        Iterable<Student> students2 = studentXmlRepo.findAll();
        int counter2 = 0;
        for (Student i:students2) {
            ++counter2;
        }
        assertEquals(counter1, counter2+1);
    }

    @Test
    void updateStudent() {
        studentXmlRepo.delete("8");
        studentXmlRepo.save(new Student("8", "Ilie", 223));
        int group1 = studentXmlRepo.findOne("8").getGroup();
        studentXmlRepo.update(new Student("8", "Ilie", 230));
        int group2 = studentXmlRepo.findOne("8").getGroup();
        studentXmlRepo.delete("8");
        assertNotEquals(group1, group2);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6})
    void findOneStudent(int num) {
        studentXmlRepo.save(new Student(String.valueOf(num), "Ionel", 250));
        Student student1 = studentXmlRepo.findOne(String.valueOf(num));
        Boolean found = false;
        for (Student student : studentXmlRepo.findAll()) {
            if (student1 == student) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
}