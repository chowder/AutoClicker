package com.chowder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Clicker implements Runnable
{
	final private static long MOUSE_DELAY = 100;  // milliseconds
	final private static float GAUSSIAN_FACTOR = 0.1f;
	private Robot clickEngine = null;

	{
		try
		{
			clickEngine = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	private final List<Interval> intervals;
	private final boolean random;
	private final boolean cyclic;
	private final Consumer<String> console;

	Clicker(List<Interval> intervals, boolean random, boolean cyclic, Consumer<String> console)
	{
		this.intervals = intervals;
		this.random = random;
		this.cyclic = cyclic;
		this.console = console;
	}

	private static long getRandom(float mean)
	{
		Random r = new Random();
		return (long) Math.abs(r.nextGaussian() * (mean * GAUSSIAN_FACTOR) + mean);
	}

	private void click() throws InterruptedException
	{
		clickEngine.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(getRandom(MOUSE_DELAY));
		clickEngine.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	@Override
	public void run()
	{
		try
		{
			do
			{
				for (Interval interval : intervals)
				{
					long sleepTime = random ? getRandom(interval.value * 1000) : (long) interval.value * 1000;
					console.accept(String.format("Sleeping for %s seconds", (float) sleepTime / 1000));
					Thread.sleep(sleepTime);
					click();
				}
			} while (cyclic);

			console.accept("Click sequence complete");
		}
		catch (InterruptedException ignored)
		{
			// Do nothing
		}
		finally
		{
			onComplete();
		}
	}

	// To override in the controller
	void onComplete() { }
}
