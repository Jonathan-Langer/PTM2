package test;

import java.io.*;
import java.util.*;

public class TimeSeries {
	ArrayList<String>featuresNames;
	float[][] data;
	int numCol;
	int numRow;
	
	
	public TimeSeries(String csvFileName) {
			String[][] dataInString;
			
			try 
			{
				BufferedReader read = new BufferedReader(new FileReader(csvFileName));
				String firstLine = read.readLine();
				String[] firstLineArr = firstLine.split(",");
				this.numCol = firstLineArr.length;
				this.featuresNames = new ArrayList<String>();
				/*for(int i=0;i<featuresNames.size();i++)
				{
					featuresNames.add(firstLineArr[i]);
				}*/
				for(String s: firstLineArr)
				{
					featuresNames.add(s);
				}
				
				
				int countRow = 0; 
				while(read.readLine()!=null)
				{
					countRow++;
				}
				this.numRow=countRow;
				read.close();
				
				
				data = new float [countRow][this.numCol];
				BufferedReader reader = new BufferedReader(new FileReader(csvFileName));
				String line;
				int row = 0;
				int counter=0;
				while((line = reader.readLine()) != null)
				{
					if(counter==0)
					{
						counter++;
						continue;
					}
					else
					{
						String[] lineArr = line.split(",");
						for(int i = 0; i < lineArr.length; i++)
						{
							data[row][i] = Float.parseFloat(lineArr[i]);
						}
						row++;
					}
				}
				reader.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	public ArrayList<Float> getDataOfAttribute(String name)
	{
		int col = getColOfFeature(name);
		ArrayList<Float> dataCol = new ArrayList<Float>();
		for(int i = 0; i < dataCol.size(); i++)
		{
			dataCol.add(data[i][col]);
		}
		return dataCol;
	}
	
	public float[] getDataOfCol(int col)
	{
		float[] dataCol = new float[this.numRow];
		for(int i = 0; i < dataCol.length; i++)
		{
			dataCol[i] = data[i][col];
		}
		return dataCol;
	}
	public Point[] getArray(int col1, int col2)
	{
		Point[] p = new Point[numRow];
		float[] col1Data = getDataOfCol(col1);
		float[] col2Data = getDataOfCol(col2);
		for(int i=0;i<p.length;i++)
		{
			p[i]= new Point(col1Data[i],col2Data[i]);
		}
		return p;
	}
	public String getFeature(int col)
	{
		return featuresNames.get(col);
	}
	public float[][] getData() {
		return data;
	}
	public int getColOfFeature(String featureName)
	{
		for(int i=0;i<featuresNames.size();i++)
		{
			if(featuresNames.get(i).equals(featureName))
			{
				return i;
			}
		}
		return 0;
	}
	
	public ArrayList<String> getFeatures()
	{
		return this.featuresNames;
	}
	
	public int getCol()
	{
		return this.numCol;
	}
	public int getRow()
	{
		return this.numRow;
	}
}

/*
package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSeries {
	
	Map<String, ArrayList<Float>> ts;
	ArrayList<String> atts;
	int dataRowSize;
	
	public TimeSeries(String csvFileName) {
		ts=new HashMap<>();
		atts=new ArrayList<>();
		try {
			BufferedReader in=new BufferedReader(new FileReader(csvFileName));
			String line=in.readLine();
			for(String att : line.split(",")) {
				atts.add(att);
				ts.put(att, new ArrayList<>());
			}
			while((line=in.readLine())!=null) {
				int i=0;
				for(String val : line.split(",")) {
					ts.get(atts.get(i)).add(Float.parseFloat(val));
					i++;
				}
			}
			dataRowSize=ts.get(atts.get(0)).size();
			
			in.close();
		}catch(IOException e) {}
	}
	
	public ArrayList<Float> getAttributeData(String name){
		return ts.get(name);
	}
	
	public ArrayList<String> getAttributes(){
		return atts;
	}
	
	public int getRowSize() {
		return dataRowSize;
	}
}*/
