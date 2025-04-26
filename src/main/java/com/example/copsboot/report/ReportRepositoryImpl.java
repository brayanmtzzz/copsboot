package com.example.copsboot.report;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final UniqueIdGenerator<ReportId> generator = new InMemoryUniqueIdGenerator<>(ReportId::new);

    @Override
    public ReportId nextId() {
        return generator.getNextUniqueId();
    }
}
