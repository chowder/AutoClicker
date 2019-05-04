package com.chowder;

import java.awt.*;

public class AutoClicker {

    public static void main(String[] args) throws AWTException, InterruptedException {
        Clicker clicker = Clicker.getInstance();
        clicker.click();
    }
}
