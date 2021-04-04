package test;

import java.util.ArrayList;
import java.util.*;

import test.Commands.Command;
import test.Commands.DefaultIO;

public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio); 
		commands=new ArrayList<>();
		// example: commands.add(c.new ExampleCommand());
		commands.add(c.new UploadCSVFileCommand());
		commands.add(c.new ChangeSettingsCommand());
		commands.add(c.new DetectAnomaliesCommand());
		commands.add(c.new DisplayResultsCommand());
		commands.add(c.new AnalyzeResultsCommand());
		commands.add(c.new ExitCommand());
	}
	
	public void start() {
		Integer counter = 1;
		int input;
		HashMap<Integer,Command> mapMenu = new HashMap<Integer,Command>();
		while(true)
		{
			dio.write("Welcome to the Anomaly Detection Server.");
			dio.write("\n");
			dio.write("Please choose an option:");
			dio.write("\n");
			counter = 1;
			for(Command c: commands)
			{
				mapMenu.put(counter, c);
				dio.write(counter + ". " + c.getDescription());
				dio.write("\n");
				counter++;
			}
			input = Integer.parseInt(dio.readText());
			if(!(mapMenu.containsKey(input)))
			{
				dio.write("The server got not valid input");
				dio.write("\n");
			}
			else
			{
				mapMenu.get(input).execute();
				if(mapMenu.get(input).description.equals("exit"))
				{
					break;
				}
			}
		}
	}
}
