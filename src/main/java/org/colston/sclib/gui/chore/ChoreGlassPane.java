package org.colston.sclib.gui.chore;

import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class ChoreGlassPane extends JPanel
{
	public ChoreGlassPane()
	{
		setOpaque(false);
		
		//capture all mouse activity
		MouseAdapter l = new MouseAdapter()	{};
		addMouseListener(l);
		addMouseMotionListener(l);
		addMouseWheelListener(l);
		
		//... and all keyboard activity
		Toolkit.getDefaultToolkit().addAWTEventListener(e -> ((InputEvent) e).consume(), AWTEvent.KEY_EVENT_MASK);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
