package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin flips a given image horizontally as well as vertically in a single image pass. */
public class My_FlipXY implements PlugInFilter 
{
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts all supported image types
		return DOES_ALL; 
	}

	public void run(ImageProcessor ip) 
	{
		int w = ip.getWidth();
		int h = ip.getHeight();
		int total = ip.getPixelCount();
		int range = (int) Math.ceil(total / 2);

		for (int y = 0; y <  range ; ++y)
		{
			int top = y;
			int bottom = total - (y + 1);
			int buffer = ip.get(y);

			ip.set(top, ip.get(bottom));
			ip.set(bottom, buffer);
			
		}
	}
}
