package cucumber.Options;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",plugin ="json:target/jsonReports/cucumber-report.json", glue = { "stepDefinitions" },tags = {"@DeletePlace"})
public class TestRunner {
//,tags = {"@DeletePlace"}
	
	//Below notes for extent reports generation
/*
 * 'plugin' provides the input JSON file.'json:' tells that we are expecting
 * something in json format.'target/jsonReports/cucumber-report.json' generally
 * all report are supposed to be in others folder so v r given the path. In pom
 * '{project.build.directory}' indicates project build directory will give path
 * until target folder by default
 */
}
