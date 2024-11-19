package zeebe.cloud;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.worker.JobWorker;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JobWorkerCreation {

    public static void main(final String[] args) {
        final String defaultAddress = "localhost:26500";
        final String envVarAddress = System.getenv("ZEEBE_ADDRESS");
        final List<String> jobTypes = List.of(
                "processTwittter_publishMessage",
                "processTwittter_approveMessage",
                "processTwittter_generateMessage"
        );

        try (final ZeebeClient client = createZeebeClient(defaultAddress, envVarAddress)) {
            System.out.println("Opening job workers.");

            for (String jobType : jobTypes) {
                JobWorker workerRegistration = client
                        .newWorker()
                        .jobType(jobType)
                        .handler(getJobHandler(jobType))
                        .timeout(Duration.ofSeconds(10))
                        .open();
                System.out.println("Job worker for job type " + jobType + " opened and receiving jobs.");
            }

            // run until System.in receives exit command
            Utility.waitUntilSystemInput("exit");
        }
    }

    private static ZeebeClient createZeebeClient(String defaultAddress, String envVarAddress) {
        final ZeebeClientBuilder clientBuilder;
        if (envVarAddress != null) {
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(envVarAddress);
        } else {
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(defaultAddress).usePlaintext();
        }
        return clientBuilder.build();
    }

    private static ExampleJobHandler getJobHandler(String jobType) {
        Map<String, ExampleJobHandler> jobHandlers = Map.of(
                "processTwittter_publishMessage", new PublishMessageJobHandler(),
                "processTwittter_approveMessage", new ApproveMessageJobHandler(),
                "processTwittter_generateMessage", new GenerateMessageJobHandler()
        );
        return jobHandlers.getOrDefault(jobType, new ExampleJobHandler());
    }
}
