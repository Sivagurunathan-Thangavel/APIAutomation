package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")

	public void beforeScenario() throws IOException {
		//As 'placeID' is static so it is necessary to call by classname.variablename and not by referencevariablename.variablename
		StepDefinition sd = new StepDefinition();
		if (StepDefinition.placeID == null) {
			sd.add_Place_Payload_with("Vikesh", "Chitoor", "Telugu");
			sd.user_calls_with_http_request("AddPlaceAPI", "POST");
			sd.verify_placeId_created_maps_to_using("Vikesh", "GetPlaceAPI");
		}
	}
}
