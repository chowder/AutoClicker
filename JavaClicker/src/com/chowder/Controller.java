package com.chowder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Controller {

    private List<Float> intervals = new ArrayList<>();
    private JTextArea console;
    private Clicker clicker;

    void addInterval(String interval) {
        if (interval.length() > 0) {
            try {
                intervals.add(Float.parseFloat(interval));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input given.");
                return;
            }
            console.append(String.format("Wait %s seconds - Click\n", interval));
        }
    }

    void setConsole(JTextArea console) {
        this.console = console;
    }

    void reset() {
        intervals.clear();
        console.setText("");
    }

    void stop() {
        clicker.interrupt();
    }

    void start() {
        try {
            clicker = new Clicker(intervals, this);
            clicker.start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    void highlightRow(int i) {
        try {
            int start = console.getLineStartOffset(i);
            int end = console.getLineEndOffset(i);
            console.requestFocus();
            console.setSelectionStart(start);
            console.setSelectionEnd(end);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
