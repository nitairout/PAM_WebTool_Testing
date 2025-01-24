package DCTool;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import util.ErrorUtil;

/*
 * This test verifies different scenarios to check delta calculator tool is working as expected
 * It runs DC tool for each scenario, then gets the output files and compare with expected files.
 * if they are not same it fails that scenario then move to next scenario
 */

public class DCToolScenarios {
	String DCTOOL_BATCH=System.getProperty("user.dir")+ "\\DCTools\\DCToolTestFiles\\DCToolBatch\\DCToolCommands.bat";
	String DCTOOL=System.getProperty("user.dir")+ "\\DCTools\\DCToolTestFiles\\DCScenarios\\";
	String dcToolBasicPath=System.getProperty("user.dir")+ "\\DCTools\\DeltaCalculatorTool\\DCScenarios\\";
	//String dcToolBasicPath="C:\\DeltaCalculatorTool\\DCScenarios_Output\\";
	//expected files path
	String S1_Expected=dcToolBasicPath+"S1_10ValidValuesDetectSpike\\output\\";
	String S2_Expected=dcToolBasicPath+"S2_DataErrorWithCatchUpSpike\\output\\";
	String S3_Expected=dcToolBasicPath+"S3_DataErrorWithNoCatchUpSpike\\output\\";
	String S4_Expected=dcToolBasicPath+"S4_RepeatedValueWithCatchUpSpike\\output\\";
	String S5_Expected=dcToolBasicPath+"S5_RepeatedValueWithNoCatchUpSpike\\output\\";
	String S6_Expected=dcToolBasicPath+"S6_DipWithCatchUpSpike\\output\\";
	String S7_Expected=dcToolBasicPath+"S7_DipWithNoCatchUpSpike\\output\\";
	String S8_Expected=dcToolBasicPath+"S8_GapWithCatchUpSpike\\output\\";
	String S9_Expected=dcToolBasicPath+"S9_GapwithNoCatchUpSpike\\output\\";
	String S10_Expected=dcToolBasicPath+"S10_NullWithCatchUpSpike\\output\\";
	String S11_Expected=dcToolBasicPath+"S11_NullwithNoCatchUpSpike\\output\\";
	String S12_Expected=dcToolBasicPath+"S12_RolloverOver2days\\output\\";
	String S13_Expected=dcToolBasicPath+"S13_RolloverLess2days\\output\\";
	String S14_Expected=dcToolBasicPath+"S14_SmallBatch\\output\\";
	String S15_Expected=dcToolBasicPath+"S15_Max4Month15min\\output\\";
	String S16_Expected=dcToolBasicPath+"S16_GapZeroSpike\\output\\";
	
	//Actual file path
	String S1_Actual=dcToolBasicPath+"S1_10ValidValuesDetectSpike-Output\\";
	String S2_Actual=dcToolBasicPath+"S2_DataErrorWithCatchUpSpike-Output\\";
	String S3_Actual=dcToolBasicPath+"S3_DataErrorWithNoCatchUpSpike-Output\\";
	String S4_Actual=dcToolBasicPath+"S4_RepeatedValueWithCatchUpSpike-Output\\";
	String S5_Actual=dcToolBasicPath+"S5_RepeatedValueWithNoCatchUpSpike-Output\\";
	String S6_Actual=dcToolBasicPath+"S6_DipWithCatchUpSpike-Output\\";
	String S7_Actual=dcToolBasicPath+"S7_DipWithNoCatchUpSpike-Output\\";
	String S8_Actual=dcToolBasicPath+"S8_GapWithCatchUpSpike-Output\\";
	String S9_Actual=dcToolBasicPath+"S9_GapwithNoCatchUpSpike-Output\\";
	String S10_Actual=dcToolBasicPath+"S10_NullWithCatchUpSpike-Output\\";
	String S11_Actual=dcToolBasicPath+"S11_NullwithNoCatchUpSpike-Output\\";
	String S12_Actual=dcToolBasicPath+"S12_RolloverOver2days-Output\\";
	String S13_Actual=dcToolBasicPath+"S13_RolloverLess2days-Output\\";
	String S14_Actual=dcToolBasicPath+"S14_SmallBatch-Output\\";
	String S15_Actual=dcToolBasicPath+"S15_Max4Month15min-Output\\";
	String S16_Actual=dcToolBasicPath+"S16_GapZeroSpike-Output\\";
	
	String actualOutput="output\\";
		@Test (priority=1)
		public void executeAllScenario(){
			try {
				
				// String[] command = {"cmd.exe", "/C", "Start", "C:\\DCToolBatch\\ExecuteDCTool.bat"};
				 String[] command = {"cmd.exe", "/C", "Start", DCTOOL_BATCH};
				 //Deleting Actual(output) files generated from previous run
				 FileUtils.cleanDirectory(new File(S1_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S2_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S3_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S4_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S5_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S6_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S7_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S8_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S9_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S10_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S11_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S12_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S13_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S14_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S15_Actual+actualOutput));
				 FileUtils.cleanDirectory(new File(S16_Actual+actualOutput));
				 
				Reporter.log("Removed all the actual files");		
				 
				//Execute the command	
		        Process p =  Runtime.getRuntime().exec(command); 
		        Thread.sleep(30000);
		        Thread.sleep(30000);
		        
		        //Checking if the command is executed successfully
		        if(p==null){
		        	throw new Exception("Tool is not generated file");
		        }
		        Thread.sleep(5000);
		        Reporter.log("Executed the commands");
			} catch (Throwable t) {
				t.printStackTrace();
				ErrorUtil.addVerificationFailure(t);
				Reporter.log("DCToolExecutionFailed");		
				Assert.fail("Error in DCToolExecution", t);
			} 
		}
		
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering null, 
		 * if the trailing value is a catch-up spike, Flag the cumulative stream with: Valid + Metered. 
		 * There is no Note and flag the interval stream with: Suspect+Metered+Note: "System detected a catch-up spike."
		 * */
		@Test (priority=2)
		public void S1_10ValidValuesDetectSpike(){
		      //S1_10ValidValuesDetectSpike verification starts
		        try{
				        //check if the output files generated
				        if(new File((S1_Actual+actualOutput)).list().length!=4)
				        	throw new Exception("Command S1_10ValidValuesDetectSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully");
				        //File comparision starts for S1_10ValidValuesDetectSpike
				        if(!FileUtils.contentEquals(new File((S1_Actual+actualOutput)+"batch0-cumulative.csv"), new File(S1_Expected+"batch0-cumulative.csv")))
					    	   throw new Exception("Actual batch0-cumulative.csv for S1_10ValidValuesDetectSpike is not same as Expected batch0-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S1_Actual+actualOutput)+"batch0-interval.csv"), new File(S1_Expected+"batch0-interval.csv")))
					    	   throw new Exception("Actual batch0-interval.csv for S1_10ValidValuesDetectSpike is not same as Expected batch0-interval.csv ");
				        if(!FileUtils.contentEquals(new File((S1_Actual+actualOutput)+"batch1-cumulative.csv"), new File(S1_Expected+"batch1-cumulative.csv")))
					    	   throw new Exception("Actual batch1-cumulative.csv for S1_10ValidValuesDetectSpike is not same Expected batch1-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S1_Actual+actualOutput)+"batch1-interval.csv"), new File(S1_Expected+"batch1-interval.csv")))
					    	   throw new Exception("Actual batch1-interval.csv for S1_10ValidValuesDetectSpike is not same as Expected batch1-interval.csv");
				        Reporter.log("Verification completed for S1_10ValidValuesDetectSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S1_10ValidValuesDetectSpikeFailed");		
					Assert.fail("Error in S1_10ValidValuesDetectSpike", t);
				} 
			}
		
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering data errors, 
		 * if the trailing value is a catch-up spike, Flag the cumulative stream with: Valid + Metered. 
		 * There is no Note and flag the interval stream with: Suspect+Metered+Note: "System detected a catch-up spike."
		 * */
		@Test (priority=3)
		public void S2_DataErrorWithCatchUpSpike(){     
		      //S2_DataErrorWithCatchUpSpike verification starts
		        try{
				        
				        //check if the output files generated
				        if(new File((S2_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S2_DataErrorWithCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S2_DataErrorWithCatchUpSpike");
				        //File comparision starts for S2_DataErrorWithCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S2_Actual+actualOutput)+"DataErrorWithCatchUpSpike-cumulative.csv"), new File(S2_Expected+"DataErrorWithCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual DataErrorWithCatchUpSpike-cumulative.csv for S2_DataErrorWithCatchUpSpike not same as DataErrorWithCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S2_Actual+actualOutput)+"DataErrorWithCatchUpSpike-interval.csv"), new File(S2_Expected+"DataErrorWithCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual DataErrorWithCatchUpSpike-interval.csv for S2_DataErrorWithCatchUpSpike not same as DataErrorWithCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S2_DataErrorWithCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S2_DataErrorWithCatchUpSpike Failed");		
					Assert.fail("Error in S2_DataErrorWithCatchUpSpike", t);
				} 
			}
		
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering data errors, 
		 * if the trailing value does not indicate a catch-up spike, both the cumulative and interval streams are flagged with 
		 * Suspect+Metered+Note: "System did not detect a catch-up spike."
		 * */
		@Test (priority=4)
		public void S3_DataErrorWithNoCatchUpSpike(){     
		      //S3_DataErrorWithNoCatchUpSpike verification starts
		        try{
			        //check if the output files generated
			        if(new File((S3_Actual+actualOutput)).list().length!=2)
			        	throw new Exception("Command S3_DataErrorWithNoCatchUpSpike not generated actual files");
			        Reporter.log("Actual output files are generated successfully for S3_DataErrorWithNoCatchUpSpike");
			        //File comparision starts for S3_DataErrorWithNoCatchUpSpike
			        if(!FileUtils.contentEquals(new File((S3_Actual+actualOutput)+"DataErrorWithNoCatchUpSpike-cumulative.csv"), new File(S3_Expected+"DataErrorWithNoCatchUpSpike-cumulative.csv")))
				    	   throw new Exception("Actual DataErrorWithNoCatchUpSpike-cumulative.csv for S2_DataErrorWithCatchUpSpike not same as DataErrorWithNoCatchUpSpike-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S3_Actual+actualOutput)+"DataErrorWithNoCatchUpSpike-interval.csv"), new File(S3_Expected+"DataErrorWithNoCatchUpSpike-interval.csv")))
				    	   throw new Exception("Actual DataErrorWithCatchUpSpike_Interval.csv for S3_DataErrorWithNoCatchUpSpike not same as DataErrorWithNoCatchUpSpike-interval.csv");
			        Reporter.log("Verification completed for S3_DataErrorWithNoCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S3_DataErrorWithNoCatchUpSpike Failed");		
					Assert.fail("Error in S3_DataErrorWithNoCatchUpSpike", t);
				} 
			}
	
	/*
	 * The Delta calculator checks for repeating values in the cumulative stream and only identifies them if 
	 * they are followed by a catch-up spike. Repeating values in the cumulative stream are flagged as Suspect+Metered+Note: 
	 * "System detected a repeated value representing a gap." Similarly, zero values in the interval stream are flagged as 
	 * Suspect+Metered+Note: "System detected a zero representing a gap."
	 * */
		@Test (priority=5)
		public void S4_RepeatedValueWithCatchUpSpike(){       
		      //S4_RepeatedValueWithCatchUpSpike verification starts
		        try{
				        //check if the output files generated
				        if(new File((S4_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S4_RepeatedValueWithCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S4_RepeatedValueWithCatchUpSpike");
				        //File comparision starts for S4_RepeatedValueWithCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S4_Actual+actualOutput)+"RepeatedValueWithCatchUpSpike-cumulative.csv"), new File(S4_Expected+"RepeatedValueWithCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual RepeatedValueWithCatchUpSpike-cumulative.csv for S4_RepeatedValueWithCatchUpSpike not same as RepeatedValueWithCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S4_Actual+actualOutput)+"RepeatedValueWithCatchUpSpike-interval.csv"), new File(S4_Expected+"RepeatedValueWithCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual RepeatedValueWithCatchUpSpike-interval.csv for S4_RepeatedValueWithCatchUpSpikel not same as RepeatedValueWithCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S4_RepeatedValueWithCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S4_RepeatedValueWithCatchUpSpike Failed");		
					Assert.fail("Error in S4_RepeatedValueWithCatchUpSpike", t);
				} 
			}
		/*
		 * The Delta Calculator tests for repeating values, and if there is no catch-up spike, 
		 * all values are considered Valid+Metered, assuming they are good zeroes.
		 * */
		@Test (priority=6)
		public void S5_RepeatedValueWithNoCatchUpSpike(){       
		      //S5_RepeatedValueWithNoCatchUpSpike verification starts
		        try{
				      
				        //check if the output files generated
				        if(new File((S5_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S5_RepeatedValueWithNoCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S5_RepeatedValueWithNoCatchUpSpike");
				        //File comparision starts for S5_RepeatedValueWithNoCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S5_Actual+actualOutput)+"RepeatedValueWithNoCatchUpSpike-cumulative.csv"), new File(S5_Expected+"RepeatedValueWithNoCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual RepeatedValueWithNoCatchUpSpike-cumulative.csv for S5_RepeatedValueWithNoCatchUpSpike not same as RepeatedValueWithNoCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S5_Actual+actualOutput)+"RepeatedValueWithNoCatchUpSpike-interval.csv"), new File(S5_Expected+"RepeatedValueWithNoCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual RepeatedValueWithNoCatchUpSpike-interval.csv for S5_RepeatedValueWithNoCatchUpSpike not same as RepeatedValueWithNoCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S5_RepeatedValueWithNoCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S5_RepeatedValueWithNoCatchUpSpike Failed");		
					Assert.fail("Error in S5_RepeatedValueWithNoCatchUpSpike", t);
				} 
			}
		
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering dip, 
		 * if the trailing value is a catch-up spike, Flag the cumulative stream with: Valid + Metered. 
		 * There is no Note and flag the interval stream with: Suspect+Metered+Note: "System detected a catch-up spike."
		 * */
		@Test (priority=7)
		public void S6_DipWithCatchUpSpike(){      
		      //S6_DipWithCatchUpSpike verification starts
		        try{
				        //check if the output files generated
				        if(new File((S6_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S6_DipWithCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S6_DipWithCatchUpSpike");
				        //File comparision starts for S6_DipWithCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S6_Actual+actualOutput)+"DipWithCatchUpSpike-cumulative.csv"), new File(S6_Expected+"DipWithCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual DataErrorWithNoCatchUpSpike-cumulative.csv for S6_DipWithCatchUpSpike not same as DipWithCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S6_Actual+actualOutput)+"DipWithCatchUpSpike-interval.csv"), new File(S6_Expected+"DipWithCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual DipWithCatchUpSpike-interval.csv for S6_DipWithCatchUpSpike not same as DipWithCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S6_DipWithCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S6_DipWithCatchUpSpike Failed");		
					Assert.fail("Error in S6_DipWithCatchUpSpike", t);
				} 
			}
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering dip, 
		 * if the trailing value does not indicate a catch-up spike, both the cumulative and interval streams are 
		 * flagged with Suspect+Metered+Note: "System did not detect a catch-up spike."
		 * */
	    @Test (priority=8)
		public void S7_DipWithNoCatchUpSpike(){     
		      //S7_DipWithNoCatchUpSpike verification starts
		        try{
			        
			        //check if the output files generated
			        if(new File((S7_Actual+actualOutput)).list().length!=2)
			        	throw new Exception("Command S7_DipWithNoCatchUpSpike not generated actual files");
			        Reporter.log("Actual output files are generated successfully for S7_DipWithNoCatchUpSpike");
			        //File comparision starts for S7_DipWithNoCatchUpSpike
			        if(!FileUtils.contentEquals(new File((S7_Actual+actualOutput)+"DipWithNoCatchUpSpike-cumulative.csv"), new File(S7_Expected+"DipWithNoCatchUpSpike-cumulative.csv")))
				    	   throw new Exception("Actual DipWithNoCatchUpSpike-cumulative.csv for S7_DipWithNoCatchUpSpike not sam as DipWithNoCatchUpSpike-cumulative.csve");
			        if(!FileUtils.contentEquals(new File((S7_Actual+actualOutput)+"DipWithNoCatchUpSpike-interval.csv"), new File(S7_Expected+"DipWithNoCatchUpSpike-interval.csv")))
				    	   throw new Exception("Actual DipWithNoCatchUpSpike-interval.csv for S7_DipWithNoCatchUpSpike not same as DipWithNoCatchUpSpike-interval.csv");
			        Reporter.log("Verification completed for S7_DipWithNoCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S7_DipWithNoCatchUpSpike Failed");		
					Assert.fail("Error in S7_DipWithNoCatchUpSpike", t);
				} 
			}
	    
	    /*
	     * The Delta calculator conducts a catch-up spike test on the trailing value after encountering gaps, 
	     * if the trailing value is a catch-up spike, Flag the cumulative stream with: Valid + Metered. 
	     * There is no Note and flag the interval stream with: Suspect+Metered+Note: "System detected a catch-up spike."
	     * */
		@Test (priority=9)
		public void S8_GapWithCatchUpSpike(){      
		      //S8_GapWithCatchUpSpike verification starts
		        try{
			        
			        //check if the output files generated
			        if(new File((S8_Actual+actualOutput)).list().length!=2)
			        	throw new Exception("Command S8_GapWithCatchUpSpike not generated actual files");
			        Reporter.log("Actual output files are generated successfully for S8_GapWithCatchUpSpike");
			        //File comparision starts for S8_GapWithCatchUpSpike
			        if(!FileUtils.contentEquals(new File((S8_Actual+actualOutput)+"GapWithCatchUpSpike-cumulative.csv"), new File(S8_Expected+"GapWithCatchUpSpike-cumulative.csv")))
				    	   throw new Exception("Actual GapWithCatchUpSpike-cumulative.csv for S8_GapWithCatchUpSpike not same as GapWithCatchUpSpike-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S8_Actual+actualOutput)+"GapWithCatchUpSpike-interval.csv"), new File(S8_Expected+"GapWithCatchUpSpike-interval.csv")))
				    	   throw new Exception("Actual GapWithCatchUpSpike-interval.csv for S8_GapWithCatchUpSpike not same as GapWithCatchUpSpike-interval.csv");
			        Reporter.log("Verification completed for S8_GapWithCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S8_GapWithCatchUpSpike Failed");		
					Assert.fail("Error in S8_GapWithCatchUpSpike", t);
				} 
			}
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering gaps, 
		 * if the trailing value does not indicate a catch-up spike, both the cumulative and interval streams are flagged with 
		 * Suspect+Metered+Note: "System did not detect a catch-up spike."
		 * */
		@Test (priority=10)
		public void S9_GapwithNoCatchUpSpike(){       
		      //S9_GapwithNoCatchUpSpike verification starts
		        try{
				        
				        //check if the output files generated
				        if(new File((S9_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S9_GapwithNoCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S9_GapwithNoCatchUpSpike");
				        //File comparision starts for S9_GapwithNoCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S9_Actual+actualOutput)+"GapWithNoCatchUpSpike-cumulative.csv"), new File(S9_Expected+"GapWithNoCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual GapWithNoCatchUpSpike-cumulative.csv for S9_GapwithNoCatchUpSpike not same as GapWithNoCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S9_Actual+actualOutput)+"GapWithNoCatchUpSpike-interval.csv"), new File(S9_Expected+"GapWithNoCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual GapWithNoCatchUpSpike-interval.csv for S9_GapwithNoCatchUpSpike not same as GapWithNoCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S9_GapwithNoCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S9_GapwithNoCatchUpSpike Failed");		
					Assert.fail("Error in S9_GapwithNoCatchUpSpike", t);
				} 
			}
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering null, 
		 * if the trailing value is a catch-up spike, Flag the cumulative stream with: Valid + Metered. 
		 * There is no Note and flag the interval stream with: Suspect+Metered+Note: "System detected a catch-up spike."
		 * */
		@Test (priority=11)
		public void S10_NullWithCatchUpSpike(){         
		        //S10_NullWithCatchUpSpike verification starts
		        try{
				       
				        //check if the output files generated
				        if(new File((S10_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S10_NullWithCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S10_NullWithCatchUpSpike");
				        //File comparision starts for S10_NullWithCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S10_Actual+actualOutput)+"NullWithCatchUpSpike-cumulative.csv"), new File(S10_Expected+"NullWithCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual NullWithCatchUpSpike-cumulative.csv for S10_NullWithCatchUpSpike not same as NullWithCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S10_Actual+actualOutput)+"NullWithCatchUpSpike-interval.csv"), new File(S10_Expected+"NullWithCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual NullWithCatchUpSpike-interval.csv for S10_NullWithCatchUpSpike not same as NullWithCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S10_NullWithCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S10_NullWithCatchUpSpike Failed");		
					Assert.fail("Error in S10_NullWithCatchUpSpike", t);
				} 
			}
		/*
		 * The Delta calculator conducts a catch-up spike test on the trailing value after encountering null, 
		 * if the trailing value does not indicate a catch-up spike, both the cumulative and interval streams are 
		 * flagged with Suspect+Metered+Note: "System did not detect a catch-up spike."
		 * */
		@Test (priority=12)
		public void S11_NullwithNoCatchUpSpike(){           
		      //S11_NullwithNoCatchUpSpike verification starts
		        try{
				        
				        //check if the output files generated
				        if(new File((S11_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S11_NullwithNoCatchUpSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S11_NullwithNoCatchUpSpike");
				        //File comparision starts for S11_NullwithNoCatchUpSpike
				        if(!FileUtils.contentEquals(new File((S11_Actual+actualOutput)+"NullwithNoCatchUpSpike-cumulative.csv"), new File(S11_Expected+"NullwithNoCatchUpSpike-cumulative.csv")))
					    	   throw new Exception("Actual NullwithNoCatchUpSpike-cumulative.csv for S11_NullwithNoCatchUpSpike not same as NullwithNoCatchUpSpike-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S11_Actual+actualOutput)+"NullwithNoCatchUpSpike-interval.csv"), new File(S11_Expected+"NullwithNoCatchUpSpike-interval.csv")))
					    	   throw new Exception("Actual NullwithNoCatchUpSpike-interval.csv for S11_NullwithNoCatchUpSpike not same as NullwithNoCatchUpSpike-interval.csv");
				        Reporter.log("Verification completed for S11_NullwithNoCatchUpSpike");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S11_NullwithNoCatchUpSpike Failed");		
					Assert.fail("Error in S11_NullwithNoCatchUpSpike", t);
				} 
			}
		/*
		 * The rollover/reset correction logic is triggered only if the dip lasts longer than 2 days. 
		 * The Cumulative stream is flagged as Suspect with a Note. Additionally, the interval stream is flagged as 
		 * Unknown+Estimate+Note, and the Delta-calculator replaces negative values with an estimate using linear interpolation.
		 * */
		@Test (priority=13)
		public void S12_RolloverOver2days(){    
		      //S12_RolloverOver2days verification starts
		        try{
			        
			        //check if the output files generated
			        if(new File((S12_Actual+actualOutput)).list().length!=2)
			        	throw new Exception("Command S12_RolloverOver2days not generated actual files");
			        Reporter.log("Actual output files are generated successfully for S12_RolloverOver2days");
			        //File comparision starts for S12_Rollover
			        if(!FileUtils.contentEquals(new File((S12_Actual+actualOutput)+"Rollover-cumulative.csv"), new File(S12_Expected+"Rollover-cumulative.csv")))
				    	   throw new Exception("Actual Rollover-cumulative.csv for S12_RolloverOver2days not same as Rollover-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S12_Actual+actualOutput)+"Rollover-interval.csv"), new File(S12_Expected+"Rollover-interval.csv")))
				    	   throw new Exception("Actual Rollover-interval.csv for S12_RolloverOver2days not same as Rollover-interval.csv");
			        Reporter.log("Verification completed for S12_Rollover_over2days");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S12_RolloverOver2days Failed");		
					Assert.fail("Error in S12_RolloverOver2days", t);
				} 
			}
		/*
		 * If the dip lasts less than 2 days, the rollover/reset correction logic considers it as a normal dip.
		 * */
		@Test (priority=14)
		public void S13_RolloverLess2days(){    
			      //S13_RolloverLess2days verification starts
			        try{
				        
				        //check if the output files generated
				        if(new File((S13_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S13_RolloverLess2days not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S13_RolloverLess2days");
				        //File comparision starts for S13_RolloverLess2days
				        if(!FileUtils.contentEquals(new File((S13_Actual+actualOutput)+"batch0-cumulative.csv"), new File(S13_Expected+"batch0-cumulative.csv")))
					    	   throw new Exception("Actual batch0-cumulative.csv for S13_RolloverLess2days not same as batch0-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S13_Actual+actualOutput)+"batch0-interval.csv"), new File(S13_Expected+"batch0-interval.csv")))
					    	   throw new Exception("Actual batch0-interval.csv for S13_RolloverLess2days not same as batch0-interval.csv");
				        Reporter.log("Verification completed for S13_RolloverLess2days");
			        } catch (Throwable t) {
						ErrorUtil.addVerificationFailure(t);
						Reporter.log("S13_RolloverLess2days Failed");		
						Assert.fail("Error in S13_RolloverLess2days", t);
					} 
				}
		
		/*
		 * Verify that when loading cumulative data in small batches to PAM, 
		 * there are no issues with Spike Detection and the identification of spikes in the data.
		 * */
		@Test (priority=15)
		public void S14_SmallBatch(){    
		      //S14_SmallBatch verification starts
		        try{
		        
			        if(new File((S14_Actual+actualOutput)).list().length!=12)
			        	throw new Exception("Command S14_SmallBatch not generated actual files");
			        Reporter.log("Actual output files are generated successfully for S14_SmallBatch");
			        //File comparision starts for S13_RolloverLess2days
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch0-cumulative.csv"), new File(S14_Expected+"batch0-cumulative.csv")))
				    	   throw new Exception("Actual batch0-cumulative.csv for S14_SmallBatch not same as batch0-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch0-interval.csv"), new File(S14_Expected+"batch0-interval.csv")))
				    	   throw new Exception("Actual batch0-interval.csv for S14_SmallBatch not same as batch0-interval.csv");
			        
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch1-cumulative.csv"), new File(S14_Expected+"batch1-cumulative.csv")))
				    	   throw new Exception("Actual batch1-cumulative.csv for S14_SmallBatch not same as batch1-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch1-interval.csv"), new File(S14_Expected+"batch1-interval.csv")))
				    	   throw new Exception("Actual batch1-interval.csv for S14_SmallBatch not same as batch1-interval.csv");
			        
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch2-cumulative.csv"), new File(S14_Expected+"batch2-cumulative.csv")))
				    	   throw new Exception("Actual batch2-cumulative.csv for S14_SmallBatch not same as batch2-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch2-interval.csv"), new File(S14_Expected+"batch2-interval.csv")))
				    	   throw new Exception("Actual batch2-interval.csv for S14_SmallBatch not same as batch2-interval.csv");
			        
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch3-cumulative.csv"), new File(S14_Expected+"batch3-cumulative.csv")))
				    	   throw new Exception("Actual batch3-cumulative.csv for S14_SmallBatch not same as batch3-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch3-interval.csv"), new File(S14_Expected+"batch3-interval.csv")))
				    	   throw new Exception("Actual batch3-interval.csv for S14_SmallBatch not same as batch3-interval.csv");
			        
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch4-cumulative.csv"), new File(S14_Expected+"batch4-cumulative.csv")))
				    	   throw new Exception("Actual batch4-cumulative.csv for S14_SmallBatch not same as batch4-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch4-interval.csv"), new File(S14_Expected+"batch4-interval.csv")))
				    	   throw new Exception("Actual batch4-interval.csv for S14_SmallBatch not same as batch4-interval.csv");
			        
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch5-cumulative.csv"), new File(S14_Expected+"batch5-cumulative.csv")))
				    	   throw new Exception("Actual batch5-cumulative.csv for S13_RolloverLess2days not same as batch5-cumulative.csv");
			        if(!FileUtils.contentEquals(new File((S14_Actual+actualOutput)+"batch5-interval.csv"), new File(S14_Expected+"batch5-interval.csv")))
				    	   throw new Exception("Actual batch5-interval.csv for S14_SmallBatch not same as batch5-interval.csv");
			        Reporter.log("Verification completed for S13_RolloverLess2days");
		        } catch (Throwable t) {
					ErrorUtil.addVerificationFailure(t);
					Reporter.log("S14_SmallBatch Failed");		
					Assert.fail("Error in S14_SmallBatch", t);
				} 
			}
		/*
		 * Verify that during the analysis of estimated interval data, weighted estimates are not displayed for error/gaps 
		 * longer than four months, allowing prompt identification of significant data import issues.
		 * */
		@Test (priority=16)
		public void S15_Max4Month15min(){    
			      //S15_Max4Month15min verification starts
			        try{
				        
				        if(new File((S15_Actual+actualOutput)).list().length!=8)
				        	throw new Exception("Command S15_Max4Month15min not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S15_Max4Month15min");
				        //File comparision starts for S13_Rollover_less2days
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch0-cumulative.csv"), new File(S15_Expected+"batch0-cumulative.csv")))
					    	   throw new Exception("Actual batch0-cumulative.csv for S15_Max4Month15min not same as batch0-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch0-interval.csv"), new File(S15_Expected+"batch0-interval.csv")))
					    	   throw new Exception("Actual batch0-interval.csv for S15_Max4Month15min not same as batch0-interval.csv");
				        
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch1-cumulative.csv"), new File(S15_Expected+"batch1-cumulative.csv")))
					    	   throw new Exception("Actual batch1-cumulative.csv for S15_Max4Month15min not same as batch1-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch1-interval.csv"), new File(S15_Expected+"batch1-interval.csv")))
					    	   throw new Exception("Actual batch1-interval.csv for S15_Max4Month15min not same as batch1-interval.csv");
				        
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch2-cumulative.csv"), new File(S15_Expected+"batch2-cumulative.csv")))
					    	   throw new Exception("Actual batch2-cumulative.csv for S15_Max4Month15min not same as batch2-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch2-interval.csv"), new File(S15_Expected+"batch2-interval.csv")))
					    	   throw new Exception("Actual batch2-interval.csv for S15_Max4Month15min not same as batch2-interval.csv");
				        
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch3-cumulative.csv"), new File(S15_Expected+"batch3-cumulative.csv")))
					    	   throw new Exception("Actual batch3-cumulative.csv for S15_Max4Month15min not same as batch3-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S15_Actual+actualOutput)+"batch3-interval.csv"), new File(S15_Expected+"batch3-interval.csv")))
					    	   throw new Exception("Actual batch3-interval.csv for S15_Max4Month15min not same as batch3-interval.csv");
				        
				        Reporter.log("Verification completed for S15_Max4Month15min");
			        } catch (Throwable t) {
						ErrorUtil.addVerificationFailure(t);
						Reporter.log("S15_Max4Month15min Failed");		
						Assert.fail("Error in S15_Max4Month15min", t);
					} 
				}
		/*
		 * Verify that Delta calculator treats adjacent repeating values as part of the data gap or data error. 
		 * The Delta calculator checks for repeating values in the cumulative stream and only identifies them if 
		 * they are followed by a catch-up spike. Repeating values in the cumulative stream are flagged as Suspect+Metered+Note: 
		 * "System detected a repeated value representing a gap." Similarly, zero values in the interval stream are flagged as 
		 * Suspect+Metered+Note: "System detected a zero representing a gap."
		 * */
		@Test (priority=17)
		public void S16_GapZeroSpike(){    
			      //S16_GapZeroSpike verification starts
			        try{
				        
				        //check if the output files generated
				        if(new File((S16_Actual+actualOutput)).list().length!=2)
				        	throw new Exception("Command S16_GapZeroSpike not generated actual files");
				        Reporter.log("Actual output files are generated successfully for S16_GapZeroSpike");
				        //File comparision starts for S12_Rollover
				        if(!FileUtils.contentEquals(new File((S16_Actual+actualOutput)+"batch0-cumulative.csv"), new File(S16_Expected+"batch0-cumulative.csv")))
					    	   throw new Exception("Actual batch0-cumulative.csv for S16_GapZeroSpike not same as batch0-cumulative.csv");
				        if(!FileUtils.contentEquals(new File((S16_Actual+actualOutput)+"batch0-interval.csv"), new File(S16_Expected+"batch0-interval.csv")))
					    	   throw new Exception("Actual batch0-interval.csv for S16_GapZeroSpike not same as batch0-interval.csv");
				        Reporter.log("Verification completed for S16_GapZeroSpike");
			        } catch (Throwable t) {
						ErrorUtil.addVerificationFailure(t);
						Reporter.log("S16_GapZeroSpike Failed");		
						Assert.fail("Error in S16_GapZeroSpike", t);
					} 
				}
}
