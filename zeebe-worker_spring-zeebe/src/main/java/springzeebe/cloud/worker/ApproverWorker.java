package springzeebe.cloud.worker;

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

        //Read variables from the process this way
        String message = (String)job.getVariablesAsMap().get("message");

        boolean approved = randomBooleanService.getRandomBoolean(message);

        // Return generated variable to Zeebe
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("approved", approved);
        return variables;
    }

}
