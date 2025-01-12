package com.demo.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.demo.service.StudentService;

public class AdminLogin extends JFrame implements ActionListener {

    JLabel lblusername, lblpassword;
    JTextField txtusername;
    JPasswordField txtpassword;
    JButton btnlogin;
    Container cp;

    StudentService sv = new StudentService();

    AdminLogin() {

       
        lblusername = new JLabel("Enter your username ");
        lblusername.setBounds(172, 118, 200, 30); 
        lblusername.setFont(new Font("Arial", Font.BOLD, 14));
        lblusername.setForeground(Color.WHITE);

        lblpassword = new JLabel("Enter your password ");
        lblpassword.setBounds(172, 183, 200, 30); 
        lblpassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblpassword.setForeground(Color.WHITE);

        txtusername = new JTextField(30);
        txtusername.setBounds(332, 118, 150, 30);

        txtpassword = new JPasswordField(30);
        txtpassword.setBounds(332, 183, 150, 30);

        btnlogin = new JButton("Login");
        btnlogin.setBounds(235, 244, 80, 30);
        btnlogin.setBackground(new Color(34, 139, 34));
        btnlogin.setForeground(Color.WHITE);

        cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(new Color(70, 130, 180)); 

        cp.add(lblusername);
        cp.add(txtusername);
        cp.add(lblpassword);
        cp.add(txtpassword);
        cp.add(btnlogin);

        btnlogin.addActionListener(this);

        setSize(563, 388);
        setVisible(true);
        setTitle("Login Frame ");
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new AdminLogin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String un = txtusername.getText();
        String pwd = txtpassword.getText();
        try {
            if (un.equals(sv.getCredential().getUsername()) && pwd.equals(sv.getCredential().getPassword())) {
                System.out.println("Hello! Your login is successful!");

                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(txtusername);
                loginFrame.dispose();

                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
            } else {
                System.out.println("Sorry! Wrong username or password.");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
