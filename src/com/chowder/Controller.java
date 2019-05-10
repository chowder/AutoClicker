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
    private boolean gaussian = false;
    private boolean cyclic = false;

    void addInterval(String interval) {
        if (interval.length() > 0) {
            try {
                intervals.add(Float.parseFloat(interval));
            } catch (NumberFormatException e) {
                console.setText("Invalid input given.");
                return;
            }
            console.setText("Added new interval");
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

    boolean start() {
        if (intervals.size() <= 0) {
            console.setText("No intervals given");
            return false;
        }
        try {
            clicker = new Clicker(intervals, console, display, gaussian, cyclic);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        clicker.start();
        return true;
    }

    void setGaussian(boolean gaussian) {
        console.setText("Random mode " + (gaussian ? "enabled" : "disabled"));
        this.gaussian = gaussian;
    }

    void setCyclic(boolean cyclic) {
        console.setText("Cyclic mode " + (cyclic ? "enabled" : "disabled"));
        this.cyclic = cyclic;
    }
}
