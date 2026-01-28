package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;

/** This plugin suppresses all non-maxima in the gradient magnitude of a given 8-bit grayscale image approximated using central differences. */
public class My_NonMaximaSuppression implements PlugInFilter 
{
	String title = null;

	public int setup(String arg, ImagePlus im) 
	{
		if (im != null)
		{
			// store the short image title for future use
			title = im.getShortTitle();
			
			// this plugin accepts 8-bit grayscale images
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

		// suppress all non-maxima in the gradient magnitude of 'ip' approximated using central differences
		// store the result in the output image 'out'
		// WRITE YOUR CODE HERE
		for (int v = 1; v < h - 1; v++){
			for (int u = 1; u < w - 1; u++)
			{
				// compute partial gradients
				float gradientX = .5f * (ip.getf(u+1, v) - ip.getf(u-1, v));
				float gradientY = .5f * (ip.getf(u, v+1) - ip.getf(u, v-1));

				float gradientAmp = (float) Math.sqrt(gradientX*gradientX + gradientY * gradientY);


				float orientation = (float) Math.toDegrees(Math.atan2(gradientY, gradientX));
				if (orientation < 0) orientation += 180;

            	int x1 = u, y1 = v;
            	int x2 = u, y2 = v;

				if ((0 <= orientation && orientation < 22.5) || (157.5 <= orientation && orientation <= 180))
				{
					x1 = u + 1; y1 = v;
					x2 = u - 1; y2 = v;
                } else if (22.5 <= orientation && orientation < 67.5)
				{
                	x1 = u + 1; y1 = v - 1;
                	x2 = u - 1; y2 = v + 1;
                } else if (67.5 <= orientation && orientation < 112.5)
				{	
                	x1 = u; y1 = v + 1;
                	x2 = u; y2 = v - 1;
                } else if (112.5 <= orientation && orientation < 157.5)
				{
                	x1 = u - 1; y1 = v - 1;
                	x2 = u + 1; y2 = v + 1;
                }
				
				float neighborAmp1 = 0, neighborAmp2 = 0;

				if (x1 > 0 && x1 < w - 1 && y1 > 0 && y1 < h - 1) {
					float gx = 0.5f * (ip.getf(x1 + 1, y1) - ip.getf(x1 - 1, y1));
					float gy = 0.5f * (ip.getf(x1, y1 + 1) - ip.getf(x1, y1 - 1));
					neighborAmp1 = (float) Math.sqrt(gx * gx + gy * gy);
				}

				if (x2 > 0 && x2 < w - 1 && y2 > 0 && y2 < h - 1) {
					float gx = 0.5f * (ip.getf(x2 + 1, y2) - ip.getf(x2 - 1, y2));
					float gy = 0.5f * (ip.getf(x2, y2 + 1) - ip.getf(x2, y2 - 1));
					neighborAmp2 = (float) Math.sqrt(gx * gx + gy * gy);
				}

				// suppress non-maxima
				if (gradientAmp > neighborAmp1 && gradientAmp > neighborAmp2) {
					out.setf(u, v, gradientAmp);
				} else {
					out.setf(u, v, 0);
				}
			}
		} 
		// NO CHANGES NEEDED AFTER THIS LINE

		// show the output image
		ImagePlus outImg = new ImagePlus("My non-maxima suppression of " + title, out);
		outImg.show();
	}

}
