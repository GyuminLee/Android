package org.jcodec.movtool;

import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.movtool.Flattern.ProgressListener;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class QTEdit {

	protected final CommandFactory[] factories;
	private final List<ProgressListener> listeners = new ArrayList<ProgressListener>();

	public static interface CommandFactory {
		String getName();

		Command parseArgs(List<String> args);

		String getHelp();
	}

	public static interface Command {
		/**
		 * Performs changes on movie header
		 * 
		 * @param movie
		 */
		void apply(MovieBox movie);
	}

	public static abstract class BaseCommand implements Command {
		public void apply(MovieBox movie, FileChannel[][] refs) {
			apply(movie);
		}

		public abstract void apply(MovieBox movie);
	}

	public QTEdit(CommandFactory... factories) {
		this.factories = factories;
	}

	public void addProgressListener(ProgressListener listener) {
		listeners.add(listener);
	}

	public void execute(String[] args) throws Exception {
		LinkedList<String> aa = new LinkedList<String>(Arrays.asList(args));

		final List<Command> commands = new LinkedList<Command>();
		while (aa.size() > 0) {
			int i;
			for (i = 0; i < factories.length; i++) {
				if (aa.get(0).equals(factories[i].getName())) {
					aa.remove(0);
					try {
						commands.add(factories[i].parseArgs(aa));
					} catch (Exception e) {
						System.err.println("ERROR: " + e.getMessage());
						return;
					}
					break;
				}
			}
			if (i == factories.length)
				break;
		}
		if (aa.size() == 0) {
			System.err.println("ERROR: A movie file should be specified");
			help();
		}
		if (commands.size() == 0) {
			System.err
					.println("ERROR: At least one command should be specified");
			help();
		}
		File input = new File(aa.remove(0));

		if (!input.exists()) {
			System.err.println("ERROR: Input file '" + input.getAbsolutePath()
					+ "' doesn't exist");
			help();
		}

		boolean inplace = new InplaceEdit() {
			protected void apply(MovieBox mov) {
				applyCommands(mov, commands);
			}
		}.save(input);
		if (!inplace) {
			final MovieBox movie = MP4Util.createRefMovie(input);
			applyCommands(movie, commands);
			File out = new File(input.getParentFile(), "." + input.getName());
			Flattern fl = new Flattern();

			for (ProgressListener pl : this.listeners)
				fl.addProgressListener(pl);

			fl.flattern(movie, out);

			out.renameTo(input);
		}
	}

	private static void applyCommands(MovieBox mov, List<Command> commands) {
		for (Command command : commands) {
			command.apply(mov);
		}
	}

	protected void help() {
		System.out.println("Quicktime movie editor");
		System.out
				.println("Syntax: qtedit <command1> <options> ... <commandN> <options> <movie>");
		System.out.println("Where options:");
		for (CommandFactory commandFactory : factories) {
			System.out.println("\t" + commandFactory.getHelp());
		}

		System.exit(-1);
	}
}