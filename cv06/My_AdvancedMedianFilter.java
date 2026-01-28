package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;
import java.util.ArrayList;
import java.util.Collections;

/** This plugin applies advanced median filtering with a square neighborhood on a given 8-bit grayscale image corrupted with salt-and-pepper noise. The filter is applied to the noise-corrupted pixels only, working only with the noise-uncorrupted pixels in the neighborhood. */
public class My_AdvancedMedianFilter implements PlugInFilter 
{
	/** The square neighborhood radius. */	
	static int radius = 1;		

	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		if (showDialog())
		{
			// duplicate the image
			ImageProcessor tmp = ip.duplicate();

			// initialize a list for storing values in the square neighborhood
			ArrayList<Integer> neighbors = new ArrayList<>();

			// WRITE YOUR CODE HERE
			int width = ip.getWidth();
			int height = ip.getHeight();

			for (int v = 0; v < height; ++v)
			{
				for (int u = 0; u < width; ++u)
				{
					// if value isn't salt or pepper i will use the original
					if (tmp.get(u, v) > 0 && tmp.get(u, v) < 255) 
					{
						continue;
					}
					// otherwise the median from not salt&pepper neighbors is needed
        			neighbors.clear();
					for (int j = Math.max(v - radius, 0); j <= Math.min(v + radius, height - 1); ++j) 
					{

						for (int i = Math.max(0, u - radius); i <= Math.min(u + radius, width - 1); ++i) 
						{
							int val = tmp.get(i, j);

							if (val > 0 && val < 255)
							{
								neighbors.add(val);
							}
						}
					}
					// setting pixel to median value
					if (!neighbors.isEmpty()) 
					{
						ip.set(u, v, getMedian(neighbors));
					}
				}
			}
		}
	}

	/** Get the median value of a given non-empty list of integers. */
	private int getMedian(ArrayList<Integer> a)
	{
		// sort the list 'a'
		Collections.sort(a);

		// the length of the list 'a'
		int len = a.size();
		
		// return the median value of the sorted list 'a'
		// to get the i-th value in the list, call a.get(i)
		// WRITE YOUR CODE HERE
		if (len % 2 == 1)
		{
			return (a.get(len/2));
		}
		int lo = a.get(len / 2 - 1);
        int hi = a.get(len / 2);
        return (lo + hi + 1) / 2;
	}

	private boolean showDialog()
	{
		GenericDialog dlg = new GenericDialog("Advanced Median Filtering");
		dlg.addNumericField("Square neighborhood radius:", radius, 0);
		dlg.showDialog();

		if (dlg.wasCanceled())
		{
			return false;
		}
		else
		{
			double tmp = dlg.getNextNumber();

			if (tmp >= 1.0)
			{
				radius = (int)tmp;
				return true;		
			}
			else
			{
				IJ.error("The radius must be a positive integer!");
				return false;	
			}
		}
	}
}
