package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin increases the contrast of a given 8-bit grayscale image by 50%. */
public class My_IncreaseContrast implements PlugInFilter 
{
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		int pixels_total = ip.getPixelCount();

		for (int i = 0; i < pixels_total; ++ i){
			float value = ip.get(i);
			value *= 1.5;
			value = Math.round(value);
			value = Math.min(value, 255);
			int output = (int) value;
			ip.set(i, output);
		}
	}
}
