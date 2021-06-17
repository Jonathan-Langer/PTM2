package anomaly_detectors;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Commands {
	
	// Default IO interface
	public interface DefaultIO{
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);
		
		// you may add default methods here
		default public void close() {}
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio) {
		this.dio=dio;
	}
	
	// the shared state of all commands
	private class SharedState{
		public TimeSeries tsTrain, tsTest;
		public TimeSeriesAnomalyDetector detector;
		public List<AnomalyReport> report;
		public List<P> ogGroups;
	}
	
	private class P {
		public long x,y;
	P(long x, long y){this.x=x; this.y=y;}
	public int cmp(P p) {
		if (p.x>this.y) return 1; //after
		if (p.y<this.x) return -1; //before
		return 0; //together
	}
	}
	
	private  SharedState sharedState=new SharedState();
	
	// Command abstract class
	public abstract class Command{
		protected String description; 
		
		public Command(String description) {
			this.description=description;
		}
		
		public abstract void execute();
	}
	
	// Command class for example:
	public class ExampleCommand extends Command{

		public ExampleCommand() {
			super("this is an example of command");
		}

		@Override
		public void execute() {
			dio.write(description);
		}		
	}
	
	// implement here all other commands
	public class UploadTimeSeriesCommand extends Command{
		public UploadTimeSeriesCommand() {
			super("upload a time series csv file");
		}

		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			//String filePath = dio.readText();
			getFile("anomalyTrain.csv");
			sharedState.tsTrain = new TimeSeries("anomalyTrain.csv");
			dio.write("Upload complete.\n");
			dio.write("Please upload your local test CSV file.\n");
			//filePath = dio.readText();
			getFile("anomalyTest.csv");
			sharedState.tsTest = new TimeSeries("anomalyTest.csv");
			dio.write("Upload complete.\n");	
		}	
	}
	
	public class AlgorithmSettingsCommand extends Command{
		public AlgorithmSettingsCommand() {
			super("algorithm settings");
		}

		@Override
		public void execute() {
			dio.write("The current correlation threshold is "+SimpleAnomalyDetector.thForCorrelation+"\n");
			dio.write("Type a new threshold\n");
			float temp = dio.readVal();
			while (!(temp>0 && temp<1)) {
			dio.write("please choose a value between 0 and 1.\n");	
			temp = dio.readVal();
			}
			SimpleAnomalyDetector.thForCorrelation=temp;
		}	
	}
	
	public class DetectAnomaliesCommand extends Command{
		public DetectAnomaliesCommand() {
			super("detect anomalies");
		}

		@Override
		public void execute() {
			sharedState.detector = new SimpleAnomalyDetector();
			sharedState.detector.learnNormal(sharedState.tsTrain);
			dio.write("anomaly detection complete.\n");
		}	
	}
	
	public class DisplayResultsCommand extends Command{
		public DisplayResultsCommand() {
			super("display results");
		}

		@Override
		public void execute() {
			sharedState.report = sharedState.detector.detect(sharedState.tsTest);
			sharedState.ogGroups = new ArrayList<P>();
			if (!sharedState.report.isEmpty())
				sharedState.ogGroups.add(new P(sharedState.report.get(0).timeStep,sharedState.report.get(0).timeStep));
			for(int i=1; i<sharedState.report.size(); i++) {
				if((sharedState.report.get(i).timeStep-1==sharedState.report.get(i-1).timeStep)
						&& (sharedState.report.get(i).description.equals(sharedState.report.get(i-1).description)))
					sharedState.ogGroups.get(sharedState.ogGroups.size()-1).y++;
				else
					sharedState.ogGroups.add(new P(sharedState.report.get(i).timeStep,sharedState.report.get(i).timeStep));
					
			}
			sharedState.report.forEach(item -> dio.write(item.timeStep+"\t"+item.description+"\n"));
			dio.write("Done.\n");
		}	
	}

	public class UploadAndAnalyzeCommand extends Command{
		public UploadAndAnalyzeCommand() {
			super("upload anomalies and analyze results");
		}
		
		private float roundAvoid(final float value, final int places) {
		    double scale = Math.pow(10, places);
		    return (float)(Math.floor(value * scale) / scale);
		}
		
		@Override
		public void execute() {
			dio.write("Please upload your local anomalies file.");
			//String filePath = dio.readText();
			List<P> filed = new ArrayList<P>();
			Scanner scan;
			String line;
			float p=0, n=sharedState.tsTest.getLength(), tp=0, fp=0;
			while (!(line=dio.readText()).equals("done")) {
				p++;
				scan = new Scanner(line);
				scan.useDelimiter(",");
				filed.add(new P(scan.nextLong(),scan.nextLong()));
				n= n+filed.get(filed.size()-1).x-filed.get(filed.size()-1).y-1;
			}
			dio.write("Upload complete.\nAnalyzing...\n");	
			int place=0; boolean used = false;
			for(P item : sharedState.ogGroups) {
				while ((place<filed.size())&&(item.cmp(filed.get(place))<=0)) 
					{
					if (item.cmp(filed.get(place))==0)
					{ used=true; tp++;}
					place++;
					}
				if(!used) fp++;
				used=false;
				}
		    tp/=p; fp/=n;
			dio.write("True Positive Rate: "+this.roundAvoid(tp, 3)+"\nFalse Positive Rate: "+this.roundAvoid(fp, 3)+"\n");		
		}	
	}

	public class ExitCommand extends Command{
		public ExitCommand() {
			super("exit");
		}

		@Override
		public void execute() {
			dio.close();
		}	
	}

	//get file
	private void getFile(String file) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			String line;
			while (!(line=dio.readText()).equals("done")) {
				writer.println(line);
				}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
