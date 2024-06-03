/**
 * @version 0.2
 * @date 02.06.2024
 */
package application;

import processing.core.PApplet;

/**
 * Main
 */
public class Main extends PApplet {

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
	try {
	    PApplet.main("components.AStarAlgorithm");
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	    e.getStackTrace();
	} finally {
	    for (;;)
		if (Thread.activeCount() == 1)
		    break;
	    System.out.println("Done!");
	}
    }
}