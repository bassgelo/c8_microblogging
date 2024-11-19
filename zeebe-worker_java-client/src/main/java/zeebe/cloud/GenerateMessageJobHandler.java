package zeebe.cloud;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class GenerateMessageJobHandler implements JobHandler {

    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        // here: business logic specific to generating messages
        System.out.println("Generating message: " + job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
