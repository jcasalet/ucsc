package bme296;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class SumContigs {
    
    static boolean closeEnough(long n1, long n2, int epsilon) {
        if(Math.abs(n1 - n2) <= epsilon)
            return true;
        else
            return false;
    }
    
    public static void main(String[] args) {
        if(args.length != 6) {
            System.err.println("usage: SumContigs <sample-name> <input-file> <output-file> <min-depth> <depth-epsilon> <position-epsilon");
            System.exit(1);   
        }
        
        String sampleName = args[0];
        String inputFile = args[1];
        String outputFile = args[2];
        int minDepth = Integer.parseInt(args[3]);
        int depthEpsilon = Integer.parseInt(args[4]);
        int positionEpsilon = Integer.parseInt(args[5]);

        // chr  pos     count
        BufferedReader reader;
        BufferedWriter writer;
        try {
            File outFile = new File(outputFile);
            reader = new BufferedReader(new FileReader(new File(inputFile)));
            writer = new BufferedWriter(new FileWriter(outFile, true));
            String line;
            long lastPosition = 0;
            long startPosition = 0;
            long depth=0;
            long lastDepth=0;
            int count = 0;
            int lastCount = 0;
            int totalCount=0;
            long length = 0;
            boolean firstLine = true;
            String chromosome = "";
            if(outFile.length() == 0)
                writer.append("sample,chromosome,start,stop,length,count,depth\n");
            writer.flush();
            while((line=reader.readLine()) != null) {
                    chromosome=line.split("\\\t")[0];
                    int position=Integer.parseInt(line.split("\\\t")[1]);
                    count = Integer.parseInt(line.split("\\\t")[2]);
                    if(firstLine) {
                        totalCount = count;
                        startPosition = position;
                        lastPosition = position;
                        lastCount = count;
                        firstLine = false;
                        continue;
                    }
                    length = lastPosition - startPosition + 1;
                    depth = totalCount / length;
                    //if((position != lastPosition +1)) {   
                    if(!closeEnough(lastPosition, position, positionEpsilon) || !closeEnough(lastDepth, depth, depthEpsilon)) {
                        if(depth >= minDepth) {
                            writer.append(sampleName + "," + chromosome + "," + startPosition + "," + 
                                        lastPosition + "," + length + "," + totalCount + "," + depth + "\n");
                            writer.flush();
                        }
                        startPosition = position;
                        lastPosition = position;
                        lastCount = count;
                        totalCount = count;
                        lastDepth = depth;
                        continue;
                    }
                    else {
                        lastPosition = position;
                        lastCount = count;
                        totalCount += count;
                    }
                }
            // write the last record
            length = lastPosition - startPosition + 1;
            depth = totalCount / length;
            if(depth >= minDepth) {
                writer.append(sampleName + "," + chromosome + "," + startPosition + "," + 
                            lastPosition + "," + length + "," + totalCount + "," + depth + "\n");
                writer.flush();
            }
        
        // close i/o 
        reader.close();
        writer.close();
        } catch (Exception e) {
            System.err.println("caught exception: " + e.toString());
        }
        
    }

}
