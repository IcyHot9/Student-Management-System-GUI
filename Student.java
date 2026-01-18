// Abstract class for Abstraction
abstract class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Abstract method for Abstraction
    public abstract String getInfo();

    // Encapsulation with getters
    public String getId() { return id; }
    public String getName() { return name; }
    
    // Setter with validation (Exception Handling)
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }
}

// Student class with Inheritance
class Student extends Person {
    private String dept;    // Encapsulation: private fields
    private String course;
    private int credits;

    public Student(String id, String name, String dept, String course, int credits) {
        super(id, name);
        this.dept = dept;
        this.course = course;
        setCredits(credits); // Using setter for validation
    }

    // Implementing abstract method (Polymorphism)
    @Override
    public String getInfo() {
        return id + " | " + name + " | " + dept + " | " + course + " | " + credits;
    }

    // Getters and Setters (Encapsulation)
    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public int getCredits() { return credits; }
    
    // Setter with validation (Exception Handling)
    public void setCredits(int credits) {
        if (credits < 0 || credits > 200) {
            throw new IllegalArgumentException("Credits must be between 0 and 200");
        }
        this.credits = credits;
    }
}
