package com.chowder.Components.Controls;

import com.chowder.Components.Abstract.AbstractToggleablePanel;
import com.chowder.Controller;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SettingsComponent extends AbstractToggleablePanel
{
	private final JCheckBox randomToggle;
	private final JCheckBox cyclicToggle;
	private final JTextField timeoutInput;

	public SettingsComponent(Controller controller)
	{
		setBorder(BorderFactory.createTitledBorder("Settings"));
		randomToggle = new JCheckBox("Random");
		cyclicToggle = new JCheckBox("Repeat");
		timeoutInput = new JTextField(4);
		timeoutInput.setColumns(4);
		randomToggle.setSelected(true);
		cyclicToggle.setSelected(true);

		add(new JLabel("Timeout:"));
		add(timeoutInput);
		add(randomToggle);
		add(cyclicToggle);

		setController(controller);
	}

	private void setController(Controller controller)
	{
		randomToggle.addActionListener(e -> controller.setGaussian(randomToggle.isSelected()));
		cyclicToggle.addActionListener(e -> controller.setCyclic(cyclicToggle.isSelected()));
		timeoutInput.addActionListener(e -> {
			controller.setTimeout(timeoutInput.getText());
			timeoutInput.setText("");
		});
	}
}
