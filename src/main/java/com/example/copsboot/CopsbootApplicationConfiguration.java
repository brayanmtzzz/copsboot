package com.example.copsboot;

import com.example.copsboot.report.ReportId;
import com.example.copsboot.user.UserId;
import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopsbootApplicationConfiguration {

    @Bean
    public UniqueIdGenerator<UserId> userIdGenerator() {
        return new InMemoryUniqueIdGenerator<>(UserId::new);
    }

    @Bean
    public UniqueIdGenerator<ReportId> reportIdGenerator() {
        return new InMemoryUniqueIdGenerator<>(ReportId::new);
    }
}
