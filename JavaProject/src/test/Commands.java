package test;

import java.io.*;
import java.util.*;

public class Commands {
	
	
	// Default IO interface
	public interface DefaultIO{
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);
		default void uploadFile(String path)
		{
			
		}
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio) {
		this.dio=dio;
		sharedState.anomalyDetector = new SimpleAnomalyDetector();
	}
	
	
	
	
	// the shared state of all commands
	private class SharedState {
		TimeSeries trainData;
		TimeSeries testData;
		SimpleAnomalyDetector anomalyDetector;   
		List<AnomalyReport> detections;
	}
	
	private  SharedState sharedState=new SharedState();

	
	// Command abstract class
	public abstract class Command{
		protected String description;
		
		public Command(String description) {
			this.description=description;
		}
		
		public String getDescription()
		{
			return this.description;
		}
		
		public abstract void execute();
	}
	
	// Command class for example:
	/*public class ExampleCommand extends Command{

		public ExampleCommand() {
			super("this is an example of command");
		}

		@Override
		public void execute() {
			dio.write(description);
		}		
	}*/
	public class UploadCSVFileCommand extends Command
	{
		
		public UploadCSVFileCommand() {
			
			super("upload a time series csv file");
		}

		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			String line =  null;
			try {
				PrintWriter createTrainFile = null;
				createTrainFile= new PrintWriter(new FileWriter("anomalyTrain.csv"));
				while(!(line = dio.readText()).equals("done"))
				{
					String[] lineRead = line.split(",");
					createTrainFile.write(line);
					createTrainFile.write("\n");
					createTrainFile.flush();
				}
				sharedState.trainData = new TimeSeries("anomalyTrain.csv");
				dio.write("Upload complete.\n");
				
				dio.write("Please upload your local test CSV file.\n");
				line =  null;
				PrintWriter createTestFile = null;
				createTestFile= new PrintWriter(new FileWriter("anomalyTest.csv"));
				while(!(line = dio.readText()).equals("done"))
				{
					String[] lineRead = line.split(",");
					createTestFile.write(line);
					createTestFile.write("\n");
					createTestFile.flush();
				}
				sharedState.testData = new TimeSeries("anomalyTest.csv");
				dio.write("Upload complete.\n");
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	public class ChangeSettingsCommand extends Command
	{
		public ChangeSettingsCommand() {
			super("algorithm settings");
		}

		@Override
		public void execute() {
			Float input = Float.parseFloat(dio.readText());
			dio.write("The current correlation threshold is " + sharedState.anomalyDetector.TH);
			dio.write("\n");
			dio.write("Type a new threshold \n");
			while(input > 1 || input < 0 )
			{
				dio.write("please choose a value between 0 and 1");
				dio.write("\n");
				dio.write("Type a new threshold");
				dio.write("\n");
			}
			sharedState.anomalyDetector = new SimpleAnomalyDetector(input.toString());
			
		}
		
	}
	
	public class DetectAnomaliesCommand extends Command {

		public DetectAnomaliesCommand() {
			super("detect anomalies");
		}

		@Override
		public void execute() {
			sharedState.anomalyDetector.learnNormal(sharedState.trainData);
			sharedState.detections = sharedState.anomalyDetector.detect(sharedState.testData);
			dio.write("anomaly detection complete.");
			dio.write("\n");
		}
		
	}
	
	public class DisplayResultsCommand extends Command {

		public DisplayResultsCommand() {
			super("display results");
		}

		@Override
		public void execute() {
			for(AnomalyReport ar: sharedState.detections)
			{
				dio.write(ar.timeStep +"\t");
				dio.write(ar.description);
				dio.write("\n");
			}
			dio.write("Done.");
			dio.write("\n");
		}		
	}
	
	public class AnalyzeResultsCommand extends Command{

		public AnalyzeResultsCommand() {
			super("upload anomalies and analyze results");
		}

		@Override
		public void execute() {
			boolean stilOpen = false;
			List<Long[]> newDetections= new ArrayList<Long[]>();
			int i=0;
			
			for(;i<sharedState.detections.size();i++)
			{
				//int j=0;
				int index=0;
				Long[] seriaOfDetections = new Long[2];
				seriaOfDetections[0]=Long.parseLong("0");
				seriaOfDetections[1]=Long.parseLong("0");
				String currentDescription= sharedState.detections.get(i).description;
				Long currentTimeStep = sharedState.detections.get(i).timeStep;
				for(int j=i+1;j<sharedState.detections.size();j++)
				{
					
					if(sharedState.detections.get(j).description.equals(currentDescription) && sharedState.detections.get(j).timeStep == currentTimeStep + 1l)
					{
						stilOpen=true;
						if(seriaOfDetections[0]==0)
						{
							seriaOfDetections[0]=currentTimeStep;
						}
						seriaOfDetections[1] = sharedState.detections.get(j).timeStep; 
						currentDescription=sharedState.detections.get(j).description;
						currentTimeStep=sharedState.detections.get(j).timeStep;
						index++;
					}
				}
				if(stilOpen==true)
				{
					newDetections.add(seriaOfDetections);
					i+=index;
					stilOpen=false;
				}
			}
			dio.write("Please upload your local anomalies file.");
			dio.write("\n");
			long positive = 0;
			int n_NumberOfRows = sharedState.testData.getRow();
			long negetive = 0;
			String line = null;
			
			List<Long[]> listOfAnomalies = new ArrayList<Long[]>();
			Set<Long[]> detectionSet = new HashSet<Long[]>();
			while(!(line = dio.readText()).equals("done"))
			{
				String[] detectionRange = line.split(",");
				Long[] arr = new Long[2];
				for(i=0;i<detectionRange.length;i++)
				{
					arr[i]=Long.parseLong(detectionRange[i]);
				}
				listOfAnomalies.add(arr);
				positive++;
			}
			dio.write("Upload complete.");
			dio.write("\n");
			Long sumOfRanges = Long.parseLong("0");
			for(Long[] arr: listOfAnomalies)
			{
				Long currentRange = arr[1]-arr[0];
				currentRange= currentRange+1;
				sumOfRanges+=currentRange;
			}
			negetive= (int) (n_NumberOfRows-sumOfRanges);
			
			Float falsePositiveRate = Float.parseFloat("0");
			Float truePositiveRate = Float.parseFloat("0");
			int truePositive = 0;
			int falsePositive = 0;
			
			for(Long[] arr: newDetections)
			{
				detectionSet.add(arr);
			}
			/*for(Long[] arr: listOfAnomalies)
			{
				for(AnomalyReport ar: sharedState.detections)
				{
					if((ar.timeStep<arr[0])||(ar.timeStep>arr[1]))
					{
						falsePositive++;
					}
				}
			}*/
			for(Long[] arrDetections: newDetections)
			{
				if(checkIntersection(arrDetections,listOfAnomalies))
				{
						truePositive++;
				}
				else
				{
					if(detectionSet.contains(arrDetections))
					{
						falsePositive++;
						detectionSet.remove(arrDetections);
					}
				}
			}
			truePositiveRate = ((float)truePositive/(float)positive);
			falsePositiveRate =  ((float)falsePositive/(float)negetive);
			Integer toPrintTPR = (int)(truePositiveRate*1000);
			Integer toPrintFPR = (int)(falsePositiveRate*1000);

			dio.write("True Positive Rate: " + ((float)toPrintTPR/1000f+"\r"));
			dio.write("\n");
			dio.write("False Positive Rate: " +((float)toPrintFPR)/1000f+"\r");
			dio.write("\n");
		}	
	}
	private boolean isContaining(Long[] detection, Long[] anomaly)
	{
		if(anomaly[0]>=detection[0]&&(anomaly[1]<=detection[1]))
		{
			return true;
		}
		return false;
	}
	private boolean isTransverse(Long[] arr1, Long[] arr2) 
	{
		if(((arr1[0]>=arr2[0]) && (arr1[0]<=arr2[1])) || (((arr1[1]>=arr2[0]) && (arr1[1]<=arr2[1]))))
		{
			if(arr1[0]==arr2[1]||arr1[1]==arr2[0])
			{
				//return false;
				return true;
			}
			return true;
		}
		return false;
	}
	private boolean checkIntersection(Long[] detection,List<Long[]> anomalies)
	{
		for(Long[] arr: anomalies)
		{
			if(isTransverse(detection,arr))
			{
				return true;
			}
			if(isContaining(detection, arr))
			{
				return true;
			}
		}
		return false;
	}
	public class ExitCommand extends Command {

		public ExitCommand() {
			super("exit");
		}

		@Override
		public void execute() {
			//dio.write("bye");
		}		
	}
	
}
