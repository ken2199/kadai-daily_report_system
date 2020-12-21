package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity


@NamedQueries({
    @NamedQuery(
            name = "getAllIines",
            query = "SELECT i FROM Iine AS i ORDER BY i.id DESC"
            ),
    @NamedQuery(
            name = "getIinesCount",
             query = "SELECT COUNT(i) FROM Iine  AS i"
       //     query = "SELECT COUNT(i) FROM Iine WHERE report_id =any(SELECT id FROM Report) AS i"
            ),

    @NamedQuery(
            name = "getReportAllIines",
            query = "SELECT i FROM Iine AS i WHERE i.report = :report ORDER BY i.id DESC"
        ),
    @NamedQuery(
            name = "getReportIinesCount",
            query = "SELECT COUNT(i) FROM Iine AS i WHERE i.report = :report"
        ),
    @NamedQuery(
            name = "getIineCheck",
            query = "SELECT COUNT(i) FROM Iine AS i WHERE i.report = :report AND i.employee = :employee"
            )


})

@Table(name = "iines")

public class Iine{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}