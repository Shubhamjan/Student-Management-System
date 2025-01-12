package com.demo.view;

import javax.swing.*;
import com.demo.service.MyConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateStudent extends JFrame {

    private JTextField rollNoField;
    private JTextField nameField;
    private JTextField addressField;
    private JLabel statusLabel;

    public UpdateStudent() {
        setTitle("Update Student");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(70, 130, 180));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(new Color(70, 130, 180)); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel rollNoLabel = new JLabel("Roll No:");
        rollNoField = new JTextField();
        rollNoField.setEditable(false);
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        formPanel.add(rollNoLabel);
        formPanel.add(rollNoField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(70, 130, 180)); 

        JButton updateButton = createStyledButton("Update");
        JButton backButton = createStyledButton("Back");

        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(Color.BLUE);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);  

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchPanel.setBackground(new Color(70, 130, 180)); 
        JLabel searchLabel = new JLabel("Enter Roll No to Update:");
        JTextField searchRollNoField = new JTextField(10);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchRollNoField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);  

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNoText = searchRollNoField.getText().trim();
                if (!rollNoText.isEmpty()) {
                    try {
                        int rollNo = Integer.parseInt(rollNoText);
                        populateFields(rollNo);
                    } catch (NumberFormatException ex) {
                        statusLabel.setText("Invalid Roll No. Please enter a numeric value.");
                        statusLabel.setForeground(Color.RED);
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int rollNo = Integer.parseInt(rollNoField.getText());
                    String name = nameField.getText();
                    String address = addressField.getText();

                    updateStudent(rollNo, name, address);
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid Roll No. Please enter a numeric value.");
                    statusLabel.setForeground(Color.RED);
                } catch (SQLException ex) {
                    statusLabel.setText("Database error: " + ex.getMessage());
                    statusLabel.setForeground(Color.RED);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage().setVisible(true);
                UpdateStudent.this.dispose();
            }
        });
    }

    private void updateStudent(int rollNo, String name, String address) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE student SET name = ?, address = ? WHERE rollNo = ?");
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setInt(3, rollNo);

        int c = ps.executeUpdate();
        System.out.println("row affected :-" + c);
        if (c > 0) {
            statusLabel.setText("Student updated successfully!");
            statusLabel.setForeground(Color.white);
        } else {
            statusLabel.setText("Failed to update student.");
            statusLabel.setForeground(Color.RED);
        }

        rollNoField.setText("");
        nameField.setText("");
        addressField.setText("");
    }

    private void populateFields(int rollNo) {
        try {
            Connection con = MyConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name, address FROM student WHERE rollNo = ?");
            ps.setInt(1, rollNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rollNoField.setText(String.valueOf(rollNo));
                nameField.setText(rs.getString("name"));
                addressField.setText(rs.getString("address"));
            } else {
                statusLabel.setText("No student found with this Roll No.");
                statusLabel.setForeground(Color.RED);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            statusLabel.setText("Error fetching student data.");
            statusLabel.setForeground(Color.RED);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
        return button;
    }

    public static void main(String[] args) {
        new UpdateStudent().setVisible(true);
    }
}
