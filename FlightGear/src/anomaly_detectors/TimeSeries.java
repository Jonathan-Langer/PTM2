package anomaly_detectors;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TimeSeries {
	
	private ArrayList<String> titles;
	private ArrayList<ArrayList<Float>> info;
	
	public TimeSeries() {titles =null; info =null;}
	
	public TimeSeries(String csvFileName) {
		titles = null; info = null;
		String lines;
		
		Scanner scan = null, lineScan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(csvFileName)));
			titles = new ArrayList<String>();
			info = new ArrayList<ArrayList<Float>>();
			lines = scan.nextLine();
			lineScan = new Scanner(lines);
			lineScan.useDelimiter(",");
			while (lineScan.hasNext()) {
				titles.add(lineScan.next());
				info.add(new ArrayList<Float>());
			}
			
			int index;

			while (scan.hasNextLine()) {
				lines = scan.nextLine();
				lineScan = new Scanner(lines);
				lineScan.useDelimiter(",");
				index =0;
				while (lineScan.hasNextFloat()) {
					info.get(index).add(lineScan.nextFloat());
					index++;
				}
			}
			scan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	 
	public boolean isEmpty() { return titles==null;}
	
	public void addTitle(String s, ArrayList<Float> i) {
		titles.add(s);
		info.add(i);
	}
	
	public void addRow(ArrayList<Float> row) {
		info.forEach(item -> item.add(row.get(info.indexOf(item))));}
	
	public int getLength() { return info.get(0).size();}
	
	public int getSize() { return titles.size();}
	
	public ArrayList<Float> getLine(String s) {return info.get(titles.indexOf(s));}

	public float[] getLineAsArray(int i) {
		float[] retArr = new float[info.get(i).size()];
		for (int j=0; j<retArr.length;j++)
			retArr[j]=info.get(i).get(j).floatValue();
		return retArr;
	}

	public ArrayList<Float> getLineAsList(int i){
		return info.get(i);
	}
	public ArrayList<String> getTitles() {return titles;}

	public ArrayList<ArrayList<Float>> getInfo() {return info;}
	
	public void addCol(String feature,ArrayList<Float> data) {
		titles.add(feature);
		info.add(data);
	}

	public TimeSeries filterBySelectingColl(HashMap<Integer,String> c){
		if(c==null)
			return null;
		if(c.isEmpty())
			return null;
		TimeSeries ret=new TimeSeries();
		for(Integer coll:c.keySet()){
			if(titles.get(coll)!=null){
				if(ret.titles==null)
					ret.titles=new ArrayList<>();
				if(ret.info==null)
					ret.info=new ArrayList<>();
				ret.titles.add(c.get(coll));
				ret.info.add(this.info.get(coll));
			}
		}
		return ret;
	}
}
