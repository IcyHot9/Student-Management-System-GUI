import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI {

    static final String ADMIN_PASS = "1234";
    static StudentManager m = new StudentManager();

    static CardLayout card = new CardLayout();
    static JPanel pages = new JPanel(card);

    static Color bgMain = new Color(240,244,248);
    static Color sideBar = new Color(25,42,86);
    static Color cardBg = Color.WHITE;
    static Color fieldBg = new Color(232,240,254);
    static Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
    static Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
    static Font btnFont   = new Font("Segoe UI", Font.BOLD, 15);

    public static void main(String[] args) {
        showLogin();
    }

    static void showLogin() {
        JFrame f = new JFrame("Admin Login");
        f.setSize(500,300);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(bgMain);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(cardBg);
        card.setPreferredSize(new Dimension(380,220));
        card.setBorder(BorderFactory.createTitledBorder("Admin Login"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15,15,15,15);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField id = bigField();
        JPasswordField pass = new JPasswordField();
        pass.setFont(fieldFont);
        pass.setBackground(fieldBg);
        pass.setPreferredSize(new Dimension(260,40));

        JButton login = button("Login", new Color(52,152,219));

        c.gridx=0; c.gridy=0; card.add(label("Admin ID"), c);
        c.gridx=1; card.add(id, c);
        c.gridx=0; c.gridy=1; card.add(label("Password"), c);
        c.gridx=1; card.add(pass, c);
        c.gridx=1; c.gridy=2; card.add(login, c);

        login.addActionListener(e -> {
            if (new String(pass.getPassword()).equals(ADMIN_PASS)) {
                f.dispose();
                showSystem();
            } else {
                JOptionPane.showMessageDialog(f, "Wrong Password");
            }
        });

        bg.add(card);
        f.add(bg);
        f.setVisible(true);
    }

    static void showSystem() {
        JFrame f = new JFrame("Student Management System");
        f.setSize(1100,650);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(sideBar);
        menu.setBorder(BorderFactory.createEmptyBorder(40,20,40,20));

        JButton add = button("Add Student", new Color(52,152,219));
        JButton upd = button("Update Student", new Color(46,204,113));
        JButton del = button("Delete Student", new Color(231,76,60));
        JButton view = button("View Students", new Color(155,89,182));
        
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        upd.setAlignmentX(Component.CENTER_ALIGNMENT);
        del.setAlignmentX(Component.CENTER_ALIGNMENT);
        view.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension btnSize = new Dimension(180, 50);
        add.setPreferredSize(btnSize);
        add.setMaximumSize(btnSize);
        add.setMinimumSize(btnSize);
        
        upd.setPreferredSize(btnSize);
        upd.setMaximumSize(btnSize);
        upd.setMinimumSize(btnSize);
        
        del.setPreferredSize(btnSize);
        del.setMaximumSize(btnSize);
        del.setMinimumSize(btnSize);
        
        view.setPreferredSize(btnSize);
        view.setMaximumSize(btnSize);
        view.setMinimumSize(btnSize);

        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        menu.add(add);
        menu.add(Box.createRigidArea(new Dimension(0, 20)));
        menu.add(upd);
        menu.add(Box.createRigidArea(new Dimension(0, 20)));
        menu.add(del);
        menu.add(Box.createRigidArea(new Dimension(0, 20)));
        menu.add(view);
        menu.add(Box.createVerticalGlue());

        pages.add(formPage("Add", true), "ADD");
        pages.add(formPage("Update", false), "UPDATE");
        pages.add(deletePage(), "DELETE");
        pages.add(viewPage(), "VIEW");

        add.addActionListener(e->card.show(pages,"ADD"));
        upd.addActionListener(e->card.show(pages,"UPDATE"));
        del.addActionListener(e->card.show(pages,"DELETE"));
        view.addActionListener(e->card.show(pages,"VIEW"));

        f.add(menu, BorderLayout.WEST);
        f.add(pages, BorderLayout.CENTER);
        f.setVisible(true);
    }

    static JPanel formPage(String title, boolean isAdd) {
        JPanel bg = new JPanel(new BorderLayout());
        bg.setBackground(bgMain);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(cardBg);
        form.setBorder(BorderFactory.createTitledBorder(title + " Student"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15,20,15,20);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField id = bigField(), name = bigField(), course = bigField(), credits = bigField();
        JComboBox<String> dept = combo();

        addRow(form,c,0,"Student ID",id);
        addRow(form,c,1,"Name",name);
        addRow(form,c,2,"Department",dept);
        addRow(form,c,3,"Course",course);
        addRow(form,c,4,"Credits",credits);

        JButton submit = button(title, isAdd ? new Color(52,152,219) : new Color(46,204,113));
        c.gridx=1; c.gridy=5; form.add(submit,c);

        submit.addActionListener(e->{
            try {
                int cr = Integer.parseInt(credits.getText());
                
                if(isAdd){
                    Student s = new Student(id.getText(), name.getText(),
                                          dept.getSelectedItem().toString(), 
                                          course.getText(), cr);
                    m.add(s);
                    msg("Student Added");
                    id.setText(""); name.setText(""); 
                    course.setText(""); credits.setText("");
                } else {
                    boolean updated = m.update(id.getText(), name.getText(),
                                             dept.getSelectedItem().toString(),
                                             course.getText(), cr);
                    msg(updated ? "Updated" : "Student Not Found");
                }
                
            } catch(NumberFormatException ex) { 
                msg("Please enter valid number for credits");
            } catch(IllegalArgumentException ex) {
                msg("Validation Error: " + ex.getMessage());
            } catch(Exception ex) { 
                msg("Error: " + ex.getMessage()); 
            }
        });

        bg.add(form, BorderLayout.NORTH);
        return bg;
    }

    static JPanel deletePage() {
        JPanel bg = new JPanel(new BorderLayout());
        bg.setBackground(bgMain);

        JPanel f = new JPanel(new GridBagLayout());
        f.setBackground(cardBg);
        f.setBorder(BorderFactory.createTitledBorder("Delete Student"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20,20,20,20);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField id = bigField();
        JButton del = button("Delete", new Color(231,76,60));

        c.gridx=0;c.gridy=0;f.add(label("Student ID"),c);
        c.gridx=1;f.add(id,c);
        c.gridx=1;c.gridy=1;f.add(del,c);

        del.addActionListener(e->{
            boolean deleted = m.delete(id.getText());
            msg(deleted ? "Deleted" : "Not Found");
            if (deleted) id.setText("");
        });

        bg.add(f, BorderLayout.NORTH);
        return bg;
    }

    static JPanel viewPage() {
        JPanel bg = new JPanel(new BorderLayout(15,15));
        bg.setBackground(bgMain);
        bg.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String[] cols = {"ID","Name","Dept","Course","Credits"};
        DefaultTableModel model = new DefaultTableModel(cols,0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI",Font.PLAIN,15));
        table.setRowHeight(28);

       JButton refresh = button("Refresh", new Color(52,152,219));
        refresh.addActionListener(e->{
        model.setRowCount(0);
        Student[] students = m.getAllStudents();
        for(Student s : students){
        String[] row = s.getInfo().split(" \\| ");
        model.addRow(row);
            }
        });

        refresh.doClick();

        bg.add(new JScrollPane(table), BorderLayout.CENTER);
        bg.add(refresh, BorderLayout.SOUTH);
        return bg;
    }

    static void addRow(JPanel p, GridBagConstraints c, int y, String t, JComponent f){
        c.gridx=0;c.gridy=y;p.add(label(t),c);
        c.gridx=1;f.setPreferredSize(new Dimension(320,42));p.add(f,c);
    }

    static JLabel label(String t){
        JLabel l=new JLabel(t);
        l.setFont(labelFont);
        return l;
    }

    static JTextField bigField(){
        JTextField t=new JTextField();
        t.setFont(fieldFont);
        t.setBackground(fieldBg);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6,10,6,10)));
        return t;
    }

    static JComboBox<String> combo(){
        JComboBox<String> c=new JComboBox<>(new String[]{"CSE","EEE","BBA","LAW"});
        c.setFont(fieldFont);
        c.setBackground(fieldBg);
        c.setPreferredSize(new Dimension(320, 42));
        return c;
    }

    static JButton button(String t, Color color){
        JButton b=new JButton(t);
        b.setFont(btnFont);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return b;
    }

    static void msg(String s){
        JOptionPane.showMessageDialog(null,s);
    }
}
