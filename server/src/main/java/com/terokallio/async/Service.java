package com.terokallio.async;

/**
 * Simple service
 *
 * Created by tero.kallio on 17/04/16.
 */
public class Service
{
    public String veryExpensiveOperation() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Time is an illusion. Lunchtime doubly so.";
    }

}
