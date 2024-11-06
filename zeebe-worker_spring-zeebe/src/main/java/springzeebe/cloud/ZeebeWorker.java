package springzeebe.cloud;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources = {"classpath:Twitter2.bpmn", "classpath:Twitter3.bpmn"} )
public class ZeebeWorker {

    public static void main(String[] args) {
        SpringApplication.run(ZeebeWorker.class, args);
    }

}
