abstract class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract String getInfo();

    public String getId() { return id; }
    public String getName() { return name; }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }
}

class Student extends Person {
    private String dept;
    private String course;
    private int credits;

    public Student(String id, String name, String dept, String course, int credits) {
        super(id, name);
        this.dept = dept;
        this.course = course;
        setCredits(credits);
    }

    @Override
    public String getInfo() {
        return id + " | " + name + " | " + dept + " | " + course + " | " + credits;
    }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public int getCredits() { return credits; }
    
    public void setCredits(int credits) {
        if (credits < 0 || credits > 200) {
            throw new IllegalArgumentException("Credits must be between 0 and 200");
        }
        this.credits = credits;
    }
}
