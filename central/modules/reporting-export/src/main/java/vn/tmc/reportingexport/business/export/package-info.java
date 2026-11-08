/**
 * PIPELINE for the monthly HR/Payroll export (§3.2.3, ADR-05): collect → validate → transform → deliver.
 * <p>Each filter is one {@link vn.tmc.reportingexport.business.export.ExportFilter} (SRP); new steps are new filters (OCP).
 */
package vn.tmc.reportingexport.business.export;
