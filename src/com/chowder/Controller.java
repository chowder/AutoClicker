package com.chowder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class Controller {
    private JLabel console;
    private Clicker clicker;
    private boolean gaussian = false;
    private boolean cyclic = false;
	private DefaultListModel<Interval> intervals;
	private JList<Interval> list;

	void addInterval(String interval) {
        if (interval.length() > 0) {
            try {
            	float value = Float.parseFloat(interval);
                intervals.addElement(new Interval(value));
            } catch (NumberFormatException e) {
                console.setText("Invalid input given.");
                return;
            }
            console.setText("Added new interval");
        }
    }

	void setDisplayList(JList<Interval> list) {
		this.list = list;
	}

	void setIntervals(DefaultListModel<Interval> intervals)
	{
		this.intervals = intervals;
	}

    void setConsole(JLabel console) {
		this.console = console;
    }

    void clear() {
		intervals.clear();
        console.setText("Cleared all intervals");
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
            clicker = new Clicker(intervals, console, list, gaussian, cyclic);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        clicker.start();
        return true;
    }

    void setGaussian(boolean gaussian) {
        console.setText(String.format("Random mode %s", gaussian ? "enabled" : "disabled"));
        this.gaussian = gaussian;
    }

    void setCyclic(boolean cyclic) {
        console.setText(String.format("Cyclic mode %s", cyclic ? "enabled" : "disabled"));
        this.cyclic = cyclic;
    }

    void remove() {
		List<Interval> selected = list.getSelectedValuesList();
		for (Interval i: selected) {
			intervals.removeElement(i);
			console.setText(String.format("Removed interval: %ss", i.value));
		}
    }
}
