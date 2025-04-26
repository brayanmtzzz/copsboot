package com.example.copsboot.report;

import com.example.orm.jpa.AbstractEntityId;

import java.util.UUID;

public class ReportId extends AbstractEntityId<UUID> {

    protected ReportId() {
    }

    public ReportId(UUID id) {
        super(id);
    }
}
