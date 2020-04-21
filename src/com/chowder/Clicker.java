package com.chowder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

class Clicker extends Thread
{
	final private static long MOUSE_DELAY = 100;  // milliseconds
	final private static float GAUSSIAN_FACTOR = 0.1f;
	final private Robot clickEngine = new Robot();
	private final float timeout;
	private final JLabel console;
	private final JList<Interval> list;
	private boolean gaussian;
	private boolean cyclic;
	private DefaultListModel<Interval> intervals;

	Clicker(DefaultListModel<Interval> intervals, JLabel console, JList<Interval> list, boolean gaussian, boolean cyclic, float timeout)
		throws AWTException
	{
		this.intervals = intervals;
		this.timeout = timeout;
		this.console = console;
		this.list = list;
		this.gaussian = gaussian;
		this.cyclic = cyclic;
	}

	private static long getRandom(long mean)
	{
		Random r = new Random();
		return (long) Math.abs(r.nextGaussian() * (mean * GAUSSIAN_FACTOR) + mean);
	}

	public void run()
	{
		Instant start = Instant.now();
		try
		{
			console.setText("Clicker thread is running...");
			while (true)
			{
				for (int i = 0; i < intervals.size(); i++)
				{
					if (timeoutReached(start))
					{
						console.setText("Clicker thread stopped, timeout reached");
						return;
					}
					highlightRow(i);
					long sleep_time = getSleepTime(i);
					console.setText(String.format("Sleeping for %ss", sleep_time / 1000));
					Thread.sleep(sleep_time);
					click();
				}
				if (!cyclic)
				{
					console.setText("Click sequence completed");
					return;
				}
			}
		}
		catch (InterruptedException e)
		{
			console.setText("Clicker thread stopped");
		}
	}

	private long getSleepTime(int i)
	{
		long sleep_time = (long) (intervals.get(i).value * 1000);
		if (gaussian)
		{
			sleep_time = getRandom(sleep_time);
		}
		return sleep_time;
	}

	private boolean timeoutReached(Instant start)
	{
		return timeout > 0 && Duration.between(start, Instant.now()).toMinutes() > timeout;
	}

	private void click() throws InterruptedException
	{
		clickEngine.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(getRandom(MOUSE_DELAY));
		clickEngine.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	private void highlightRow(int i)
	{
		list.setSelectedIndex(i);
	}
}
