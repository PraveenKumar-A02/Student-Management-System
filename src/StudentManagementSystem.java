import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class StudentManagementSystem extends JFrame implements ActionListener {
    // UI Components
    private JLabel jlTitle, jlName, jlID, jlGrade, jlDOB, jlGender, jlContact, jlEmail;
    private JTextField tfName, tfID, tfGrade, tfDOB, tfContact, tfEmail, tfSearch;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnReset;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    // Database Properties
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mysql@02";
    private Connection connection;

    public StudentManagementSystem() {
        initializeUI();
        connectToDatabase();
        loadStudentDataFromDatabase();
    }

    private void initializeUI() {
        // Frame settings
        setTitle("Student Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Title
        jlTitle = new JLabel("Student Management System", JLabel.CENTER);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 24));
        jlTitle.setBounds(300, 10, 400, 30);
        add(jlTitle);

        // Labels and text fields
        jlName = new JLabel("Name");
        jlName.setBounds(50, 60, 100, 30);
        add(jlName);
        tfName = new JTextField();
        tfName.setBounds(150, 60, 200, 30);
        add(tfName);

        jlID = new JLabel("Student ID");
        jlID.setBounds(50, 100, 100, 30);
        add(jlID);
        tfID = new JTextField();
        tfID.setBounds(150, 100, 200, 30);
        add(tfID);

        jlGrade = new JLabel("Grade");
        jlGrade.setBounds(50, 140, 100, 30);
        add(jlGrade);
        tfGrade = new JTextField();
        tfGrade.setBounds(150, 140, 200, 30);
        add(tfGrade);

        jlDOB = new JLabel("Date of Birth");
        jlDOB.setBounds(50, 180, 100, 30);
        add(jlDOB);
        tfDOB = new JTextField();
        tfDOB.setBounds(150, 180, 200, 30);
        add(tfDOB);

        jlGender = new JLabel("Gender");
        jlGender.setBounds(50, 220, 100, 30);
        add(jlGender);
        rbMale = new JRadioButton("Male");
        rbMale.setBounds(150, 220, 70, 30);
        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(220, 220, 80, 30);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);

        jlContact = new JLabel("Contact");
        jlContact.setBounds(50, 260, 100, 30);
        add(jlContact);
        tfContact = new JTextField();
        tfContact.setBounds(150, 260, 200, 30);
        add(tfContact);

        jlEmail = new JLabel("Email");
        jlEmail.setBounds(50, 300, 100, 30);
        add(jlEmail);
        tfEmail = new JTextField();
        tfEmail.setBounds(150, 300, 200, 30);
        add(tfEmail);

        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setBounds(50, 350, 100, 30);
        btnAdd.addActionListener(this);
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(170, 350, 100, 30);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(290, 350, 100, 30);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnReset = new JButton("Reset");
        btnReset.setBounds(50, 400, 100, 30);
        btnReset.addActionListener(this);
        add(btnReset);

        // Search field and button
        tfSearch = new JTextField();
        tfSearch.setBounds(500, 60, 200, 30);
        add(tfSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(720, 60, 100, 30);
        btnSearch.addActionListener(this);
        add(btnSearch);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Name", "Student ID", "Grade", "Date of Birth", "Gender", "Contact", "Email"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(400, 100, 550, 300);
        add(scrollPane);

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection successful!");
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentDataFromDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("student_id"),
                        rs.getString("grade"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("email")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load student data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = tfName.getText().trim();
        String id = tfID.getText().trim();
        String grade = tfGrade.getText().trim();
        String dob = tfDOB.getText().trim();
        String contact = tfContact.getText().trim();
        String email = tfEmail.getText().trim();
        String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "");

        if (name.isEmpty() || id.isEmpty() || grade.isEmpty() || dob.isEmpty() || contact.isEmpty() || email.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "INSERT INTO students (name, student_id, grade, dob, gender, contact, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, id);
            ps.setString(3, grade);
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setString(6, contact);
            ps.setString(7, email);
            ps.executeUpdate();

            tableModel.addRow(new Object[]{name, id, grade, dob, gender, contact, email});
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            resetFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to add student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = tfID.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID is required to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "UPDATE students SET name = ?, grade = ?, dob = ?, gender = ?, contact = ?, email = ? WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, tfName.getText().trim());
            ps.setString(2, tfGrade.getText().trim());
            ps.setString(3, tfDOB.getText().trim());
            ps.setString(4, rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : ""));
            ps.setString(5, tfContact.getText().trim());
            ps.setString(6, tfEmail.getText().trim());
            ps.setString(7, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
                loadStudentDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to update student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = tfID.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID is required to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudentDataFromDatabase();
            } else {
                JOptionPane.showMessageDialog(this, "No student found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to delete student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "No database connection.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String keyword = tfSearch.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a keyword to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM students WHERE name LIKE ? OR student_id LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                tableModel.setRowCount(0); // Clear existing rows

                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getString("name"),
                            rs.getString("student_id"),
                            rs.getString("grade"),
                            rs.getString("dob"),
                            rs.getString("gender"),
                            rs.getString("contact"),
                            rs.getString("email")
                    });
                }

                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No matching students found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to search students: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        tfName.setText("");
        tfID.setText("");
        tfGrade.setText("");
        tfDOB.setText("");
        genderGroup.clearSelection();
        tfContact.setText("");
        tfEmail.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) addStudent();
        if (e.getSource() == btnUpdate) updateStudent();
        if (e.getSource() == btnDelete) deleteStudent();
        if (e.getSource() == btnSearch) searchStudent();
        if (e.getSource() == btnReset) resetFields();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}

