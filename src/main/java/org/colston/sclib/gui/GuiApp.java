package org.colston.sclib.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public abstract class GuiApp
{
	private JFrame frame;
	private StatusPanel statusPanel;

	public void start()
	{
		
		// create the frame for the splash to cover
		frame = new JFrame(getApplicationName());

		// create and show the splash screen
		Icon icon = getSplashIcon();
		if (icon == null)
		{
			//TODO: make a default
		}
		final Splash splash = new Splash(frame,	icon);
		splash.showSplash();

		//give ourselves minimum time for splash screen
		long startMillis = System.currentTimeMillis();
		configure();
		createUI();

		// show the GUI and get rid of the splash
		EventQueue.invokeLater(() ->
		{
			frame.pack();
			frame.setLocationRelativeTo(null);

			long timeLeft = 3000 - (System.currentTimeMillis() - startMillis);
			if (timeLeft > 0)
			{
				//pause
				try
				{
					Thread.sleep(timeLeft);
				} catch (InterruptedException e) {}
			}
			// end pause
			frame.setVisible(true);
			getFocusComponent().requestFocusInWindow();
			splash.disposeSplash();
		});

	}

	protected void configure()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void createUI()
	{
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(getFrameWindowListener());
		frame.setIconImages(getFrameIconImages());
		
		statusPanel = new StatusPanel();

		frame.setJMenuBar(createMenuBar());
		JPanel contentPane = new JPanel(new BorderLayout());
		frame.setContentPane(contentPane);
		contentPane.add(createToolBar(), BorderLayout.NORTH);
		contentPane.add(createMainPanel(), BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setMessage(getApplicationName());
	}

	protected abstract List<? extends Image> getFrameIconImages();

	protected abstract WindowListener getFrameWindowListener();
	
	protected abstract JComponent getFocusComponent();

	protected abstract JComponent createMainPanel();

	protected abstract JToolBar createToolBar();

	protected abstract JMenuBar createMenuBar();

	protected abstract Icon getSplashIcon();

	protected abstract String getApplicationName();
	
	public JFrame getFrame()
	{
		return frame;
	}
}
