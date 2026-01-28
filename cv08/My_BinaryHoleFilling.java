package PB130;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import java.util.Queue;
import java.util.ArrayDeque;

/** This class encapsulates the coordinates of a single pixel. */
class Pixel
{
	/** Pixel coordinates. */
	int x, y;

	/** Constructor. */
	Pixel(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

/** This plugin fills holes in a given binary image by emulating morphological reconstruction via flood filling. */
public class My_BinaryHoleFilling implements PlugInFilter 
{	
	final static int BACKGROUND_LABEL = 0; // alias for the background label of a binary image
	final static int TMP_LABEL = 128; // alias for the temporary label used during flood filling

	public int setup(String arg, ImagePlus im) 
	{
		// this plugin accepts binary images
		return DOES_8G; 
	}

	public void run(ImageProcessor ip) 
	{	
		// go through the one-pixel image border of 'ip' and apply flood filling to all background pixels
		// represented by BACKGROUND_LABEL, relabeling them to TMP_LABEL and storing the intermediate output in 'ip'
		// binarize the intermediate output (TMP_LABEL -> 0, otherwise -> 255) directly in 'ip'
		// WRITE YOUR CODE HERE

		int width = ip.getWidth();
		int height = ip.getHeight();
		int pixelsTotal = ip.getPixelCount();

		int[] yCoordinates = {0, height - 1};	

		for (int x = 0; x < width; x++)
		{
			for (int k = 0; k < 2; k++)
			{
				if (ip.get(x, yCoordinates[k]) == BACKGROUND_LABEL) floodFill(ip, x, yCoordinates[k], TMP_LABEL);
			}
		}

		int[] xCoordinates = {0, width - 1};

		for(int y = 0; y < height; y++)
		{
			for (int k = 0; k < 2; k++)
			{
				if (ip.get(xCoordinates[k], y) == BACKGROUND_LABEL) floodFill(ip, xCoordinates[k], y, TMP_LABEL);
			}
		}

		for (int i = 0; i < pixelsTotal; i++)
		{
			if (ip.get(i) == TMP_LABEL)
			{
				ip.set(i, 0);
			} else {
				ip.set(i, 255);
			}
		}

	}

	private void floodFill(ImageProcessor out, int x, int y, int label)
	{
		// create an empty queue
		Queue<Pixel> q = new ArrayDeque<Pixel>();

		// to add a pixel with coordinates 'x' and 'y' to the tail of the queue 'q', call q.add(new Pixel(x, y))
		// to check if the queue 'q' is empty, call q.isEmpty()
		// to remove a pixel from the head of the queue 'q' and store it in the variable 'n', call n = q.remove()
		// WRITE YOUR CODE HERE

		int w = out.getWidth();
    	int h = out.getHeight();

		out.set(x, y, label);
		q.add(new Pixel(x, y));

		int[] xDistance = {1, -1, 0, 0};
		int[] yDistance = {0, 0, 1, -1};

		while (!q.isEmpty())
		{
			Pixel n = q.remove();

			for (int k = 0; k < 4; ++k)
			{
				int nx = n.x + xDistance[k];
				int ny = n.y + yDistance[k];


				if (nx >= 0 && nx < w && ny >= 0 && ny < h)
				{
	
					if (out.get(nx, ny) == BACKGROUND_LABEL)
					{
						out.set(nx, ny, label);
						q.add(new Pixel(nx, ny));
					}
				}
			}
		}
	}
}
