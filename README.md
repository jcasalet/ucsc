# UCSC BMEB Utilities 

This project contains utilities developed for the BMEB track


### Pre-requisites

* Java SDK 7 or newer
* Maven 3

## Usage

Run samtools mpileup:
```
samtools depth $bamFile > $depthFile 

```

Clone the repository, then

```
mvn clean package
```

and run the application using:

```
mvn exec:java -Dexec.mainClass="bme296.BinReads $sampleName $depthFile $outputFile $binWidth
```


## What to look for?
Looking at output file for each chromome, you'll notice:

* not all bins are exactly your bin width, but roughly they are 
* some counts are off the charts, but most follow a trend 
* updateDocuments() that shows different ways of updating documents


