package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.util.Random;
import ij.gui.GenericDialog;

/** This plugin degrades a given 8-bit grayscale image by the salt and pepper noise with the predefined probabilities for salt and for pepper. */
public class My_SaltAndPepperNoise implements PlugInFilter 
{	
	/** The switching probability for salt. */	
	static double prob_salt = 0.1;		

	/** The switching probability for pepper. */	
	static double prob_pepper = 0.4;		
	
	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts 8-bit grayscale images only
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{
		// show the dialog for setting the switching probabilities
		if (showDialog())
		{
			// create a generator of pseudorandom numbers
			// the seed is fixed for reproducibility purposes only
			Random r = new Random(0);

			// degrade the image by the salt and pepper noise
			// the switching probability for salt and for pepper is stored in 'prob_salt' and in 'prob_pepper', respectively
			// to generate a new uniformly distributed random value within the [0, 1) interval, you may call 'r.nextDouble()'
			// WRITE YOUR CODE HERE

			int pixels = ip.getPixelCount();
			for (int i = 0; i < pixels; ++i)
			{
				double probability = r.nextDouble();
				if (probability < prob_salt){
					ip.set(i, 255);
					} else if(probability < prob_salt+prob_pepper){
						ip.set(i, 0);
					}
			}
		}
	}

	private boolean showDialog()
	{
		GenericDialog dlg = new GenericDialog("Salt and Pepper Noise");
		dlg.addNumericField("Switching probability for salt:", prob_salt, 2);
		dlg.addNumericField("Switching probability for pepper:", prob_pepper, 2);
		dlg.showDialog();

		if (dlg.wasCanceled())
		{
			return false;
		}
		else
		{
			double tmp1 = dlg.getNextNumber();
			double tmp2 = dlg.getNextNumber();

			if (tmp1 >= 0.0 && tmp1 < 1.0)
			{
				prob_salt = tmp1;	
			}
			else
			{
				IJ.error("The switching probability for salt must be within the [0, 1) interval!");
				return false;	
			}

			if (tmp2 >= 0.0 && tmp2 < 1.0)
			{
				prob_pepper = tmp2;	
			}
			else
			{
				IJ.error("The switching probability for pepper must be within the [0, 1) interval!");
				return false;	
			}

			if (prob_salt + prob_pepper <= 1.0)
			{
				return true;
			}
			else
			{
				IJ.error("The sum of the switching probabilities cannot be higher than 1.0!");
				return false;
			}
		}
	}
}
