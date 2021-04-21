package anomaly_detectors;

import java.util.ArrayList;

import anomaly_detectors.Commands.Command;
import anomaly_detectors.Commands.DefaultIO;

public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio); 
		commands=new ArrayList<>();
		// example: commands.add(c.new ExampleCommand());
		// implement
		commands.add(c.new UploadTimeSeriesCommand());
		commands.add(c.new AlgorithmSettingsCommand());
		commands.add(c.new DetectAnomaliesCommand());
		commands.add(c.new DisplayResultsCommand());
		commands.add(c.new UploadAndAnalyzeCommand());
		commands.add(c.new ExitCommand());
	}
	
	public void start() {
		// implement
		dio.write("Welcome to the Anomaly Detection Server.\n"
				+ "Please choose an option:\n");
		commands.forEach(com->dio.write((commands.indexOf(com)+1)+". "+com.description+"\n"));
		int val = (int) dio.readVal();
		dio.readText();
		commands.get(val-1).execute();
		if (val!=6)
			this.start();
	}
	
}
