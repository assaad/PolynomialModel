package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class Executor {
    public static void main(String[] args) throws IOException {

        Benchmark[] toRun = new Benchmark[3];

        toRun[0] = new BenchmarkArrayList();
        toRun[1] = new BenchmarkRbTree();
        toRun[2] = new BenchmarkTreeMap();

        long starttime;
        long endtime;
        double res;
        starttime = System.nanoTime();
        //DataLoaderZip.setBaseDir("/Users/duke/Documents/dev/assaad/BenchmarkIoT/DataSets/");
        DataLoaderZip.setBaseDir("D:\\workspace\\Github\\PolynomialModel\\DataSets\\");
        final ArrayList<DataPoint> points = DataLoaderZip.load("ds1.zip");
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println("Loaded :" + points.size() + " values in " + res + " s!");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Writing on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkWrite(10));
        }


    }
}