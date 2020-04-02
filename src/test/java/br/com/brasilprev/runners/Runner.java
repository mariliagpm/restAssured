package br.com.brasilprev.runners;


import org.junit.AfterClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features", 
plugin = {"pretty"},
glue = "br.com.brasilprev.stepDefs", monochrome = true, dryRun = false)
public class Runner {

	@AfterClass
	 public static void writeExtentReport() {
	 }
}
