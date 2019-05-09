package com.chowder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Random;

class Clicker extends Thread {

    final private Robot clickEngine = new Robot();
    final private static long MOUSE_DELAY = 20;  // milliseconds
    final private static long GAUSSIAN_FACTOR = 2;

    private boolean _gaussianMode = false;
    private List<Float> intervals;
    private final JLabel console;
    private final JTextArea display;

    Clicker(List<Float> intervals, JLabel console, JTextArea display) throws AWTException {
        this.intervals = intervals;
        this.console = console;
        this.display = display;
    }

    public void run() {
        if (intervals.size() != 0) {
            console.setText("Clicker thread is running...");
            try {
                for (int i = 0; i < intervals.size(); i++) {
                    highlightRow(i);
                    long sleep_time = getRandom(intervals.get(i).longValue() * 1000);
                    Thread.sleep(sleep_time);
                    click();
                }
            } catch (InterruptedException e) {
                console.setText("Clicker thread stopped");
            }
        } else {
            console.setText("Add some intervals before starting");
        }
    }

    private void click() throws InterruptedException {
        clickEngine.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(getRandom(MOUSE_DELAY));
        clickEngine.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static long getRandom(long mean) {
        Random r = new Random();
        return Math.abs((long)r.nextGaussian() * GAUSSIAN_FACTOR + mean);
    }

    private void highlightRow(int i) {
        try {
            int start = display.getLineStartOffset(i);
            int end = display.getLineEndOffset(i);
            display.requestFocus();
            display.setSelectionStart(start);
            display.setSelectionEnd(end);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
