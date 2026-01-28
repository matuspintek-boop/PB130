package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin flips a given image vertically. */
public class My_FlipY implements PlugInFilter 
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
		int range = (int) Math.ceil(h/2);

		for (int i = 0; i < range; ++ i)
		{
			for (int x = 0; x < w; ++ x)
			{
				int top = i;
				int bottom = h - (i +1);
				int buffer = ip.get(x, top);

				ip.set(x, top, ip.get(x, bottom));
				ip.set(x , bottom, buffer);
			}
		}
	}
}
