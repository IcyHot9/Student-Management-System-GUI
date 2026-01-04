import javax.swing.*;
import java.awt.event.*;

public class StudentGUI {

    public static void main(String[] args) {

        StudentManager manager = new StudentManager();

        JFrame frame = new JFrame("Student Management System");
        frame.setSize(400, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(30, 30, 100, 25);
        frame.add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(150, 30, 180, 25);
        frame.add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 70, 100, 25);
        frame.add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(150, 70, 180, 25);
        frame.add(txtName);

        JLabel lblDept = new JLabel("Department:");
        lblDept.setBounds(30, 110, 100, 25);
        frame.add(lblDept);

        JTextField txtDept = new JTextField();
        txtDept.setBounds(150, 110, 180, 25);
        frame.add(txtDept);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(30, 160, 80, 30);
        frame.add(btnAdd);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(120, 160, 80, 30);
        frame.add(btnSearch);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(210, 160, 80, 30);
        frame.add(btnDelete);

        JButton btnShow = new JButton("Show All");
        btnShow.setBounds(120, 210, 120, 30);
        frame.add(btnShow);

        btnAdd.addActionListener(e -> {
            String id = txtId.getText();
            String name = txtName.getText();
            String dept = txtDept.getText();

            manager.addStudent(new Student(id, name, dept));
            JOptionPane.showMessageDialog(frame, "Student Added");
        });

        btnSearch.addActionListener(e -> {
            Student s = manager.searchStudent(txtId.getText());
            if (s != null) {
                JOptionPane.showMessageDialog(frame,
                        "Name: " + s.getName() + "\nDepartment: " + s.getDepartment());
            } else {
                JOptionPane.showMessageDialog(frame, "Student Not Found");
            }
        });

        btnDelete.addActionListener(e -> {
            boolean removed = manager.deleteStudent(txtId.getText());
            if (removed) {
                JOptionPane.showMessageDialog(frame, "Student Deleted");
            } else {
                JOptionPane.showMessageDialog(frame, "Student Not Found");
            }
        });

        btnShow.addActionListener(e -> {
            String result = "";
            for (Student s : manager.getAllStudents()) {
                result += s.getId() + " - " + s.getName() + " - " + s.getDepartment() + "\n";
            }
            JOptionPane.showMessageDialog(frame, result.isEmpty() ? "No Students" : result);
        });

        frame.setVisible(true);
    }
}
