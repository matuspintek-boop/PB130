package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin inverts a given 8-bit grayscale image. */
public class My_Inversion implements PlugInFilter 
{
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		// WRITE YOUR CODE HERE
		int pixels_total = ip.getPixelCount();

		for (int i = 0; i < pixels_total; ++i){
			int value = ip.get(i);
			ip.set(i, 255 - value);
		}
	}
}
