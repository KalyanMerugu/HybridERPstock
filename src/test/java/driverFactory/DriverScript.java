package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath = "./FileInput/Controller.xlsx";
String outputpath = "./FileOuput/HybridResults.xlsx";
String TCSheet = "MasterTestCases";
public void startTest()throws Throwable
{
	String Module_status ="";
	String Module_New ="";
	//create referance object for accessing excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in tcsheet
	for(int i=1;i<=xl.rowcount(TCSheet);i++)
	{
		if(xl.getcelldata(TCSheet, i, 2).equalsIgnoreCase("Y"))
		{
			//read the testcase for tcsheet
			String TCModule =xl.getcelldata(TCSheet, i, 1);
			//iterate all rows in TCModule sheet
			for(int j=1;j<=xl.rowcount(TCModule);j++)
			{
				//read all cell from TCModule sheet
				String Description =xl.getcelldata(TCModule, j, 0);
				String ObjectType = xl.getcelldata(TCModule, j, 1);
				String Ltype = xl.getcelldata(TCModule, j, 2);
				String Lvalue = xl.getcelldata(TCModule, j, 3);
				String TestData =xl.getcelldata(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("startBrowser"))
					{
						driver =FunctionLibrary.startBrowser();
					}
					if(ObjectType.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
					}
					if(ObjectType.equalsIgnoreCase("waitForElement"))
					{
						Thread.sleep(2000);
						FunctionLibrary.waitForElement(Ltype, Lvalue, TestData);
					}
					if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Ltype, Lvalue, TestData);
					}
					if(ObjectType.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Ltype, Lvalue);
					}
					if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(TestData);
					}
					if(ObjectType.equals("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
					}
					if(ObjectType.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(Ltype, Lvalue, TestData);
					}
					if(ObjectType.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(Ltype,Lvalue);
					}
					if(ObjectType.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
					}
					if(ObjectType.equalsIgnoreCase("capturesup"))
					{
						FunctionLibrary.capturesup(Ltype,Lvalue);
					}
					if(ObjectType.equalsIgnoreCase("suppliertable"))
					{
						FunctionLibrary.suppliertable();
					}
					if(ObjectType.equalsIgnoreCase("capturecus"))
					{
						FunctionLibrary.capturecus(Ltype,Lvalue);
						}
					if(ObjectType.equalsIgnoreCase("customertable"))
					{
						FunctionLibrary.customertable();

					}
					//write as pass into status cell in TCModule sheet
					xl.setcelldata(TCModule, j, 5, "pass", outputpath);
					Thread.sleep(2000);
					Module_status = "True";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//write as fail into status cell in TCModule sheet
					xl.setcelldata(TCModule, j, 5, "Fail", outputpath);
					Module_New = "False";
				}
				if(Module_status.equalsIgnoreCase("True"))
				{
					//write pass into TCCSheet in status cell
					Thread.sleep(2000);
					xl.setcelldata(TCSheet, i, 3, "Pass", outputpath);
				}	
			}
			if(Module_New.equalsIgnoreCase("False"))
			{
				//write pass into TCSheet in status cell
				xl.setcelldata(TCSheet, i, 3, "fail", outputpath);
			}
		}
		else
		{
			//write as Blocked for testcases which are flag tp N
			xl.setcelldata(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
}
}
