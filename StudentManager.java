import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Interface for Abstraction
interface IStudentManager {
    void add(Student s);
    boolean update(String id, String n, String d, String c, int cr);
    boolean delete(String id);
    List<Student> getAllStudents();
}

public class StudentManager implements IStudentManager {
    private List<Student> list;  // Encapsulation: private field
    private final String FILE = "students.txt";

    public StudentManager() {
        list = new ArrayList<>();
        load();  // Load from file on startup
    }

    @Override
    public void add(Student s) { 
        list.add(s); 
        save(); 
    }

    // Polymorphism: method overloading
    public Student find(String id) {
        for (Student s : list) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    // Polymorphism: method overloading (find by name)
    public Student findByName(String name) {
        for (Student s : list) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    @Override
    public boolean update(String id, String n, String d, String c, int cr) {
        Student s = find(id);
        if (s == null) return false;
        
        s.setName(n);
        s.setDept(d);
        s.setCourse(c);
        s.setCredits(cr);
        save(); 
        return true;
    }

    @Override
    public boolean delete(String id) {
        Student s = find(id);
        if (s != null) {
            list.remove(s);
            save();
            return true;
        }
        return false;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(list);  // Return copy for encapsulation
    }

    // File I/O with proper Exception Handling
    private void save() {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (Student s : list) {
                pw.println(s.getId() + "," + s.getName() + "," + 
                          s.getDept() + "," + s.getCourse() + "," + s.getCredits());
            }
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    private void load() {
        File file = new File(FILE);
        if (!file.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int credits = Integer.parseInt(parts[4]);
                    list.add(new Student(parts[0], parts[1], parts[2], parts[3], credits));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing credits from file: " + e.getMessage());
        }
    }
}
