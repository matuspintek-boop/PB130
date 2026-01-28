package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/** This plugin automatically validates the My_SaltAndPepperNoise plugin. */
public class UnitTests_SaltAndPepperNoise implements PlugIn 
{	
	int failedTests = 0;
	String rootDir = IJ.getDirectory("plugins") + "PB130/";
	String srcDir = rootDir + "images/";
	String refDir = rootDir + "refs/";	

	public void run(String arg) 
	{
		IJ.open(srcDir + "chessboard.tif");
		IJ.run("Compile and Run...", "compile=" + rootDir + "My_SaltAndPepperNoise.java");
		IJ.wait(500);
		IJ.run("Close All");
		IJ.run("Refresh Menus");
		IJ.log("\\Clear");
		IJ.log("Please wait, running all pre-defined tests...");
		IJ.log("Source image directory: " + srcDir);
		IJ.log("Reference image directory: " + refDir);
		IJ.log("=============================================");
		testPlugin("My SaltAndPepperNoise", "switching=0.1 switching_0=0.4", "chessboard.tif", "noisy_chessboard_01_04.tif");
		testPlugin("My SaltAndPepperNoise", "switching=0.0 switching_0=0.0", "chessboard.tif", "noisy_chessboard_0_0.tif");
		testPlugin("My SaltAndPepperNoise", "switching=0.5 switching_0=0.5", "chessboard.tif", "noisy_chessboard_05_05.tif");
		testPlugin("My SaltAndPepperNoise", "switching=0.35 switching_0=0.2", "tree_rings.tif", "noisy_tree_rings_035_02.tif");

		if (failedTests > 0)
		{
			IJ.log("Total number of tests failed: " + failedTests);
		}
		else
		{
			IJ.log("CONGRATULATIONS! ALL TESTS PASSED.");
		}		
	}

	private void testPlugin(String pluginName, String pluginParams, String srcImage, String refImage)
	{
		IJ.log("Plugin name: " + pluginName);
		IJ.log("Source image: " + srcImage);
		IJ.log("Reference image: " + refImage);
		IJ.open(srcDir + srcImage);
		ImagePlus out = IJ.getImage();
		IJ.run(pluginName, pluginParams);
		IJ.open(refDir + refImage);
		ImagePlus ref = IJ.getImage();
		
		if (compare(ref.getProcessor(), out.getProcessor()))
		{
			IJ.log("Test passed!");
			
		}
		else
		{
			IJ.log("Test failed!");
			++failedTests;
		}

		out.changes = false;
		out.close();
		ref.changes = false;
		ref.close();
		IJ.log("=============================================");
	}

	private boolean compare(ImageProcessor ref, ImageProcessor out)
	{	
		if (ref.getWidth() != out.getWidth() || ref.getHeight() != out.getHeight())
		{
			IJ.log("Error: Inconsistent image size between the reference and tested images!");
			return false;
		}
		
		int num = ref.getPixelCount();

		for (int i = 0; i < num; ++i)
		{
			if (ref.get(i) != out.get(i))
			{
				return false;  		
			}
		}
		
		return true;		
	}

}
