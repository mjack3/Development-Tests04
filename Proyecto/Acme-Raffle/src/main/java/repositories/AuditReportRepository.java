package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.AuditReport;

@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, Integer>{

}
