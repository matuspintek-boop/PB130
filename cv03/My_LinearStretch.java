package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin applies the linear stretch on a given 8-bit grayscale image. */
public class My_LinearStretch implements PlugInFilter 
{
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		int pixels_total = ip.getPixelCount();
		int max = 0;
		int min = 255;

		for (int i = 0; i < pixels_total; ++i)
		{
			int value = ip.get(i);
			min = Math.min(min, value);
			max = Math.max(max, value);
		}

		if (max > min)
		{
			float scale = 255.0f / (max - min);


			for (int i = 0; i < pixels_total; ++i)
			{
				int v = ip.get(i);
        		int out = Math.round((v - min) * scale);
        		ip.set(i, out);

			}
		}
	}
}
