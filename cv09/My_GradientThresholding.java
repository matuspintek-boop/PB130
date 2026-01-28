package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin thresholds a given 8-bit grayscale image based on the gradient information. */
public class My_GradientThresholding implements PlugInFilter 
{
	String title = null;

	public int setup(String arg, ImagePlus im) 
	{
		if (im != null)
		{
			// store the short image title for future use
			title = im.getShortTitle();
			
			// this plugin accepts 8-bit grayscale images only
			return DOES_8G + NO_CHANGES; 
		}
		else
		{
			// no image is open
			IJ.noImage();
			return DONE;
		}
	}

	public void run(ImageProcessor ip) 
	{	
		// create the output image as a copy of the input one
		ImageProcessor out = ip.duplicate();

		// threshold
		int thresh = 0;

		// find a suitable threshold 'thresh' for the image 'ip' based on the gradient information
		// skip pixels in which the gradient magnitude operator looks outside the image domain
		// threshold the image 'ip' with the computed threshold 'thresh' (<thresh -> background, >=thresh -> foreground)
		// write the thresholded image into the image 'out' (background -> 0, foreground -> 255)
		// WRITE YOUR CODE HERE
		int width = out.getWidth();
		int height = out.getHeight();
		float nomin = 0f;
		float denomi = 0f;

		for (int v = 1; v < height - 1; v++)
		{
			for (int u = 1; u <  width - 1; u++)
			{
				int gX = ip.get(u + 1, v) - ip.get(u - 1, v);
				int gY = ip.get(u, v + 1) - ip.get(u, v - 1);

				float magnitude = (float) Math.sqrt(gX*gX + gY*gY);

				nomin += magnitude * ip.get(u, v);
				denomi += magnitude;
			}
		}

		thresh = (int) (nomin/denomi);

		for (int v = 0; v < height; v++)
		{
			for (int u = 0; u < width; u++)
			{
				int value = ip.get(u, v);
				if ( value < thresh)
				{
					out.set(u, v, 0);
				} else{
					out.set(u, v, 255);
				}
			}
		}
		// NO CHANGES NEEDED AFTER THIS LINE

		// show the output image
		ImagePlus outImg = new ImagePlus(String.format("My gradient thresholding (thresh=%d) of ", thresh) + title, out);
		outImg.show();
	}
}
