package com.chowder;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Random;

class Clicker extends Thread {

    final private Robot clickEngine = new Robot();
    final private static long MOUSE_DELAY = 100;  // milliseconds
    final private static float GAUSSIAN_FACTOR = 0.1f;

    private boolean gaussian;
    private boolean cyclic;
    private List<Float> intervals;
    private final JLabel console;
    private final JTextArea display;

    Clicker(List<Float> intervals, JLabel console, JTextArea display, boolean gaussian, boolean cyclic)
            throws AWTException {
        this.intervals = intervals;
        this.console = console;
        this.display = display;
        this.gaussian = gaussian;
        this.cyclic = cyclic;
    }

    public void run() {
        try {
            console.setText("Clicker thread is running...");
            for (int i = 0; i < intervals.size(); i++) {
                highlightRow(i);

                // Get sleep time
                long sleep_time = intervals.get(i).longValue() * 1000;
                if (gaussian) sleep_time = getRandom(sleep_time);

                // Sleep thread
                console.setText("Sleeping for " + (float) sleep_time / 1000 + "s");
                Thread.sleep(sleep_time);

                // Click
                click();

                // Reset `i` if cyclic
                if (cyclic && i == intervals.size() - 1) i = -1;
            }
        } catch (InterruptedException e) {
            console.setText("Clicker thread stopped");
            return;
        }
        console.setText("Click sequence completed");
    }

    private void click() throws InterruptedException {
        clickEngine.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(getRandom(MOUSE_DELAY));
        clickEngine.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static long getRandom(long mean) {
        Random r = new Random();
        return (long)Math.abs(r.nextGaussian() * (mean * GAUSSIAN_FACTOR) + mean);
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