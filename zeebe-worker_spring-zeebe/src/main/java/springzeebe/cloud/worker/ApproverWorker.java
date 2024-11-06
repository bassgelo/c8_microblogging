package springzeebe.cloud.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springzeebe.cloud.services.RandomBooleanService;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApproverWorker {

    @Autowired
    private RandomBooleanService randomBooleanService;

    @JobWorker(type = "processTwittter_approveMessage")
    public Map<String, Object> approveMessage(final ActivatedJob job) {

        //3 Blocks of code

        //Block 1: read variables from the process instance
        //Read variables from the process this way
        String message = (String)job.getVariablesAsMap().get("message");

        //Block 2: call the actual logic
        //It is better to call the logic not directly here in this class, instead use a bean that has the real logic, and pass the variables read from the process
        //This enables the separation between, the actual business logic and the code that is required by the Engine.
        boolean approved = randomBooleanService.getRandomBoolean(message);

        //Block 3: return to the process instance the result of the processing (if necessary)
        // Return generated variable to Zeebe
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
        return variables;
    }

}
