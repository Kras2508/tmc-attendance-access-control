/**
 * PRESENTATION layer of the Access Management module (controllers / scheduled triggers).
 * <p>Rules: calls only the module facade in {@code business} (the facade is the single entry point);
 * never calls services, repositories or ports directly (dependency rule 4, §3.3.1).
 */
package vn.tmc.accessmanagement.presentation;
