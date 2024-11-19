package zeebe.cloud;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateMessageJobHandler implements JobHandler {
    private static final Logger logger = LoggerFactory.getLogger(GenerateMessageJobHandler.class);

    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        // here: business logic specific to generating messages
        System.out.println("Generating message: " + job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
