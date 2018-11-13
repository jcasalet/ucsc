package cmps242;

import java.util.HashMap;
import java.util.Random;


public class Problem2 {
    
    public static void main(String[] args) {
        int dataSetLength = 1000; //Integer.parseInt(args[0]);
        int start = 400; //Integer.parseInt(args[1]);
        int end = 600; //Integer.parseInt(args[2]);
        int trainingSetLength = 10; //Integer.parseInt(args[3]);
        int numTrials=50000;
        boolean skewedTrain=true;
        boolean skewedTest=true;
       
        DataPoints myData = new DataPoints(dataSetLength, start, end);
        int numFailures=0;

        // run experiment 
        for (int run=0; run<numTrials; run++) {
            HashMap<Integer, String> trainingData = getTrainingSet(trainingSetLength, dataSetLength, start, end, skewedTrain);  
            Interval myInterval = getInterval(trainingData);                
            int testPoint = getTestPoint(skewedTest, dataSetLength);
                
            System.out.print("testing " + testPoint + " in interval: " + myInterval.getStart() + ", " + myInterval.getEnd());     
            String actualValue = myData.getDataPoints().get(testPoint);   
            String testValue="";
            if(testPoint >= myInterval.getStart() && testPoint <= myInterval.getEnd() ) {
                testValue = "+";
            }
            else
                testValue = "-";
                
            if(! actualValue.equals(testValue)) {
                numFailures++;
                System.out.println("\t error!");
            }
            else
                System.out.println("");
        }
        
        System.out.println("error is " + (float) numFailures / (float) numTrials);
        numFailures=0;
    }
    
    public static int getTestPoint(boolean skewed, int dataSetLength) {
        Random myRandom = new Random();
        int point1 = myRandom.nextInt(dataSetLength-1);
        if(! skewed)
            return point1;
        int point2 = myRandom.nextInt(dataSetLength-1);
        if(point1 <= point2) {
            return point1;
        }
        else
            return point2;
    }
    
    public static Interval getInterval(HashMap<Integer, String> data) {
        int start=Integer.MAX_VALUE, end=Integer.MIN_VALUE;
        // get start
        for(Integer myKey: data.keySet()) {
            if(data.get(myKey).equals("+")) {
                if(myKey < start)
                    start = myKey;
                if(myKey > end) 
                    end = myKey;
            }
        }
        
        // get end
        for(Integer myKey: data.keySet()) {
            if(data.get(myKey).equals("+") && myKey > end)
                start = myKey;
        }
        if(start == Integer.MAX_VALUE)
            start=0;
        if(end == Integer.MIN_VALUE)
            end=0;
        Interval myInterval = new Interval(start, end);
        return myInterval;
    }
    
    public static HashMap<Integer, String> getTrainingSet(int trainingSetLength, int dataSetLength, int start, int end, boolean skewed) {
        HashMap<Integer, String> trainingSet = new HashMap<Integer, String>();
        Random myRandom = new Random();
        int dataPoint;
        
        if (! skewed) {
            for(int i=0; i< trainingSetLength; i++) {
                dataPoint = myRandom.nextInt(dataSetLength -1);
                if(dataPoint < start || dataPoint > end)
                    trainingSet.put(dataPoint,"-");
                else
                    trainingSet.put(dataPoint,  "+");
            }
        }
        else {
            for(int i=0; i< trainingSetLength; i++) {
                int dataPoint1 = myRandom.nextInt(dataSetLength -1);
                int dataPoint2 = myRandom.nextInt(dataSetLength -1);
                if(dataPoint1 <= dataPoint2)
                    dataPoint = dataPoint1;
                else
                    dataPoint = dataPoint2;
                if(dataPoint < start || dataPoint > end)
                    trainingSet.put(dataPoint,"-");
                else
                    trainingSet.put(dataPoint,  "+");
            }
        }
        
        return trainingSet;
    }

}

class Interval {
    int start, end;
    public Interval(int start, int end) {
        this.start=start;
        this.end=end;
    }
    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return end;
    }
    
    
}

class DataPoints {
    HashMap<Integer, String> myDataPoints;
    
    public HashMap<Integer, String> getDataPoints() {
        return myDataPoints;
    }
    

    
    public DataPoints(int length, int start, int end) {
        myDataPoints = new HashMap<Integer, String>();
        for(int i=0; i< length; i++) {
            if(i >= start && i <= end)
                myDataPoints.put(i,  "+");
            else
                myDataPoints.put(i,  "-");
        }
    }
}
