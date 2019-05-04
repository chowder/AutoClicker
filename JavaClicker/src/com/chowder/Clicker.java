package com.chowder;

import java.awt.*;
import java.awt.event.InputEvent;

public class Clicker {

    static private Clicker instance;

    final private Robot clickEngine;

    final private float MOUSE_DELAY = 50;  // milliseconds
    final private float GAUSSIAN_FACTOR = 2;  // stddev

    private Clicker() throws AWTException {
        this.clickEngine = new Robot();
    }

    public static Clicker getInstance() throws AWTException {
        if (instance == null) {
            instance = new Clicker();
        }
        return instance;
    }

    public void click() throws InterruptedException {
        clickEngine.mousePress(InputEvent.BUTTON1_MASK);
        Thread.sleep(50);
        clickEngine.mouseRelease( InputEvent.BUTTON1_MASK );
    }
}
