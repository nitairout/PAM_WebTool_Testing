@echo off 
cd "C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool>
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S1_10ValidValuesDetectSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S2_DataErrorWithCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S3_DataErrorWithNoCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S4_RepeatedValueWithCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S5_RepeatedValueWithNoCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S6_DipWithCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S7_DipWithNoCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S8_GapWithCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S9_GapwithNoCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S10_NullWithCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S11_NullwithNoCatchUpSpike'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S12_RolloverOver2days'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S13_RolloverLess2days'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S14_SmallBatch'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S15_Max4Month15min'
TIMEOUT /T 2
DCTool -path='C:\Users\nrout_admin\Automation\Applications\GitHub\Dev\PAM\qa-pam-test-automation\src\DCTool\DeltaCalculatorTool\DCScenarios\S16_GapZeroSpike'
exit
echo