package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin increases the brightness of a given 8-bit grayscale image by 30. */
public class My_IncreaseBrightness implements PlugInFilter 
{
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{

		int pixels_total = ip.getPixelCount();
		// WRITE YOUR CODE HERE
		for (int i = 0; i < pixels_total; ++ i){
			int value = ip.get(i);
			value += 30;
			value = Math.min(value, 255);
			ip.set(i, value);
		}
	}
}
