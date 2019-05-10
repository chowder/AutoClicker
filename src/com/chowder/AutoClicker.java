package com.chowder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AutoClicker {

    final private static String TITLE = "Autoclicker";
    final private static int WINDOW_WIDTH = 300;
    final private static int WINDOW_HEIGHT = 475;
    final private static String MONOSPACE_FONT = "Courier New";
    final private static int FONT_SIZE = 12;

    private static List<Component> toggleables = new ArrayList<Component>();
    private static JLabel console;

    final private static Controller controller = new Controller();

    public static void main(String args[]) {
        // Setup our theme
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Creating the Frame
        JFrame frame = new JFrame();
        setupFrame(frame);

        //Creating the panel at bottom
        JPanel bottom_panel = new JPanel();
        setupBottomPanel(bottom_panel);

        //Creating display area
        JScrollPane display = new JScrollPane();
        setupDisplayPanel(display);

        //Adding the components to the frame.
        frame.add(bottom_panel, BorderLayout.SOUTH);
        frame.add(display, BorderLayout.CENTER);

        // View the frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void setupFrame(JFrame frame) {
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        frame.setIconImage(icon);
        frame.setTitle(TITLE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
    }

    private static void setupDisplayPanel(JScrollPane panel) {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setMargin(new Insets(10, 10, 10, 10));
        ta.setFont(new Font(MONOSPACE_FONT, Font.PLAIN, FONT_SIZE));
        ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedItem(ta);
            }
        });
        controller.setDisplay(ta);
        panel.setViewportView(ta);
    }

    private static void setupBottomPanel(JPanel main_panel) {
        main_panel.setLayout(new GridLayout(0, 1));
        main_panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        main_panel.add(intervalPanel());
        main_panel.add(settingsPanel());
        main_panel.add(startStopPanel());
        main_panel.add(consolePanel());
    }

    private static JPanel intervalPanel() {
        JPanel panel = new JPanel();

        // Add field description
        panel.add(new JLabel("New Interval"));

        // Add text field
        JTextField tf = new JTextField(5); // accepts up to 5 characters
        tf.addActionListener(e -> {
            controller.addInterval(tf.getText());
            tf.setText("");
        });
        toggleables.add(tf);
        panel.add(tf);

        // Add add button
        JButton add = new JButton("Add");
        add.addActionListener(e -> {
            controller.addInterval(tf.getText());
            tf.setText("");
        });
        toggleables.add(add);
        panel.add(add);

        // Add reset button
        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> controller.reset());
        toggleables.add(reset);
        panel.add(reset);
        return panel;
    }

    private static JPanel settingsPanel() {
        JPanel panel = new JPanel();

        // Add randomisation checkbox
        JCheckBox randomToggle = new JCheckBox("Randomise");
        randomToggle.addActionListener(e -> {
            controller.setGaussian(randomToggle.isSelected());
        });
        panel.add(randomToggle);

        // Add cyclic checkbox
        JCheckBox cyclicToggle = new JCheckBox("Repeat");
        cyclicToggle.addActionListener(e -> {
            controller.setCyclic(cyclicToggle.isSelected());
        });
        panel.add(cyclicToggle);

        // Add it to our things to disable
        toggleables.add(randomToggle);
        toggleables.add(cyclicToggle);
        return panel;
    }

    private static JPanel startStopPanel() {
        JPanel panel = new JPanel();
        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            if (start.getText().compareTo("Start") == 0) {
                if (controller.start()) {
                    start.setText("Stop");
                    disableButtons();
                }
            }
            else if (start.getText().compareTo("Stop") == 0) {
                start.setText("Start");
                controller.stop();
                enableButtons();
            }
        });
        panel.add(start);
        return panel;
    }

    private static JPanel consolePanel() {
        JPanel panel = new JPanel();
        console = new JLabel();
        controller.setConsole(console);
        panel.add(console);
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

    private static void disableButtons() {
        toggleables.forEach(component -> component.setEnabled(false));
    }

    private static void enableButtons() {
        toggleables.forEach(component -> component.setEnabled(true));
    }
}