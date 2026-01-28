package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;

/** This plugin approximates the gradient magnitude using central differences. */
public class My_GradientMagnitude implements PlugInFilter 
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

		// go through the image 'ip' and compute the gradient magnitude at each pixel
		// skip pixels in which the operator looks outside the image domain
		// store the computed values into the image 'out'
		// to work with floating point values, use getf()/setf() methods of the 'ImageProcessor' class
		// to compute the square root of x, call Math.sqrt(x)
		// WRITE YOUR CODE HERE
		for (int j = 1; j < h - 1; ++j)
			{
				for (int i = 1; i < w - 1; ++i)
				{
					float gradx = .5f * (ip.getf(i+1, j) - ip.getf(i-1, j));
					gradx *= gradx;
					float grady = .5f * (ip.getf(i, j+1) - ip.getf(i, j-1));
					grady *= grady;

					//vytvorenie pomocnej premennej kvoli prehladnosti kodu a zmene typu
					double sum_ = (double) (gradx + grady);

					float output = (float) Math.sqrt(sum_);
					out.setf(i, j, output);
				}
			}
		// NO CHANGES NEEDED AFTER THIS LINE

		// show the output image
		ImagePlus outImg = new ImagePlus("My gradient magnitude of " + title, out);
		outImg.show();
	}
}
