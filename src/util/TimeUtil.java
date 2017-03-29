package util;

import java.sql.Timestamp;

/**
 * Created by Jed on 2017-03-28.
 */
public class TimeUtil {

    public static double getTimeDifferenceInSeconds(Timestamp start, Timestamp end) {

        if (start == null || end == null) {
            throw new NullPointerException();
        }

        double result;
        //do relative computation
        //get the difference in milliseconds first
        //we know end is always greater than start
        long milliDifference = end.getTime() - start.getTime();

        //get their nano values for a more precise calc
        //remove unnecessary part, precision is up to 3 decimal after millis
        //after that its just zero
        long nanoStart = start.getNanos() / 1000;
        long nanoEnd = end.getNanos() / 1000;

        //get the values after the millis part
        double startNanoPart = nanoStart % 1000 / 1000.0;
        double endNanoPart = nanoEnd % 1000/ 1000.0;

        //relative computation
        //start is zero so end is the difference between end and start
        //just add the nano part of the end
        double endMillis = milliDifference + endNanoPart;
        //you don't have to add zero to start nano

        //so difference is the end millis and start nano
        double difference = endMillis - startNanoPart;

        result = difference / 1000.0;

        return result;
    }
}
