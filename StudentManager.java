import java.io.*;

interface IStudentManager {
    void add(Student s);
    boolean update(String id, String n, String d, String c, int cr);
    boolean delete(String id);
    Student[] getAllStudents();
}

public class StudentManager implements IStudentManager {
    private Student[] list;
    private int size;
    private int capacity;
    private final String FILE = "students.txt";

    public StudentManager() {
        capacity = 10;
        list = new Student[capacity];
        size = 0;
        load();
    }

    private void resize() {
        capacity = capacity * 2;
        Student[] newList = new Student[capacity];
        for (int i = 0; i < size; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }

    @Override
    public void add(Student s) { 
        if (size == capacity) {
            resize();
        }
        list[size] = s;
        size++;
        save(); 
    }

    public Student find(String id) {
        for (int i = 0; i < size; i++) {
            if (list[i].getId().equals(id)) return list[i];
        }
        return null;
    }

    public Student findByName(String name) {
        for (int i = 0; i < size; i++) {
            if (list[i].getName().equalsIgnoreCase(name)) return list[i];
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
        int index = -1;
        
        for (int i = 0; i < size; i++) {
            if (list[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                list[i] = list[i + 1];
            }
            list[size - 1] = null;
            size--;
            save();
            return true;
        }
        return false;
    }

    @Override
    public Student[] getAllStudents() {
        Student[] result = new Student[size];
        for (int i = 0; i < size; i++) {
            result[i] = list[i];
        }
        return result;
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (int i = 0; i < size; i++) {
                Student s = list[i];
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
                    add(new Student(parts[0], parts[1], parts[2], parts[3], credits));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing credits from file: " + e.getMessage());
        }
    }
}
