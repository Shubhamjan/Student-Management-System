package com.demo.view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

import com.demo.service.MyConnection;

public class ShowAllStudents extends JFrame {

    public ShowAllStudents() {
        setTitle("Show All Students");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        getContentPane().setBackground(new Color(70, 130, 180)); 

        
        JLabel headerLabel = new JLabel("Student Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 28));
        headerLabel.setForeground(new Color(255, 215, 0)); 
        add(headerLabel, BorderLayout.NORTH);

       
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(70, 130, 180));

        
        String[] columnNames = {"Roll No", "Name", "Address"};

        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        
        JTable studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        studentTable.getTableHeader().setBackground(new Color(34, 139, 34)); // Forest Green
        studentTable.getTableHeader().setForeground(Color.WHITE);

        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        
        add(tablePanel, BorderLayout.CENTER);

        
        loadStudentData(tableModel);

       
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(34, 139, 34)); 
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.addActionListener(e -> {
            new MainPage().setVisible(true);
            ShowAllStudents.this.dispose();
        });

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(70, 130, 180)); 
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    
    private void loadStudentData(DefaultTableModel tableModel) {
        Connection con = MyConnection.getConnection();
        try {
            String query = "SELECT * FROM student";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int rollNo = rs.getInt("rollNo");
                String name = rs.getString("name");
                String address = rs.getString("address");

                
                tableModel.addRow(new Object[]{rollNo, name, address});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ShowAllStudents().setVisible(true);
    }
}
