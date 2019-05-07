package com.chowder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AutoClicker {

    final private static String TITLE = "Autoclicker";
    final private static int WINDOW_WIDTH = 300;
    final private static int WINDOW_HEIGHT = 400;
    final private static String MONOSPACE_FONT = "Courier New";
    final private static int FONT_SIZE = 12;

    final private static Controller controller = new Controller();
    
    public static void main(String args[]) {
        // Setup our theme
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Creating the Frame
        JFrame frame = new JFrame(TITLE);
        setupFrame(frame);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel();
        setupPanel(panel);

        //Creating main console area
        JScrollPane scroll_pane = new JScrollPane();
        setupScrollPane(scroll_pane);

        //Adding Components to the frame.
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(scroll_pane, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void setupFrame(JFrame frame) {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
    }

    private static void setupScrollPane(JScrollPane scroll_pane) {
        JTextArea ta = new JTextArea();
        setupTextArea(ta);
        scroll_pane.setViewportView(ta);
    }

    private static void setupTextArea(JTextArea ta) {
        ta.setEditable(false);
        ta.setMargin(new Insets(10, 10, 10, 10));
        ta.setFont(new Font(MONOSPACE_FONT, Font.PLAIN, FONT_SIZE));

        ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedItem(ta);
            }
        });
        controller.setConsole(ta);
    }

    private static void setupPanel(JPanel main_panel) {
        main_panel.setLayout(new GridLayout(0, 1));
        main_panel.add(intervalPanel());
        main_panel.add(startStopPanel());
    }

    private static JPanel intervalPanel() {
        JPanel panel = new JPanel();

        // Add field description
        panel.add(new JLabel("New Interval"));

        // Add text field
        JTextField tf = new JTextField(5); // accepts up to 5 characters
        panel.add(tf);

        // Add add button
        JButton add = new JButton("Add");
        add.addActionListener(e -> {
            controller.addInterval(tf.getText());
            tf.setText("");
        });
        panel.add(add);

        // Add reset button
        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> controller.reset());
        panel.add(reset);
        return panel;
    }

    private static JPanel startStopPanel() {
        JPanel panel = new JPanel();
        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            if (start.getText().compareTo("Start") == 0) {
                start.setText("Stop");
                controller.start();
            }
            else {
                start.setText("Start");
                controller.stop();
            }
        });
        panel.add(start);
        return panel;
    }

    private static void selectedItem(JTextArea ta) {
        int pos = ta.getCaretPosition();
        try {
            int start = Utilities.getRowStart(ta, pos);
            int end = Utilities.getRowEnd(ta, pos);
            ta.setSelectionStart(start);
            ta.setSelectionEnd(end);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
