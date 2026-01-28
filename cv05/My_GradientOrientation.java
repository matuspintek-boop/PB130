package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;

/** This plugin approximates the gradient orientation using central differences. */
public class My_GradientOrientation implements PlugInFilter 
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
		// size of the input image
		int w = ip.getWidth();
		int h = ip.getHeight();

		// allocate the output image
		FloatProcessor out = new FloatProcessor(w, h);

		// go through the image 'ip' and compute the gradient orientation at each pixel
		// skip pixels in which the operator looks outside the image domain
		// store the computed values into the image 'out'
		// to work with floating point values, use getf()/setf() methods of the 'ImageProcessor' class
		// to compute the orientation of a gradient vector (gx, gy) in degrees, call Math.toDegrees(Math.atan2(gy, gx))
		// WRITE YOUR CODE HERE
		for (int j = 1; j < h - 1; ++j)
			{
				for (int i = 1; i < w - 1; ++i)
				{
					double gradx = (double) .5f * (ip.getf(i+1, j) - ip.getf(i-1, j));
					double grady = (double) .5f * (ip.getf(i, j+1) - ip.getf(i, j-1));

					float output = (float) Math.toDegrees(Math.atan2(grady, gradx));

					out.setf(i, j, output);
				}
			}
		// NO CHANGES NEEDED AFTER THIS LINE

		// show the output image
		ImagePlus outImg = new ImagePlus("My gradient orientation of " + title, out);
		outImg.show();
	}
}
