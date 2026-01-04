import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student searchStudent(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public boolean deleteStudent(String id) {
        Student s = searchStudent(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }
}
