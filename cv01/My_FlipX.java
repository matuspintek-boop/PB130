package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin flips a given image horizontally. */
public class My_FlipX implements PlugInFilter 
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
		int range = (int) Math.ceil(w/2);

		for (int y = 0; y < h; ++ y)
		{
			for (int i = 0; i < range; ++ i)
			{
				int left = i;
				int right = w - (i +1);
				int buffer = ip.get(left, y);

				ip.set(left, y, ip.get(right, y));
				ip.set(right, y, buffer);
			}
		}
	}
}
