package com.PTGen;

import java.nio.ByteBuffer;

public class Generator
{

	/*
	 * This function generates a random texture to the ByteBuffer buf.
	 * The size argument indicates
	 * 
	 */
	public static void generate(int size, ByteBuffer buf)
	{
		// TODO Auto-generated method stub
		//Generate some Perlin noise
		//System.out.print((byte)interpolateBetween(0, 255, 0, 0, 0.5f, 0.5f));
		/*
		System.out.print((byte)interpolateBetween(0, 255, 0, 0, 0.5f, 0.5f));
		System.out.print((byte)interpolateBetween(0, 255, 0, 0, 0.5f, 1.0f));
		System.out.print((byte)interpolateBetween(0, 255, 0, 0, 0.0f, 0.5f));
		System.out.print((byte)interpolateBetween(0, 255, 0, 0, 0.5f, 0.0f));
		System.out.print((byte)interpolateBetween(0, 255, 0, 0, 1.0f, 0.5f));*/
		
		byte[][] noiseNum = new byte[size][size];
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				//noiseNum[i][j] = (byte)(255*Math.random());
				noiseNum[i][j] = (byte) interpolateBetween(0, 32, 172, 255, (float)j/(float)size, (float)i/(float)size);
			}
		}
		
		
		
		
		for (int i = 0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				buf.put(noiseNum[i][j]);
				buf.put(noiseNum[i][j]);
				buf.put(noiseNum[i][j]);
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
