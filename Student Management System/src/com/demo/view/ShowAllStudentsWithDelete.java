package com.demo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.demo.service.MyConnection;

public class ShowAllStudentsWithDelete extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public ShowAllStudentsWithDelete() {
        setTitle("Show All Students");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame

        // Set background color for the frame
        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue background

        // Header label with styling
        JLabel headerLabel = new JLabel("Student Records", JLabel.CENTER);
        headerLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 28));
        headerLabel.setForeground(new Color(255, 215, 0)); // Gold text color
        add(headerLabel, BorderLayout.NORTH);

        // Table with custom model
        tableModel = new DefaultTableModel(new Object[]{"Roll No", "Name", "Address", "Action"}, 0);
        studentTable = new JTable(tableModel) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return JButton.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Make only the 'Delete' column editable
            }
        };

        // Load student data
        loadStudentData();

        // Set custom cell editor for delete buttons
        studentTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Student table customization
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        studentTable.getTableHeader().setBackground(new Color(34, 139, 34)); // Forest Green
        studentTable.getTableHeader().setForeground(Color.WHITE);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Back button with styling
        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(34, 139, 34)); // Forest Green
        backButton.addActionListener(e -> {
            new MainPage().setVisible(true);
            ShowAllStudentsWithDelete.this.dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        bottomPanel.setBackground(new Color(70, 130, 180)); // Match frame background
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadStudentData() {
        tableModel.setRowCount(0);

        try (Connection con = MyConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

            while (rs.next()) {
                int rollNo = rs.getInt("rollNo");
                String name = rs.getString("name");
                String address = rs.getString("address");

                Object[] rowData = {rollNo, name, address, "Delete"};
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isClicked;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            // Set button color to green
            button.setBackground(new Color(34, 139, 34)); // Forest Green
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));

            button.addActionListener(e -> {
                int row = studentTable.getSelectedRow();
                if (row >= 0) {
                    int rollNo = (int) tableModel.getValueAt(row, 0);
                    deleteStudent(rollNo);
                    loadStudentData();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(value == null ? "" : value.toString());
            isClicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isClicked = false;
            return button.getText();
        }
    }

    private void deleteStudent(int rollNo) {
        try (Connection con = MyConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE rollNo = ?");
            ps.setInt(1, rollNo);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

    public static void main(String[] args) {
        new ShowAllStudentsWithDelete().setVisible(true);
    }
}
