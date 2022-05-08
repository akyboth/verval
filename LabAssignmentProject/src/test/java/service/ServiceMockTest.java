package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceMockTest {
    @Mock
    StudentXMLRepository studentXmlRepo;
    @Mock
    HomeworkXMLRepository homeworkXmlRepo;
    @Mock
    GradeXMLRepository gradeXmlRepo;

    @Captor
    ArgumentCaptor<Student> studentCaptor;

    public Service service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new Service(studentXmlRepo, homeworkXmlRepo, gradeXmlRepo);
        studentCaptor = ArgumentCaptor.forClass(Student.class);
    }

    @Test
    void findAllStudents() {
        Iterable<Student> students = Arrays.asList(new Student[]{
                new Student("101", "Akos1", 531),
                new Student("102", "Akos2", 531)});
        Mockito.when(studentXmlRepo.findAll()).thenReturn(students);
        assertEquals(students, service.findAllStudents());
    }

    @Test
    void updateStudent() {
        Student mockStudent = new Student("101", "Akos", 531);
        Mockito.when(studentXmlRepo.update(mockStudent)).thenReturn(mockStudent);
        assertEquals(1, service.updateStudent("101", "Akos", 531));
    }

    @Test
    void updateStudentGroup() {
        Student mockStudent = new Student("101", "Akos", 531);
        Mockito.when(studentXmlRepo.update(mockStudent)).thenReturn(mockStudent);
        service.updateStudent("101", "Akos", 531);
        Mockito.verify(studentXmlRepo).update(studentCaptor.capture());
        assertEquals(new Student("101", "Akos", 531), studentCaptor.getValue());
    }
}
