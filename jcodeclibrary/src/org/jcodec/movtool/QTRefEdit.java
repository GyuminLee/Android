package org.jcodec.movtool;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.movtool.QTEdit.Command;
import org.jcodec.movtool.QTEdit.CommandFactory;

/**
 * This class is part of JCodec ( www.jcodec.org ) This software is distributed
 * under FreeBSD License
 * 
 * @author The JCodec project
 * 
 */
public class QTRefEdit {

    protected final CommandFactory[] factories;

    public QTRefEdit(CommandFactory... factories) {
        this.factories = factories;
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
            System.err.println("ERROR: At least one command should be specified");
            help();
        }
        File input = new File(aa.remove(0));
        
        if (aa.size() == 0){
            System.err.println("ERROR: A movie output file should be specified");
            help();
        }
        
        File output = new File(aa.remove(0));
        
        if (!input.exists()) {
            System.err.println("ERROR: Input file '" + input.getAbsolutePath() + "' doesn't exist");
            help();
        }
        
        if (output.exists()){
            System.err.println("WARNING: Output file '" + output.getAbsolutePath() + "' exist, overwritting");
        }
        
        MovieBox ref = MP4Util.createRefMovie(input);
        applyCommands(ref, commands);
        MP4Util.writeMovie(output, ref);
        System.out.println("INFO: Created reference file: "+output.getAbsolutePath());
    }
    
    private static void applyCommands(MovieBox mov, List<Command> commands) throws IOException {
        for (Command command : commands) {
            command.apply(mov);
        }
    }

    protected void help() {
        System.out.println("Quicktime movie editor");
        System.out.println("Syntax: qtedit <command1> <options> ... <commandN> <options> <movie> <output>");
        System.out.println("Where options:");
        for (CommandFactory commandFactory : factories) {
            System.out.println("\t" + commandFactory.getHelp());
        }

        System.exit(-1);
    }
}