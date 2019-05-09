package com.chowder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Controller {

    private List<Float> intervals = new ArrayList<>();
    private JTextArea display;
    private JLabel console;
    private Clicker clicker;

    void addInterval(String interval) {
        if (interval.length() > 0) {
            try {
                intervals.add(Float.parseFloat(interval));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input given.");
                return;
            }
            display.append(String.format("Wait %s seconds - Click\n", interval));
        }
    }

    void setDisplay(JTextArea display) {
        this.display = display;
    }

    void setConsole(JLabel console) {
        this.console = console;
    }

    void reset() {
        intervals.clear();
        display.setText("");
    }

    void stop() {
        clicker.interrupt();
    }

    void start() {
        try {
            clicker = new Clicker(intervals, console, display);
            clicker.start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
