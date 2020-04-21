package com.chowder;

import com.chowder.Components.AppControls;
import com.chowder.Components.AppControlsComponent;
import com.chowder.Components.AppDisplayComponent;
import com.chowder.Components.AppFrameComponent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class AutoClicker {

    final private static String TITLE = "Bakery";

    private static List<Component> toggles = new ArrayList<>();

    final private static Controller controller = new Controller();

    public static void main(String[] args) {
        // Setup our theme
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppFrameComponent frame = new AppFrameComponent(TITLE);
		AppDisplayComponent display = new AppDisplayComponent();
		AppControlsComponent controls = new AppControlsComponent();

        //Creating bottom component
        JPanel bottom = createControlsPanel();

        //Adding the components to the frame.
        frame.add(display);
        frame.add(bottom);

		controller.setDisplayList(display.list);
		controller.setIntervals(display.listModel);

        // View the frame
		frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createControlsPanel() {
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        panel.add(intervalsPanel());
        panel.add(settingsPanel());
        panel.add(startStopPanel());
        panel.add(consolePanel());
        return panel;
    }

    private static JPanel intervalsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Intervals"));

		// Add text field
		JTextField tf = new JTextField(5); // accepts up to 5 characters
		tf.addActionListener(e -> {
			controller.addInterval(tf.getText());
			tf.setText("");
		});
		toggles.add(tf);
		panel.add(tf);

		// Add add button
		JButton add = new JButton("Add");
		add.addActionListener(e -> {
			controller.addInterval(tf.getText());
			tf.setText("");
		});
		toggles.add(add);
		panel.add(add);

        // Add remove button
        JButton remove = new JButton("Remove");
        remove.addActionListener(e -> controller.remove());
        toggles.add(remove);
        panel.add(remove);

        // Add reset button
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> controller.clear());
        toggles.add(clear);
        panel.add(clear);
        return panel;
    }

    private static JPanel settingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Settings"));

        // Add randomisation checkbox
        JCheckBox randomToggle = new JCheckBox("Random");
        randomToggle.addActionListener(e -> controller.setGaussian(randomToggle.isSelected()));
        panel.add(randomToggle);

        // Add cyclic checkbox
        JCheckBox cyclicToggle = new JCheckBox("Repeat");
        cyclicToggle.addActionListener(e -> controller.setCyclic(cyclicToggle.isSelected()));
        panel.add(cyclicToggle);

        // Add it to our things to disable
        toggles.add(randomToggle);
        toggles.add(cyclicToggle);
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

    private static JComponent consolePanel() {
        JPanel panel = new JPanel();
        JLabel console = new JLabel("Application started");
        controller.setConsole(console);
        panel.add(console);
        return panel;
    }

    private static void disableButtons() {
        toggles.forEach(component -> component.setEnabled(false));
    }

    private static void enableButtons() {
        toggles.forEach(component -> component.setEnabled(true));
    }
}
