package com.chowder.Components;

import com.chowder.Controller;
import com.chowder.Interval;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class AppDisplayComponent extends JScrollPane
{
	final private static String MONOSPACE_FONT = "Courier New";
	final private static int FONT_SIZE = 14;
	public final JList<Interval> list;
	public final DefaultListModel<Interval> listModel;

	public AppDisplayComponent(Controller controller)
	{
		DefaultListModel<Interval> listModel = new DefaultListModel<>();
		JList<Interval> list = new JList<>(listModel);
		list.setBorder(new EmptyBorder(10, 10, 10, 10));
		list.setFixedCellHeight(20);
		list.setFont(new Font(MONOSPACE_FONT, Font.PLAIN, FONT_SIZE));
		this.list = list;
		this.listModel = listModel;
		setViewportView(list);
		setController(controller);
	}

	private void setController(Controller controller)
	{
		controller.setDisplayList(list);
		controller.setIntervals(listModel);
	}
}
