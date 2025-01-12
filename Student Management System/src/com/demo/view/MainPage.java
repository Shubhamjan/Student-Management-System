package com.demo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Main Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color for the content pane
        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue background

        // Welcome text with enhanced styling
        JLabel titleLabel = new JLabel("Welcome to the Main Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 24));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold text color
        add(titleLabel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBackground(new Color(70, 130, 180)); // Match background with the main frame

        // Buttons
        JButton insertButton = createStyledButton("Insert");
        JButton updateButton = createStyledButton("Update");
        JButton deleteButton = createStyledButton("Delete");
        JButton showButton = createStyledButton("Show");

        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        insertButton.addActionListener(e -> {
            new StudentForm().setVisible(true);
            MainPage.this.dispose();
        });

        updateButton.addActionListener(e -> {
            new UpdateStudent().setVisible(true);
            MainPage.this.dispose();
        });

        deleteButton.addActionListener(e -> {
            new ShowAllStudentsWithDelete().setVisible(true);
            MainPage.this.dispose();
        });

        showButton.addActionListener(e -> {
            new ShowAllStudents().setVisible(true);
            MainPage.this.dispose();
        });
    }

    /**
     * Creates a styled JButton with consistent design.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(34, 139, 34)); // Forest Green background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add white border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        return button;
    }

    public static void main(String[] args) {
        new MainPage().setVisible(true);
    }
}
