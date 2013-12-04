package com.PTGen;

import java.nio.ByteBuffer;

public class Generator
{

	/*
	 * This function generates a random texture to the ByteBuffer buf.
	 * The size argument indicates
	 * 
	 */
	public static void generate(int size, int basefrequency, ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		//Generate some Perlin noise
		byte[][] noiseNum = new byte[size][size];
		float[][] signalSums = new float[size][size];
		int wavelength = basefrequency;
		
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				noiseNum[i][j] = (byte)(255*Math.random());
				//noiseNum[i][j] = (byte) interpolateBetween(0, 32, 172, 255, (float)j/(float)size, (float)i/(float)size);
			}
		}
		
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
					int smallX = (x/wavelength);
					smallX = smallX*wavelength;
					int largeX = (x/wavelength + 1) * wavelength;
					int smallY = (y/wavelength) * wavelength;
					int largeY = (y/wavelength + 1) * wavelength;
					
					
					//Check if we are outside of array bounds
					if (largeX >= size) 
						largeX = size-1;
					if (largeY >= size) 
						largeY = size-1;
					
					/*
					System.out.println("SmallX "+smallX);
					System.out.println("SmallY "+smallY);
					System.out.println("LargeX "+largeX);
					System.out.println("LargeY "+largeY);*/
					
					float xPos = ((float)(x-smallX))/((float)(largeX-smallX));
					float yPos = ((float)(y-smallY))/((float)(largeY-smallY));
					
					//Calculate the interpolation
					signalSums[x][y] = signalSums[x][y] +
										interpolateBetween(noiseNum[smallX][smallY], 
										noiseNum[smallX][largeY],
										noiseNum[largeX][smallY], 
										noiseNum[largeX][largeY],
										xPos, yPos) * ((float)wavelength/(float)basefrequency);
					if (signalSums[x][y] > 127)
						signalSums[x][y] = 127.0f;
					if (signalSums[x][y] < -128)
						signalSums[x][y] = -128.0f;
					
				}
			}
			wavelength = wavelength/2;
		}		
		
		
		
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				
				buf.put((byte)(signalSums[i][j]+128));
				buf.put((byte)(signalSums[i][j]+128));
				buf.put((byte)(signalSums[i][j]+128));
				buf.put((byte) 255);
			}
		}
	}
	
	private static float interpolateBetween(int a,int b,int c, int d,float ratX, float ratY)
	{/*
		System.out.println("Res A :" + ((float)a*(1-ratX)*(1-ratY)));
		System.out.println("Res B :" + ((float)b*(ratX)*(1-ratY)));
		System.out.println("Res C :" + ( (float)c*(1-ratX)*(ratY)));
		System.out.println("Res D :" + ((float)d*(ratX)*(ratY)));
		System.out.println();*/
		return  (float)((float)a*(1-ratX)*(1-ratY) + (float)b*(1-ratX)*(ratY) + (float)c*(ratX)*(1-ratY) + (float)d*(ratX)*(ratY))/1.0f;
	}

}
