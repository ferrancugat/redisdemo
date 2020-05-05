package org.demo.nosql.redisdemo.configuration;

import org.demo.nosql.redisdemo.command.OperationExecutor;
import org.demo.nosql.redisdemo.command.SequentialOperationExecutor;
import org.demo.nosql.redisdemo.storage.InMemoryExpireStore;
import org.demo.nosql.redisdemo.storage.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisDemoConfig {

    @Bean
    public Store store() {
        return new InMemoryExpireStore();
    }

    @Bean
    public OperationExecutor executor(Store store) {
        return new SequentialOperationExecutor(store);
    }

}
