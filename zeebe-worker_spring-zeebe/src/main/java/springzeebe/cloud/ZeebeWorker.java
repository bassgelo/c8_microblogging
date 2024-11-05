package springzeebe.cloud;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ZeebeWorker {

    public static void main(String[] args) {
        SpringApplication.run(ZeebeWorker.class, args);
    }

    @JobWorker(type = "processTwittter_publishMessage")
    public Map<String, Object> orchestrateSomething(final ActivatedJob job) {

        // Do the business logic
        System.out.println("Yeah, now you can orchestrate something :-) You could use data from the process variables: " + job.getVariables());

        // Probably add some process variables
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("resultValue", 42);
        return variables;
    }

}
