package com.chowder.Components.Controls;

import com.chowder.Components.Abstract.AbstractToggleablePanel;
import com.chowder.Controller;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class IntervalsComponent extends AbstractToggleablePanel
{
	private final JTextField input;
	private final JButton addButton;
	private final JButton removeButton;
	private final JButton clearButton;

	public IntervalsComponent(Controller controller)
	{
		setBorder(BorderFactory.createTitledBorder("Intervals"));

		// Setup text input field
		this.input = new JTextField(5);
		this.addButton = new JButton("Add");
		this.removeButton = new JButton("Remove");
		this.clearButton = new JButton("Clear");

		add(this.input);
		add(this.addButton);
		add(this.removeButton);
		add(this.clearButton);

		setController(controller);
	}

	private void setController(Controller controller)
	{
		addButton.addActionListener(e -> {
			controller.addInterval(input.getText());
			input.setText("");
		});

		input.addActionListener(e -> {
			controller.addInterval(input.getText());
			input.setText("");
		});

		removeButton.addActionListener(e -> controller.remove());
		clearButton.addActionListener(e -> controller.clear());
	}
}
