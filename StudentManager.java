import java.io.*;

public class StudentManager {
	
	private Student[] students = new Student[50];
	private int count = 0;
	private final String FILE_NAME = "students.txt";
	
	public StudentManager() {
		loadFromFile();
	}
	
	public void addStudent(Student s) {
		if (count < students.length) {
			students[count] = s;
			count++;
			saveToFile();
		}
	}
	
	public Student searchStudent(String id) {
		for (int i = 0; i < count; i++) {
			if (students[i].getId().equals(id)) {
				return students[i];
			}
		}
		return null;
	}
	
	public boolean deleteStudent(String id) {
		for (int i = 0; i<count; i++) {
			if(students[i].getId().equals(id)) {
				for(int j = i; j<count - 1; j++) {
					students[j] = students[j + 1];
				}
				students[count-1] = null;
				count--;
				saveToFile();
				return true;
			}
		}
		return false;
	}
	
	public Student[] getStudents() {
		return students;
	}
	
	public int getCount() {
		return count;
	}
	
	
	private void saveToFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
			for (int i = 0; i<count; i++) {
				bw.write(students[i].getId() + ","+
						students[i].getId() + "," +
						students[i].getDepartment());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Error writing file");
		}
	}

	private void loadFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
			String line;
			while ((line = br.readLine()) != null){
				String[] data = line.split(",");
				students [count] = new Student(data[0], data[1], data[2]);
				count++;
		}
			br.close();
		} catch (IOException e) {
		}
	}

}
