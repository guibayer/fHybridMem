/*
 * SimpleT1FLS.java
 *
 * Created on May 20th 2012
 *
 * Copyright 2012 Christian Wagner All Rights Reserved.
 */
package fHybridFuzzyModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import generic.Input;
import generic.Output;
import generic.Tuple;
import intervalType2.sets.IntervalT2MF_Interface;
import intervalType2.sets.IntervalT2MF_Trapezoidal;
import intervalType2.sets.IntervalT2MF_Triangular;
import intervalType2.system.IT2_Antecedent;
import intervalType2.system.IT2_Consequent;
import intervalType2.system.IT2_Rule;
import intervalType2.system.IT2_Rulebase;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Trapezoidal;
import type1.sets.T1MF_Triangular;
import type1.system.T1_Consequent;

/**
 * A simple example of a type-1 FLS based on the "How much to tip the waiter"
 *  scenario.
 * We have two inputs: food quality and service level and as an output we would
 * like to generate the applicable tip.
 * @author Christian Wagner
 */
public class FHybridMemT2FLS
{
    Input recencyOfAccess, readFrequency, writeFrequency;    //the inputs to the FLS
    Output promotion;             //the output of the FLS
    IT2_Rulebase rulebase;   //the rulebase captures the entire FLS
    
    StringBuffer sbf;//String Buffer to select all the text that will be printed on a file
    BufferedWriter bwr;
        
    public FHybridMemT2FLS()
    {
        //Define the inputs
    	recencyOfAccess = new Input("Recency of Access Level", new Tuple(0,10));
        readFrequency = new Input("Read Frequency Level", new Tuple(0,10)); 
        writeFrequency = new Input("Write Frequency Level", new Tuple(0,10));
        promotion = new Output("Promotion", new Tuple(0,100));               //a percentage for the tip
        
        //High Functions using the article functions with c constant equal to 0.4
        //T1MF_Trapezoidal("MF for High Recency of Access", new double[] {2, 4, 6, 8});
        T1MF_Trapezoidal highROAUMF = new T1MF_Trapezoidal("Upper MF for High Recency of Access", new double[] {0, 0, 2.4, 4.4});
        T1MF_Trapezoidal highROALMF = new T1MF_Trapezoidal("Lower MF for High Recency of Access", new double[] {0, 0, 2.4, 3.6}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal highROAMF = new IntervalT2MF_Trapezoidal("IT2MF for High Recency of Access", highROAUMF, highROALMF);
        
        //Medium
        T1MF_Trapezoidal mediumROAUMF = new T1MF_Trapezoidal("Upper MF for Medium Recency of Access", new double[] {1.6, 3.6, 4.4, 6.4});
        T1MF_Trapezoidal mediumROALMF = new T1MF_Trapezoidal("Lower MF for Medium Recency of Access", new double[] {2.4, 3.6, 4.4, 5.6}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal mediumROAMF = new IntervalT2MF_Trapezoidal("IT2MF for Medium Recency of Access", mediumROAUMF, mediumROALMF);
        
        //Low
        T1MF_Trapezoidal lowROAUMF = new T1MF_Trapezoidal("Upper MF for Low Recency of Access", new double[] {3.6, 5.6, 10, 10});
        T1MF_Trapezoidal lowROALMF = new T1MF_Trapezoidal("Lower MF for Low Recency of Access", new double[] {4.4, 5.6, 10, 10}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal lowROAMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Recency of Access", lowROAUMF, lowROALMF);

        //plotMFs("Recency of Access Membership Functions", new IntervalT2MF_Interface[]{highROAMF, mediumROAMF, lowROAMF}, 100);        
        
        //Set up the membership functions (MFs) for each input and output
        // Pontos da equação do matlab que são da upper: 1, 2, 7, 8 (Substituindo valores acima de 10 e abaixo de 0 pelos limites da variável)
        // Pontos da equação do matlab que são da lower: 5, 6, 3, 4
        //Low
       
        T1MF_Trapezoidal lowReadFrequencyUMF = new T1MF_Trapezoidal("Upper MF for Low Read Frequency", new double[] {0, 0, 2.6, 5.6});
        T1MF_Trapezoidal lowReadFrequencyLMF = new T1MF_Trapezoidal("Lower MF for Low Read Frequency", new double[] {0, 0, 2.6, 4.4}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal lowReadFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Read Frequency", lowReadFrequencyUMF, lowReadFrequencyLMF);
        
        //Medium
        T1MF_Trapezoidal mediumReadFrequencyUMF = new T1MF_Trapezoidal("Upper MF for Medium Read Frequency", new double[] {1.6, 3.6, 6.4, 8.4});
        T1MF_Trapezoidal mediumReadFrequencyLMF = new T1MF_Trapezoidal("Lower MF for Medium Read Frequency", new double[] {2.4, 3.6, 6.4, 7.6}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal mediumReadFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for Medium Read Frequency", mediumReadFrequencyUMF, mediumReadFrequencyLMF);
        
        //High
        T1MF_Trapezoidal highReadFrequencyUMF = new T1MF_Trapezoidal("Upper MF for High Read Frequency", new double[] {4.4, 7.4, 10, 10});
        T1MF_Trapezoidal highReadFrequencyLMF = new T1MF_Trapezoidal("Lower MF for High Read Frequency", new double[] {5.6, 7.4, 10, 10}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal highReadFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for High Read Frequency", highReadFrequencyUMF, highReadFrequencyLMF);
        
        //plotMFs("Read Frequency Membership Functions", new IntervalT2MF_Interface[]{lowReadFrequencyMF, mediumReadFrequencyMF, highReadFrequencyMF}, 100); 
                
        //Set up the membership functions (MFs) for each input and output
        // Pontos da equação do matlab que são da upper: 1, 2, 7, 8 (Substituindo valores acima de 10 e abaixo de 0 pelos limites da variável)
        // Pontos da equação do matlab que são da lower: 5, 6, 3, 4
        //Low
        T1MF_Trapezoidal lowWriteFrequencyUMF = new T1MF_Trapezoidal("Upper MF for Low Write Frequency", new double[] {0, 0, 2.6, 5.6});
        T1MF_Trapezoidal lowWriteFrequencyLMF = new T1MF_Trapezoidal("Lower MF for Low Write Frequency", new double[] {0, 0, 2.6, 4.4}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal lowWriteFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Write Frequency", lowWriteFrequencyUMF, lowWriteFrequencyLMF);
        
        //Medium
        T1MF_Trapezoidal mediumWriteFrequencyUMF = new T1MF_Trapezoidal("Upper MF for Medium Write Frequency", new double[] {1.6, 3.6, 6.4, 8.4});
        T1MF_Trapezoidal mediumWriteFrequencyLMF = new T1MF_Trapezoidal("Lower MF for Medium Write Frequency", new double[] {2.4, 3.6, 6.4, 7.6}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal mediumWriteFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for Medium Write Frequency", mediumWriteFrequencyUMF, mediumWriteFrequencyLMF);
        
        //High
        T1MF_Trapezoidal highWriteFrequencyUMF = new T1MF_Trapezoidal("Upper MF for High Write Frequency", new double[] {4.4, 7.4, 10, 10});
        T1MF_Trapezoidal highWriteFrequencyLMF = new T1MF_Trapezoidal("Lower MF for High Write Frequency", new double[] {5.6, 7.4, 10, 10}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal highWriteFrequencyMF = new IntervalT2MF_Trapezoidal("IT2MF for High Write Frequency", highWriteFrequencyUMF, highWriteFrequencyLMF);
        
        //plotMFs("Write Frequency Membership Functions", new IntervalT2MF_Interface[]{lowWriteFrequencyMF, mediumWriteFrequencyMF, highWriteFrequencyMF}, 100); 
    
        
        //Set up the membership functions (MFs) for each input and output
        // Pontos da equação do matlab que são da upper: 1, 2, 7, 8 (Substituindo valores acima de 10 e abaixo de 0 pelos limites da variável)
        // Pontos da equação do matlab que são da lower: 5, 6, 3, 4
        //Low
        T1MF_Trapezoidal lowPromotionUMF = new T1MF_Trapezoidal("Upper MF for Low Promotion", new double[] {0, 0, 1, 6});
        T1MF_Trapezoidal lowPromotionLMF = new T1MF_Trapezoidal("Lower MF for Low Promotion", new double[] {0, 0, 1, 4}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal lowPromotionMF = new IntervalT2MF_Trapezoidal("IT2MF for Low Promotion", lowPromotionUMF, lowPromotionLMF);
             
        //Average
        T1MF_Trapezoidal averagePromotionUMF = new T1MF_Trapezoidal("Upper MF for Average Promotion", new double[] {0, 4, 6, 10});
        T1MF_Trapezoidal averagePromotionLMF = new T1MF_Trapezoidal("Lower MF for Average Promotion", new double[] {1, 4, 6, 9}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal averagePromotionMF = new IntervalT2MF_Trapezoidal("IT2MF for Average Promotion", averagePromotionUMF, averagePromotionLMF);
    
        //High
        T1MF_Trapezoidal highPromotionUMF = new T1MF_Trapezoidal("Upper MF for High Promotion", new double[] {4, 9, 10, 10});
        T1MF_Trapezoidal highPromotionLMF = new T1MF_Trapezoidal("Lower MF for High Promotion", new double[] {6, 9, 10, 10}, new double[] {0.6, 0.6});
        IntervalT2MF_Trapezoidal highPromotionMF = new IntervalT2MF_Trapezoidal("IT2MF for High Promotion", highPromotionUMF, highPromotionLMF);
        
        //plotMFs("Promotion Membership Functions", new IntervalT2MF_Interface[]{lowPromotionMF, averagePromotionMF, highPromotionMF}, 100); 
        
        //Set up the antecedents and consequents - note how the inputs are associated...
        IT2_Antecedent highROA = new IT2_Antecedent("HighROA", highROAMF, recencyOfAccess);
        IT2_Antecedent mediumROA = new IT2_Antecedent("MediumROA", mediumROAMF, recencyOfAccess);
        IT2_Antecedent lowROA = new IT2_Antecedent("LowROA", lowROAMF, recencyOfAccess);

        IT2_Antecedent lowReadFrequency = new IT2_Antecedent("LowRF", lowReadFrequencyMF, readFrequency);
        IT2_Antecedent mediumReadFrequency = new IT2_Antecedent("MediumRF", mediumReadFrequencyMF, readFrequency);
        IT2_Antecedent highReadFrequency = new IT2_Antecedent("HighRF", highReadFrequencyMF, readFrequency);
        
        IT2_Antecedent lowWriteFrequency = new IT2_Antecedent("LowRF", lowWriteFrequencyMF, writeFrequency);
        IT2_Antecedent mediumWriteFrequency = new IT2_Antecedent("MediumRF", mediumWriteFrequencyMF, writeFrequency);
        IT2_Antecedent highWriteFrequency = new IT2_Antecedent("HighRF", highWriteFrequencyMF, writeFrequency);

        IT2_Consequent lowPromotion = new IT2_Consequent("LowTip", lowPromotionMF, promotion);
        IT2_Consequent mediumPromotion = new IT2_Consequent("MediumTip", averagePromotionMF, promotion);
        IT2_Consequent highPromotion = new IT2_Consequent("HighTip", highPromotionMF, promotion);

        //Set up the rulebase and add rules
        rulebase = new IT2_Rulebase(27);
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, lowReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, lowReadFrequency, mediumWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, lowReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, mediumReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, mediumReadFrequency, mediumWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, mediumReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, highReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, highReadFrequency, mediumWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{highROA, highReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, lowReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, lowReadFrequency, mediumWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, lowReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, mediumReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, mediumReadFrequency, mediumWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, mediumReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, highReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, highReadFrequency, mediumWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{mediumROA, highReadFrequency, highWriteFrequency}, highPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, lowReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, lowReadFrequency, mediumWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, lowReadFrequency, highWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, mediumReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, mediumReadFrequency, mediumWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, mediumReadFrequency, highWriteFrequency}, mediumPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, highReadFrequency, lowWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, highReadFrequency, mediumWriteFrequency}, lowPromotion));
        rulebase.addRule(new IT2_Rule(new IT2_Antecedent[]{lowROA, highReadFrequency, highWriteFrequency}, mediumPromotion));
       
        
        //just an example of setting the discretisation level of an output - the usual level is 100
        promotion.setDiscretisationLevel(1000);        
            
        //plot some sets, discretizing each input into 100 steps.
        //plotMFs("Recency of Access Membership Functions", new IntervalT2MF_Interface[]{highROAMF, mediumROAMF, lowROAMF}, 1000);        
        //plotMFs("Read Frequency Membership Functions", new IntervalT2MF_Interface[]{lowReadFrequencyMF, mediumReadFrequencyMF, highReadFrequencyMF}, 1000); 
        //plotMFs("Write Frequency Membership Functions", new IntervalT2MF_Interface[]{lowWriteFrequencyMF, mediumWriteFrequencyMF, highWriteFrequencyMF}, 1000); 
        //plotMFs("Promotion Membership Functions", new IntervalT2MF_Interface[]{lowPromotionMF, averagePromotionMF, highPromotionMF}, 1000); 
        
        
        //plot control surface
        //do either height defuzzification (false) or centroid d. (true)
        //plotControlSurface(false, 100, 100);
        
        //print out the rules
        //System.out.println("\n"+rulebase);        
    }
    
    /**
     * Basic method that prints the output for a given set of inputs.
     * @param foodQuality
     * @param serviceLevel 
     */
    public String getPromotionValue(double recencyOfAccessLevel, double readFrequencyLevel, double writeFrequencyLevel)
    {
    	String outputString;
       //first, set the inputs
       recencyOfAccess.setInput(recencyOfAccessLevel);
	   readFrequency.setInput(readFrequencyLevel);
	   writeFrequency.setInput(writeFrequencyLevel);
	   //now execute the FLS
	   //Using height defuzzification:
	   //rulebase.evaluate(0).get(promotion); 
	   //Using centroid defuzzification
	   //rulebase.evaluate(1).get(promotion)
       /*System.out.println("ROA: "+ recencyOfAccess.getInput()
       + " | RF: " + readFrequency.getInput()
       + " | WF: " + writeFrequency.getInput() 
       + " | Promotion: " + rulebase.evaluate(1).get(promotion)
    		   ); 
        */
	   outputString = String.format("ROA: %f | RF: %f | WF: %f  | Promotion: %f \n",
        recencyOfAccess.getInput(), readFrequency.getInput(), writeFrequency.getInput(), rulebase.evaluate(1).get(promotion));
	   
	   return outputString;
    		   
    }
       
    private void plotMFs(String name, IntervalT2MF_Interface[] sets, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter();
        plotter.plotMF(sets[0].getName(), sets[0], discretizationLevel, null, false);
       
        for (int i=1;i<sets.length;i++)
        {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, null, false);
        }
        plotter.show(name);
    }
}
