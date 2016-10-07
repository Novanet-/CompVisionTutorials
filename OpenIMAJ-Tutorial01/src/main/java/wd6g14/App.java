package wd6g14;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.typography.hershey.HersheyFont;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
    	//Create an image

        //Extracted height, width, and x and y positions for the program into variables, so they can be easily adjusted, also modified the displayed text, and the font it is displayed in (displays
        // in cyrillic)
        final int width =1000;
        final int height = 250;
        MBFImage image = new MBFImage(width, height, ColourSpace.RGB);

        //Fill the image with white
        image.fill(RGBColour.CYAN);
        		        
        //Render some test into the image
        final double widthScaler = 0.05;
        final double heightScaler = 0.6;
        image.drawText("Foo Hello World Bar", (int) Math.round(widthScaler * (double) width), (int) Math.round(heightScaler * (double) height), HersheyFont.CYRILLIC, 50, RGBColour.BLACK);

        //Apply a Gaussian blur
        image.processInplace(new FGaussianConvolve(1.25f));
        
        //Display the image
        DisplayUtilities.display(image);
    }
}
