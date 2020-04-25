package com.chowder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class Controller
{
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
	private Future<?> currentClicker;

	private DefaultListModel<Interval> intervals;
	private JList<Interval> list;

	private boolean cyclic = true;
	private boolean gaussian = true;
	private float timeout = -1;


	private Consumer<String> consoleCallback;
	private Consumer<Boolean> enableControlsCallback;
	private BiConsumer<String, Object> updateParameter;

	public void setDisplayList(JList<Interval> list)
	{
		this.list = list;
	}

	public void setIntervals(DefaultListModel<Interval> intervals)
	{
		this.intervals = intervals;
	}

	public void setConsoleCallback(Consumer<String> callback)
	{
		consoleCallback = callback;
	}

	public void setEnableControlsCallback(Consumer<Boolean> callback)
	{
		enableControlsCallback = callback;
	}

	public void setUpdateParameterCallback(BiConsumer<String, Object> callback)
	{
		updateParameter = callback;
	}

	public void setGaussian(boolean gaussian)
	{
		consoleCallback.accept(String.format("Random mode %s", gaussian ? "enabled" : "disabled"));
		this.gaussian = gaussian;
	}

	public void setCyclic(boolean cyclic)
	{
		consoleCallback.accept(String.format("Cyclic mode %s", cyclic ? "enabled" : "disabled"));
		this.cyclic = cyclic;
	}

	public void setTimeout(String timeout)
	{
		if (timeout.length() > 0)
		{
			try
			{
				float value = Float.parseFloat(timeout);
				if (value <= 0)
				{
					this.timeout = -1;
					consoleCallback.accept("Timeout disabled");
					updateParameter.accept("Timeout", "Disabled");
				}
				else
				{
					this.timeout = value;
					consoleCallback.accept(String.format("Timeout after %s minutes", this.timeout));
					updateParameter.accept("Timeout", this.timeout);
				}
			}
			catch (NumberFormatException e)
			{
				consoleCallback.accept("Invalid timeout value given");
			}
		}
	}

	public void addInterval(String interval)
	{
		if (interval.length() > 0)
		{
			try
			{
				float value = Float.parseFloat(interval);
				intervals.addElement(new Interval(value));
				consoleCallback.accept("Added new interval");
			}
			catch (NumberFormatException e)
			{
				consoleCallback.accept("Invalid input given.");
			}
		}
	}

	public void clear()
	{
		intervals.clear();
		timeout = -1;
		consoleCallback.accept("Cleared all intervals");
	}

	public boolean start()
	{
		if (intervals.isEmpty())
		{
			consoleCallback.accept("No intervals given");
			return false;
		}
		List<Interval> intervalList = Collections.list(intervals.elements());
		Clicker clicker = new Clicker(intervalList, gaussian, cyclic, consoleCallback) {
			@Override
			void onComplete()
			{
				super.onComplete();
				enableControlsCallback.accept(true);
			}
		};
		currentClicker = executorService.submit(clicker);
		if (timeout > 0)
		{
			executorService.schedule(() -> {
				currentClicker.cancel(true);
				consoleCallback.accept("Clicker thread stopped, timeout reached");
			}, (long) (timeout * 60), TimeUnit.SECONDS);
		}
		return true;
	}

	public void stop()
	{
		currentClicker.cancel(true);
		consoleCallback.accept("Clicker thread interrupted");
	}

	public void remove()
	{
		List<Interval> selected = list.getSelectedValuesList();
		for (Interval i : selected)
		{
			intervals.removeElement(i);
			consoleCallback.accept(String.format("Removed interval: %ss", i.value));
		}
	}
}
