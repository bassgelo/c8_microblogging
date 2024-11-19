package zeebe.cloud;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleJobHandler implements JobHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExampleJobHandler.class);

    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        // here: business logic that is executed with every job
        logger.info("Handling job: {}", job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
