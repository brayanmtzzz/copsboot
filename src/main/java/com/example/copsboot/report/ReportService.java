package com.example.copsboot.report;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report createReport(CreateReportParameters parameters) {
        Report report = new Report(repository.nextId(), parameters.reporterId(), parameters.dateTime(), parameters.description());
        return repository.save(report);
    }
}
