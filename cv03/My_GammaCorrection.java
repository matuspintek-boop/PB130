package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.gui.GenericDialog;

/** This plugin applies a gamma correction on a given 8-bit grayscale image. */
public class My_GammaCorrection implements PlugInFilter 
{
	/** The gamma value. */
	static double gamma = 2.2;		

	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{	
		// show the dialog for setting the value of gamma
		if (showDialog())
		{
			// apply the gamma correction
			// the value of gamma given by the user is stored in 'gamma'
			// WRITE YOUR CODE HERE

			int pixels = ip.getPixelCount();

			for (int i = 0; i < pixels; ++i )
			{
				float value = (float) ip.get(i);
				float out = ((float) Math.pow( (double) (value / 255.0f), gamma ) ) * 255.0f;
				int output = (int) Math.round(out);

				ip.set(i, output);
			}
		}
		
	}

	private boolean showDialog()
	{
		GenericDialog dlg = new GenericDialog("Gamma Correction");
		dlg.addNumericField("Gamma value:", gamma, 2);
		dlg.showDialog();

		if (dlg.wasCanceled())
		{
			return false;
		}
		else
		{
			double tmp = dlg.getNextNumber();

			if (tmp > 0.0 && tmp <= 5.0)
			{
				gamma = tmp;
				return true;		
			}
			else
			{
				IJ.error("The gamma value must be within the (0, 5] interval!");
				return false;	
			}
		}
	}
}
