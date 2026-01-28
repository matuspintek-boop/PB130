package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/** This plugin automatically validates the My_NonMaximaSuppression plugin. */
public class UnitTests_NonMaximaSuppression implements PlugIn 
{	
	int failedTests = 0;
	String plugDir = IJ.getDirectory("plugins") + "PB130/";
	String srcDir = plugDir + "images/";
	String refDir = plugDir + "refs/";	

	public void run(String arg) 
	{
		IJ.open(srcDir + "blobs.tif");
		IJ.run("Compile and Run...", "compile=" + plugDir + "My_NonMaximaSuppression.java");
		IJ.wait(500);
		IJ.run("Close All");
		IJ.run("Refresh Menus");
		IJ.log("\\Clear");
		IJ.log("Please wait, running all pre-defined tests...");
		IJ.log("Source image directory: " + srcDir);
		IJ.log("Reference image directory: " + refDir);
		IJ.log("=============================================");
		testPlugin("My NonMaximaSuppression", "blobs.tif", "blobs_nms.tif");
		testPlugin("My NonMaximaSuppression", "blurred_circle.tif", "blurred_circle_nms.tif");
		testPlugin("My NonMaximaSuppression", "nuts_bolts.tif", "nuts_bolts_nms.tif");
		testPlugin("My NonMaximaSuppression", "orka.tif", "orka_nms.tif");

		if (failedTests > 0)
		{
			IJ.log("Total number of tests failed: " + failedTests);
		}
		else
		{
			IJ.log("CONGRATULATIONS! ALL TESTS PASSED.");
		}		
	}

	private void testPlugin(String pluginName, String srcImage, String refImage)
	{
		IJ.log("Plugin name: " + pluginName);
		IJ.log("Source image: " + srcImage);
		IJ.log("Reference image: " + refImage);
		IJ.open(srcDir + srcImage);
		ImagePlus src = IJ.getImage();
		IJ.run(pluginName);
		ImagePlus out = IJ.getImage();
		IJ.open(refDir + refImage);
		ImagePlus ref = IJ.getImage();
		
		int count = countSubstantialDifferences(ref.getProcessor(), out.getProcessor());
		
		if (count == 0)
		{
			IJ.log("Test passed!");
		}
		else
		{
			IJ.log("Number of differences: " + count);
			IJ.log("Test failed!");
			++failedTests;
		}

		src.changes = false;
		src.close();
		out.changes = false;
		out.close();
		ref.changes = false;
		ref.close();
		IJ.log("=============================================");
	}

	private int countSubstantialDifferences(ImageProcessor ref, ImageProcessor out)
	{	
		if (ref.getWidth() != out.getWidth() || ref.getHeight() != out.getHeight())
		{
			IJ.log("Error: Inconsistent image size between the reference and tested images!");
			return ref.getPixelCount();
		}

		int count = 0;		
		int num = ref.getPixelCount();

		for (int i = 0; i < num; ++i)
		{
			if (Math.abs(ref.getf(i) - out.getf(i)) > 0.001f)
			{
				++count;
			}
		}
		
		return count;		
	}

}
