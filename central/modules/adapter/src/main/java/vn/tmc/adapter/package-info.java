/**
 * <b>Adapter</b> module — integration module (Table 13, §3.3.1). Has no layers.
 * <p>Each adapter REALIZES one port owned by a domain module and calls one external system through a
 * dedicated client (Ports &amp; Adapters, ADR-04, OCP/LSP/DIP §5.1).
 * <p>May depend only on {@code ..persistence.port..} packages of other modules and the shared kernel —
 * never on their business, repositories or entities. No other module depends on this module.
 */
package vn.tmc.adapter;
