package springzeebe.cloud.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GeneratorWorker {

    @JobWorker(type = "processTwittter_generateMessage")
    public Map<String, Object> generateMessage(final ActivatedJob job) {

        // Generate a random String, with random UUID and convert it to a string
        String generatedString = UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", "");

        // Return generated variable to Zeebe
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("message", generatedString);
        return variables;
    }
}
