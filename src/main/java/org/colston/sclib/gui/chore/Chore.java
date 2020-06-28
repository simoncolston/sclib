package org.colston.sclib.gui.chore;

import java.awt.Component;
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingWorker;

/**
 * Swing worker that blocks user input while working.
 * @author simon
 *
 * @param <T> type of object returned from the background thread process
 */
public abstract class Chore<T>
{
	private static Config config = new Config();
	
	private SwingWorker<T, ChoreProgressProvider> worker = new SwingWorker<>() 
		{
			@Override
			protected T doInBackground() throws Exception
			{
				return doChore();
			}

			@Override
			protected void done()
			{
				choreComplete();
			}
		};
	
	private ChoreGlassPane glassPane;
	
	public Chore()
	{
		init(config.getRootPane());
	}
	
	public Chore(JComponent component)
	{
		Component c = component;
		while (!(c instanceof RootPaneContainer))
		{
			c = c.getParent();
		}
		init((RootPaneContainer) c);
	}
	
	public Chore(RootPaneContainer rpc)
	{
		init(rpc);
	}

	private void init(RootPaneContainer rootPane)
	{
		if (rootPane.getGlassPane() instanceof ChoreGlassPane)
		{
			glassPane = (ChoreGlassPane) rootPane.getGlassPane();
		}
		else 
		{
			glassPane = new ChoreGlassPane();
			rootPane.setGlassPane(glassPane);
		}
	}
	
	public void execute()
	{
		showGlass();
		worker.execute();
	}
	
	public T get()
	{
		try
		{
			return worker.get();
		}
		catch (InterruptedException | ExecutionException e)
		{
		}
		return null;
	}
	
	protected abstract T doChore() throws Exception;
	
	protected abstract void updateUI();
	
	protected void choreException(Exception e)
	{
		e.printStackTrace();
		removeGlass();
	}
	
	private void showGlass()
	{
		glassPane.setVisible(true);
	}
	
	private void removeGlass()
	{
		glassPane.setVisible(false);
	}
	
	private void choreComplete()
	{
		try
		{
			worker.get(); //to find out if there was an exception
			updateUI();
			removeGlass();
		} catch (InterruptedException | ExecutionException e)
		{
			choreException(e);
		}
	}
	
	public static Config getConfig()
	{
		return config;
	}
	
	public static class Config
	{
		private RootPaneContainer rootPane;

		public RootPaneContainer getRootPane()
		{
			return rootPane;
		}

		public Config setRootPane(RootPaneContainer rootPane)
		{
			this.rootPane = rootPane;
			return this;
		}
	}
}
