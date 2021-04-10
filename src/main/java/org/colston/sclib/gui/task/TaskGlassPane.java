package org.colston.sclib.gui.task;

import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

/**
 * Glass pane that is put on a {@link javax.swing.RootPaneContainer} while a background chore is executing to stop any
 * mouse or keyboard input from the user.
 */
public class TaskGlassPane extends JPanel
{
	public TaskGlassPane()
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
