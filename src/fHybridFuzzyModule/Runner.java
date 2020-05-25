package fHybridFuzzyModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Runner {
	
    public static void main (String args[]) throws IOException
    {
    	FileWriter fw = new FileWriter("Output_Type1_Against_Type2.txt");
        BufferedWriter writeFileBuffer = new BufferedWriter(fw);
        FHybridMemT1FLS fHybridSystemT1 = new FHybridMemT1FLS();
        FHybridMemT2FLS fHybridSystemT2 = new FHybridMemT2FLS();
        
        //get some outputs
        for(int i = 0; i <= 10; i++) {
        	for(int j = 0; j <= 10; j++) {
        		for(int k = 0; k <= 10; k++) {
        			writeFileBuffer.write("Tipo-1:" + fHybridSystemT1.getPromotionValue(i,j,k));
        			writeFileBuffer.write("Tipo-2:" + fHybridSystemT2.getPromotionValue(i,j,k));
        			writeFileBuffer.write("--------------------------------------------------------------------------------------\n");
        		}
        	}
        }
       writeFileBuffer.close();
    }
}
