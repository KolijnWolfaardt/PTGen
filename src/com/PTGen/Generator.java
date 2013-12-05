package com.PTGen;

import java.nio.ByteBuffer;

public class Generator
{

	/*
	 * This function generates Perlin noise to the bytebuffer buf.
	 * 
	 * The size argument indicates the size of the buffer, and should be a 
	 * factor of two.
	 * 
	 * Basefrequency indicates the Lowest frequency to include, should also be 
	 * a power of two, and should be equal or smaller to size. 
	 * 
	 * The buffer size needs to be 4*size*size, since the function is to be 
	 * used to generate Red, Green, Blue and Alpha values.
	 * 
	 * 
	 * The Perlin noise function works by first generating a grid of random 
	 * numbers, and then interpolating between increasingly smaller blocks
	 * selected from the random numbers. Each block size gives a signal with a 
	 * frequency that depends on the block size
	 * 
	 * The different interpolated signals are added together, with weights
	 * inversely proportional to their block size. This means low frequency 
	 * signals, ie. signals with large block sizes are much more pronounced 
	 * than higher frequency signals.
	 * 
	 */
	public static void generate(int size, int basefrequency, ByteBuffer buf)
	{
		//noiseNum holds the generated random values.
		float[][] noiseNum = new float[size][size];
		//signalSums hold the sums of all the different interpolations
		float[][] signalSums = new float[size][size];
		//The current wavelength being added together
		int wavelength = basefrequency;
				
		
		//First, fill the noise array
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				noiseNum[i][j] = (float)(255*Math.random());
			}
		}
		
		//Then, in a loop, take a block, get the corner points, interpolate and add
		while (wavelength >= 2)
		{
			//So say wavelength is 32, size is 64.
			//This means 3 points will be used, We are interrested in points 0, 32 and 64.
			//If wavelength is 8, it is 0,8,16,24 to 64. I am aware the last interval is smaller, but so be it.
			//    |     |
			//  --c-----d --
			//    |     |
			//    |     |
			//  --a-----b--
			//    |     |
			
			
			for (int y = 0; y < size; y++)
			{
				for (int x = 0; x < size; x++)
				{
					//calculate the positions of the corners. It requires integer division
					//These numbers a calculated in every loop, even though they mmight stay the same.
					int smallX = (x/wavelength);
					smallX = smallX*wavelength;
					int largeX = (x/wavelength + 1) * wavelength;
					int smallY = (y/wavelength) * wavelength;
					int largeY = (y/wavelength + 1) * wavelength;
					
					
					//Check if we are outside of array bounds. This happens because size/size ==1, and then we add 1 in the above code
					if (largeX >= size) 
						largeX = size-1;
					if (largeY >= size) 
						largeY = size-1;
					
					//Calculate where we are between two points, in 2 dimensions
					float xPos = ((float)(x-smallX))/((float)(largeX-smallX));
					float yPos = ((float)(y-smallY))/((float)(largeY-smallY));
					
					//Calculate the interpolation, and add it to the signals.
					signalSums[x][y] = signalSums[x][y] +
							interpolateCosineBetween(noiseNum[smallX][smallY], 
										noiseNum[largeX][smallY],
										noiseNum[smallX][largeY], 
										noiseNum[largeX][largeY],
										xPos, yPos) * ((float)wavelength/(float)basefrequency);
					
				}
			}
			wavelength = wavelength/2;
		}		
		
		
		//Place the data in the ByteBuffer
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				//128 is added to each number, because bytes go from -128 to 127, but ByteBuffer is unsigned.
				//f(x, y) = (1 + sin( (x + noise(x * 5 , y * 5 ) / 2 ) * 50) ) / 2
				byte res = (byte)((1.0f+Math.cos(signalSums[i][j]*Math.PI/16))*64.0f);

				buf.put(res);
				buf.put(res);
				buf.put(res);
				//Alpha is 255
				buf.put((byte) 255);
			}
		}
	}
	private static float interpolateCosineBetween(float a,float b,float c, float d,float ratX, float ratY)
	{
		float fX = (float) ((1-Math.cos(ratX*Math.PI))*0.5f); 
		float fY = (float) ((1-Math.cos(ratY*Math.PI))*0.5f); 
		
		float value1 = a*(1.0f-fX)+b*fX;
		float value2 = c*(1.0f-fX)+d*fX;

		return (value1*(1.0f-fY)+value2*fY)/2.0f;
	}
	
	
	private static float interpolateLinearBetween(float a,float b,float c, float d,float ratX, float ratY)
	{
		return (a*(1.0f-ratX)*(1.0f-ratY) + (float)b*(ratX)*(1.0f-ratY) + c*(1.0f-ratX)*(ratY) + d*(ratX)*(ratY))/1.0f;
	}

}
