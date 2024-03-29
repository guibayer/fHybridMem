package fHybridFuzzyModule;

import java.util.Comparator;

public class MemoryPage {
	
	private double recencyOfAccessValue, readFrequencyValue, writeFrequencyValue;
	
	//Posição 0 será o inferior e 1 o valor superior
	private double[] outputValue = new double[2];

	public MemoryPage(double recencyOfAccessValue, double readFrequencyValue, double writeFrequencyValue) {
		this.recencyOfAccessValue = recencyOfAccessValue;
		this.readFrequencyValue = readFrequencyValue;
		this.writeFrequencyValue = writeFrequencyValue;	
	}
	
	public MemoryPage(double recencyOfAccessValue, double readFrequencyValue, double writeFrequencyValue,
			double outputInfValue, double outputSupValue) {
		this.recencyOfAccessValue = recencyOfAccessValue;
		this.readFrequencyValue = readFrequencyValue;
		this.writeFrequencyValue = writeFrequencyValue;
		this.outputValue[0] = outputInfValue;
		this.outputValue[1] = outputSupValue;	
	}
	
	public static Comparator<MemoryPage> myMemoryPageComparator = new Comparator<MemoryPage>() {

		public int compare(MemoryPage page1, MemoryPage page2) {
		   //Double StudentName1 = page1.getOutputInfValue();
		   //Double StudentName2 = page2.getOutputInfValue();

			// XU e YAGER
	       if (((page1.getOutputInfValue() + page1.getOutputSupValue()) < (page2.getOutputInfValue() + page2.getOutputSupValue()))
	    		 || (((page1.getOutputInfValue() + page1.getOutputSupValue()) == (page2.getOutputInfValue() + page2.getOutputSupValue())) 
	    		 	&& ((page1.getOutputSupValue() - page1.getOutputInfValue()) <= (page2.getOutputSupValue() - page2.getOutputInfValue())) 
	    		   ))
	       {
	    	   return -1;
	       }else {
	    	   return 0;
	       }
			//		|| (tempXValue == listHostUseLevel.get(i).getOutputXValue()
			//			& tempYValue <= listHostUseLevel.get(i).getOutputYValue())) {

			//	tempXValue = listHostUseLevel.get(i).getOutputXValue();
			//	tempYValue = listHostUseLevel.get(i).getOutputYValue();
			//	outputHost = listHostUseLevel.get(i).getPowerHost();
		   
		   //ascending order
		   //return StudentName1.compareTo(StudentName2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }
				
	};
	

	public String printaValores() {
		String outputString = null;
		
		outputString = String.format("ROA: %f | RF: %f | WF: %f  | Promotion Inf: %f | Promotion Sup: %f \n",
		       this.getRecencyOfAccessValue(), this.getReadFrequencyValue(), this.getWriteFrequencyValue(), 
		       this.getOutputInfValue(), this.getOutputSupValue());
			   
		
		return outputString;
	}
	
	public double getRecencyOfAccessValue() {
		return recencyOfAccessValue;
	}

	public void setRecencyOfAccessValue(double recencyOfAccessValue) {
		this.recencyOfAccessValue = recencyOfAccessValue;
	}

	public double getReadFrequencyValue() {
		return readFrequencyValue;
	}

	public void setReadFrequencyValue(double readFrequencyValue) {
		this.readFrequencyValue = readFrequencyValue;
	}

	public double getWriteFrequencyValue() {
		return writeFrequencyValue;
	}

	public void setWriteFrequencyValue(double writeFrequencyValue) {
		this.writeFrequencyValue = writeFrequencyValue;
	}
	
	public double getOutputInfValue() {
		return outputValue[0];
	}

	public void setOutputInfValue(double outputInfValue) {
		this.outputValue[0] = outputInfValue;
	}
	
	public double getOutputSupValue() {
		return outputValue[1];
	}

	public void setOutputSupValue(double outputValue) {
		this.outputValue[1] = outputValue;
	}
		
}
