# UCSC BMEB Utilities 

This project contains utilities developed for the BMEB track


### Pre-requisites

* Java SDK 7 or newer
* Maven 3

## Usage

Run samtools depth:
```
samtools depth $bamFile > $depthFile 

```

Clone the repository, then

```
mvn clean package
```

and run the application using:

```
mvn exec:java -Dexec.mainClass=bme296.BinReads $sampleName $depthFile $outputFile $binWidth $widthEpsilon
```


## What to look for?
Looking at output file for each chromome, you'll notice:

* not all bins are exactly your bin width, but roughly they are.  That's due to the widthEpsilon parameter and the fact that `samtools depth` doesn't generate completely contiguous reads
* some counts are off the charts, but most follow a trend 


