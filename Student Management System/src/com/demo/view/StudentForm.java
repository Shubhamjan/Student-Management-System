package com.demo.view;

import javax.swing.*;
import com.demo.service.MyConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentForm extends JFrame {

    private JLabel statusLabel;

    public StudentForm() {
        setTitle("Student Form");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(70, 130, 180)); 

     
        JLabel headerLabel = new JLabel("Student Registration", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(255, 215, 0)); 
        add(headerLabel, BorderLayout.NORTH);

       
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        formPanel.setBackground(new Color(70, 130, 180)); 
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel rollNoLabel = new JLabel("Roll No:");
        rollNoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField rollNoField = new JTextField();
        rollNoField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(rollNoLabel);
        formPanel.add(rollNoField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(70, 130, 180)); 

        JButton addButton = createStyledButton("Add");
        JButton backButton = createStyledButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(new Color(0, 128, 0)); 

        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH);

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int rollNo = Integer.parseInt(rollNoField.getText());
                    String name = nameField.getText();
                    String address = addressField.getText();

                    
                    Connection con = MyConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("INSERT INTO student (rollNo, name, address) VALUES (?, ?, ?)");
                    ps.setInt(1, rollNo);
                    ps.setString(2, name);
                    ps.setString(3, address);

                    int c = ps.executeUpdate();
                    if (c > 0) {
                        statusLabel.setText("Student added successfully!");
                        statusLabel.setForeground(Color.WHITE); 
                    } else {
                        statusLabel.setText("Failed to add student.");
                        statusLabel.setForeground(Color.RED);
                    }

                    
                    rollNoField.setText("");
                    nameField.setText("");
                    addressField.setText("");

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
                StudentForm.this.dispose(); 
            }
        });
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
        new StudentForm().setVisible(true);
    }
}
