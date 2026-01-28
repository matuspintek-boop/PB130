package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** This plugin thresholds a given 8-bit grayscale image using the triangle method. */
public class My_UnimodalThresholding implements PlugInFilter 
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
		// create the output image as a copy of the input one
		ImageProcessor out = ip.duplicate();

		// the number of pixels in the image 'ip'
		int num = ip.getPixelCount();

		// threshold
		int thresh = 0;

		// compute the intensity histogram and find the minimum and maximum intensities
		int min = ip.get(0);
		int max = ip.get(0);
		int[] hist = new int[256];
		int value = 0;

		for (int i = 0; i < num; ++i)
		{
			value = ip.get(i);
			++hist[value];

			if (value < min)
			{
				min = value;
			}
			else if (value > max)
			{		
				max = value;
			}
		}

		// find a suitable threshold 'thresh' for the image 'ip' using the triangle method
		// the distance between a point [sx, sy] and a line a * x + b * y + c = 0 is given as |a * sx + b * sy + c| / sqrt(a * a + b * b)
		// threshold the image 'ip' with the computed threshold 'thresh' (<thresh -> background, >=thresh -> foreground)
		// write the thresholded image into the image 'out' (background -> 0, foreground -> 255)
		// WRITE YOUR CODE HERE
		int hightestPoint = min;
		for (int i = min; i <= max; i++)
		{
			if (hist[hightestPoint] < hist[i])
			{
				hightestPoint = i;
			}
		}

		int secondPoint;

		if (hightestPoint - min > max - hightestPoint){
			secondPoint = min;
		} else{
			secondPoint = max;
		}


		int a = hist[secondPoint] - hist[hightestPoint];
		int b = hightestPoint - secondPoint;

		// c = -a * x1 - b * y1
		int c = -a * secondPoint - b * hist[secondPoint];

		double maxDistance = 0.0;
		thresh = secondPoint;

		int start = Math.min(secondPoint, hightestPoint);
		int end = Math.max(secondPoint, hightestPoint);

		for (int i = start; i <= end; ++i)
		{
			int sx = i;
			int sy = hist[i];

			double distance = Math.abs(a * sx + b * sy + c); 

			if (distance > maxDistance)
			{
				maxDistance = distance;
				thresh = sx;
			}
		}

		for (int i = 0; i < num; ++i)
		{
			value = ip.get(i);
			
			if (value < thresh)
			{
				out.set(i, 0);
			}
			else
			{
				out.set(i, 255);
			}
		}
		// NO CHANGES NEEDED AFTER THIS LINE

		// show the output image
		ImagePlus outImg = new ImagePlus(String.format("My unimodal thresholding (thresh=%d) of ", thresh) + title, out);
		outImg.show();
	}
}
