package com.demo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SplashWindow extends JFrame implements ActionListener{
	JLabel lblshow;
	JButton btnnext;
	
	public SplashWindow() {
		lblshow = new JLabel();
		lblshow.setHorizontalAlignment(SwingConstants.CENTER);
		lblshow.setBounds(5, 5, 520, 253);
		lblshow.setIcon(new ImageIcon("C:\\Users\\ASUS\\eclipse-workspace\\javaPractice\\Student Management System\\student-management-system.png"));
		btnnext = new JButton("Welcome");
		btnnext.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 28));
		btnnext.setBounds(195, 300, 143, 59);
		btnnext.setBackground(new Color(173, 216, 230)); // Light Blue
		getContentPane().setLayout(null);
		btnnext.setForeground(Color.WHITE);
		getContentPane().add(lblshow);
		getContentPane().add(btnnext);
		
		btnnext.addActionListener(this);
		
		setSize(551,546);
		setVisible(true);
		setTitle("My Student Project");
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new SplashWindow();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		new AdminLogin();
	}
	
}