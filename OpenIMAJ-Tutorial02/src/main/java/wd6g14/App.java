package wd6g14;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {

	public static final String IMAGE_PROCESSING_WINDOW = "Image Processing Window";


	public static void main( String[] args ) {
	    JFrame window = DisplayUtilities.createNamedWindow(IMAGE_PROCESSING_WINDOW, IMAGE_PROCESSING_WINDOW, true);

    	//Create an image
	    MBFImage image = null;
	    try
	    {
		    image = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
	    }
	    catch (IOException e)
	    {
		    e.printStackTrace();
	    }

	    System.out.println(image.colourSpace);

	    DisplayUtilities.displayName(image, IMAGE_PROCESSING_WINDOW);
	    DisplayUtilities.displayName(image.getBand(0), IMAGE_PROCESSING_WINDOW);

	    MBFImage clone = image.clone();

	    for (int y=0; y<image.getHeight(); y++) {
		    for(int x=0; x<image.getWidth(); x++) {
			    clone.getBand(1).pixels[y][x] = 0;
			    clone.getBand(2).pixels[y][x] = 0;
		    }
	    }

	    //or

//	    clone.getBand(1).fill(0f);
//	    clone.getBand(2).fill(0f);

	    DisplayUtilities.displayName(clone, IMAGE_PROCESSING_WINDOW);

	    image.processInplace(new CannyEdgeDetector());

	    DisplayUtilities.displayName(image, IMAGE_PROCESSING_WINDOW);

	    image.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
	    image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
	    image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
	    image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
	    image.drawShape(new Ellipse(500f, 300f, 100f, 70f, 0f), 5, RGBColour.RED);
	    image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
	    image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);

		image.processInplace(new FGaussianConvolve(0.5f));
	    DisplayUtilities.displayName(image, IMAGE_PROCESSING_WINDOW);
    }
}
