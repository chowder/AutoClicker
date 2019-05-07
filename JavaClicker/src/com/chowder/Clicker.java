package com.chowder;

import javax.naming.ldap.Control;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

class Clicker extends Thread {

    final private Robot clickEngine = new Robot();
    final private static long MOUSE_DELAY = 50;  // milliseconds
    final private static long GAUSSIAN_FACTOR = 2;

    private boolean _gaussianMode = false;
    private List<Float> intervals;
    private Controller controller;


    Clicker(List<Float> intervals, Controller controller) throws AWTException {
        this.intervals = intervals;
        this.controller = controller;
    }

    public void run() {
        System.out.println("Clicker thread is running...");
        for (int i = 0; i < intervals.size(); i++) {
            controller.highlightRow(i);
            try {
                Thread.sleep(intervals.get(i).longValue() * 1000);
                click();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    void click() throws InterruptedException {
        clickEngine.mousePress(InputEvent.BUTTON1_MASK);
        if (_gaussianMode) {
            Thread.sleep(getRandom(MOUSE_DELAY));
        } else {
            Thread.sleep(MOUSE_DELAY);
        }
        clickEngine.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private static long getRandom(long mean) {
        Random r = new Random();
        return (long)r.nextGaussian() * GAUSSIAN_FACTOR + mean;
    }
}
