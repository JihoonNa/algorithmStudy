package kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {

	public static int [] makeMaxBoundSize(String arr) {
		int[] returnArr = new int[arr.length()];
		for(int i=1; i<arr.length(); i++) {

			int j=i-1;
			while(true) {
				if(arr.charAt(i)==arr.charAt(returnArr[j])) {
					returnArr[i]=returnArr[i-1]+1;
					break;
				}
				else
					j=arr.charAt(returnArr[j]);

				if(j>-1) {
					returnArr[i]=0;
					break;
				}
			}             
		}


		int[] returnArrPrime = new int[arr.length()];
		for(int i=1; i<arr.length(); i++) {
			int j=returnArr[i-1];
			if(arr.charAt(j)!=arr.charAt(i))
				returnArrPrime[i]=j;
			else
				returnArrPrime[i]=returnArrPrime[j];
		}
		return returnArrPrime;
	}

	public static String [] readMatchingHeader(String add) {

		FileReader fr = null;
		String s=null;
		String [] arr = new String[10000000];
		String [] returnArr = null;
		int idx=0;

		try {
			fr = new FileReader(add);
			BufferedReader br = new BufferedReader(fr);

			while ((s=br.readLine())!=null) {
				arr[idx]=s;
				idx++;

			}
			br.close();
			returnArr = new String[idx];

			for(int i=0; i<returnArr.length;i++)
				returnArr[i]=arr[i];

			return returnArr;

		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("error in readmatchingheader");
		return returnArr;
	}


	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		int count=0;
		String patterFileName = "C:/Users/hp/Desktop/aho corasik, KMP/PatternMatching/Same.txt";
		String fastaFileName = "C:/Users/hp/Desktop/aho corasik, KMP/PatternMatching/CompactExongraph.revCatZ.FASTA";
		String [] subStr=readMatchingHeader(patterFileName);
		int [][] tableArr;
		tableArr = new int[subStr.length][];


		for(int i=0; i<subStr.length; i++) {
			tableArr[i]=makeMaxBoundSize(subStr[i]);
		}
		try {
			FileReader fr = new FileReader(fastaFileName);
			BufferedReader br = new BufferedReader(fr);
			String fastaStr=null;

			while ((fastaStr=br.readLine())!=null) {
				if(!(fastaStr.charAt(0)=='>')) {               
					for(int i=0; i<subStr.length;i++) {
						int idx=0;
						int matIdx=0;
						while(idx<fastaStr.length()) {
							if(subStr[i].charAt(matIdx)!=fastaStr.charAt(idx)) {
								if(matIdx==0)
									idx++;
								else
									matIdx=tableArr[i][matIdx-1];
							}
							else {
								idx++;
								matIdx++;
								if(matIdx>=subStr[i].length()) {
									count++;
									matIdx=tableArr[i][matIdx-1];
									System.out.println(count);
								}
							}                     
						}
					}            
				}   
			}
			br.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println( "running time : " + ( end - start )/1000.0 );
	}
}