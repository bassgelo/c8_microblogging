package zeebe.cloud;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class ExampleJobHandler implements JobHandler {
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        // here: business logic that is executed with every job
        System.out.println(job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
