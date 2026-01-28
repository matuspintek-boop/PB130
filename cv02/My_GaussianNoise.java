package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.gui.GenericDialog;
import java.util.Random;

/** This plugin adds the additive Gaussian noise to a given 8-bit grayscale image. */
public class My_GaussianNoise implements PlugInFilter 
{	
	/** The additive Gaussian noise standard deviation. */	
	static double sigma = 10;		
	
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		// show the dialog for setting the standard deviation	
		if (showDialog())
		{	
			// create a generator of pseudorandom numbers
			// the seed is fixed for reproducibility purposes only
			Random r = new Random(0);

			int pixels_total = ip.getPixelCount();

			// add the additive Gaussian noise with the standard deviation 'sigma' given by the user
			// WRITE YOUR CODE HERE
			

			for ( int i = 0; i < pixels_total; ++i )
			{
				int value = ip.get(i);
				value += Math.round(r.nextGaussian()*sigma);

				if ( value > 255 ){
					value = 255;
				} else if ( value < 0 ){
					value = 0;
				}

				ip.set(i, value);
			}
		}	
	}

	private boolean showDialog()
	{
		GenericDialog dlg = new GenericDialog("Additive Gaussian Noise");
		dlg.addNumericField("Standard deviation:", sigma, 2);
		dlg.showDialog();

		if (dlg.wasCanceled())
		{
			return false;
		}
		else
		{
			double tmp = dlg.getNextNumber();

			if (tmp > 0.0 && tmp <= 50.0)
			{
				sigma = tmp;
				return true;		
			}
			else
			{
				IJ.error("The standard deviation must be within the (0, 50] interval!");
				return false;	
			}
		}
	}
}
