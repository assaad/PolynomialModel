package org.kevoree.polynomial.benchmark;

import org.kevoree.util.DataLoaderZip;
import org.kevoree.util.DataPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by assaa_000 on 25/11/2014.
 */
public class ExecutorPaper {

    public static void execute(String dataset, String name, int times, int size, double error){

        Benchmark[] toRun = new Benchmark[4];

        toRun[0] = new BenchmarkTreeMap();
        BenchmarkPolynomialTreeMap btm= new BenchmarkPolynomialTreeMap();
        btm.error=error;
        toRun[1] = btm;

        toRun[2] = new BenchmarkKmfPolynomial();
        toRun[3] = new BenchmarkKmfDiscrete();

        long starttime;
        long endtime;
        double res;

        DataLoaderZip.setBaseDir("/Users/assaad/work/github/BenchmarkIoT/DataSets/");
        starttime = System.nanoTime();
        final ArrayList<DataPoint> p = DataLoaderZip.load(dataset);
        endtime = System.nanoTime();
        res = ((double) (endtime - starttime)) / (1000000000);
        System.out.println();
        System.out.println("Loaded " + name + " :" + p.size() + " values in " + res + " s!");


        Collections.sort(p);

        final ArrayList<DataPoint> points=new ArrayList<DataPoint>(size);
        for(int i=0;i<size;i++){
            DataPoint dp= new DataPoint();
            dp.time=i;
            dp.value=p.get(i%p.size()).value;
            points.add(dp);
        }



      for (int i = 0; i < toRun.length; i++) {
            toRun[i].setGcCollect(true);
            toRun[i].setDataPoints(points);
            System.out.println("Writing on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkWrite(times)+" s");
        }


       System.out.println("-----------------------------------------------");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Sequential reading on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkSequencialRead(times, points.size())+" s");
        }

       System.out.println("-----------------------------------------------");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Random reading on " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkRandomRead(times,points.size())+" s");
        }


       System.out.println("-----------------------------------------------");

        for (int i = 0; i < toRun.length; i++) {
            toRun[i].setDataPoints(points);
            System.out.println("Test Continuity " + toRun[i].getBenchmarkName() + ": " + toRun[i].benchmarkContinuity(10)+"");
        }

    }


    public static void main(String[] args) throws IOException {
        int times=10;
        int point=1000000;


        execute("ds0.zip","Constant", times,point,0.0001); //Constant database


     /*   execute("ds12.zip","Linear",times,point,0.0001);
        execute("ds1.zip","Temperature",times,point,0.0001);
        execute("ds3.zip","Luminosity",times,point,0.0001);
        execute("ds5.zip","Electric",times,point,0.0001);
        execute("ds9.zip","Sound",times,point,0.0001);
        execute("ds11.zip","Random",times,point,0.0001); */



    }
}
