package zeebe.cloud;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.worker.JobWorker;
import java.time.Duration;


public class JobWorkerCreation {

    public static void main(final String[] args) {
        final String defaultAddress = "localhost:26500";
        final String envVarAddress = System.getenv("ZEEBE_ADDRESS");
        final String jobType = System.getenv().getOrDefault("JOB_TYPE", "foo");

        try (final ZeebeClient client = createZeebeClient(defaultAddress, envVarAddress)) {
            System.out.println("Opening job worker.");

            try (final JobWorker workerRegistration =
                         client
                                 .newWorker()
                                 .jobType(jobType)
                                 .handler(new ExampleJobHandler())
                                 .timeout(Duration.ofSeconds(10))
                                 .open()) {
                System.out.println("Job worker opened and receiving jobs.");

                // run until System.in receives exit command
                Utility.waitUntilSystemInput("exit");
            }
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
}
