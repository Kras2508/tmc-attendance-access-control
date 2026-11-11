# Hướng dẫn làm việc cho team

Tài liệu này dành cho các thành viên trong nhóm. README mô tả kiến trúc, còn file này mô tả **cách làm** để không phá kiến trúc.

## 1. Quy tắc bắt buộc (vi phạm thì `./mvnw verify` sẽ fail)

1. **Chỉ gọi module khác qua `api`.** Ví dụ Gate Access muốn ghi attendance thì gọi `AttendanceRecordingApi`, **không** import `AttendanceRecordingService` hay `AttendanceEventRepository`.
2. **Mỗi module chỉ đọc/ghi schema của mình.** Bảng mới thì đặt migration trong `src/main/resources/db/migration/<schema>/` của chính module đó, đánh số theo dải của module (audit `V1xx`, access_management `V2xx`, identity_qr `V3xx`, gate_access `V4xx`, attendance `V5xx`, reporting_export `V6xx`). Không tạo foreign key sang schema khác.
3. **Không tạo dependency vòng.** Không module nào được phụ thuộc vào `reporting-export` hay `adapter`. Nếu cần thêm dependency giữa các module thì phải sửa `pom.xml`, và sửa `pom.xml` là thay đổi kiến trúc: phải thống nhất trong nhóm và cập nhật report (Figure 1).
4. **Phân lớp đóng:** `presentation → business → persistence`. Controller chỉ gọi `*Facade`, không gọi thẳng service hay repository.
5. **Hệ thống bên ngoài chỉ được gọi qua port.** Domain module định nghĩa interface trong `persistence/port`, còn class implement nằm trong `central/modules/adapter`.
6. **Kiosk không import gì từ central**, chỉ dùng `shared-kernel` và `qr-token-lib`. Kiosk giao tiếp với central qua HTTP (`CentralGateway`).
7. **Inject qua constructor, phụ thuộc vào interface** (DIP). Không dùng `@Autowired` trên field.

## 2. Thêm một tính năng mới

1. Xác định FR thuộc module nào (Table 5 trong report, hoặc `package-info.java` của module).
2. Nếu module khác cần dùng tính năng đó: thêm method vào interface `api` và DTO vào `api/dto`. Dùng `record`, không trả entity ra ngoài.
3. Viết logic trong `business/service`, rồi cho facade gọi service.
4. Thêm endpoint trong `presentation` (controller gọi facade).
5. Thêm migration và repository trong `persistence` nếu cần.
6. Chạy `./mvnw verify` trước khi tạo PR.

## 3. Thay đổi kiến trúc

Mọi quyết định mới (ví dụ thêm module, đổi cơ chế giao tiếp, thêm công nghệ) đều phải viết ADR mới trong `architecture/adr/`, dùng `ADR-template.md`. Report Section 7 (Additional Architecture Decisions) đang trống và sẽ lấy nội dung từ các ADR này.

## 4. Git workflow

* Nhánh `main` luôn build được. Không push thẳng lên `main`.
* Mỗi việc một nhánh: `feature/<module>-<mô-tả>`, ví dụ `feature/gate-access-handle-scan`.
* Commit message: `<module>: <mô tả>`, ví dụ `attendance: record event idempotently by eventId`.
* PR cần ít nhất 1 người review. Người review kiểm tra các quy tắc ở mục 1.

## 5. Phân công Milestone 2

Task 2 yêu cầu implement ít nhất một module chính. Gợi ý: làm trọn **Flow A (gate scan)** vì luồng này đi qua nhiều module và thể hiện rõ nhất kiến trúc.

| Phần | Module / thư mục | Người phụ trách |
|---|---|---|
| Value types, UnitOfWork, Clock | `shared/shared-kernel`, `central/tmc-app/config` | _TBD_ |
| Token library V1–V5 | `shared/qr-token-lib` | _TBD_ |
| Identity & QR (credential, validateToken) | `central/modules/identity-qr` | _TBD_ |
| Gate Access (AccessDecisionService, KioskSyncService) | `central/modules/gate-access` | _TBD_ |
| Attendance + Audit | `central/modules/attendance`, `central/modules/audit` | _TBD_ |
| Access Management (login, RBAC, kiosk auth) | `central/modules/access-management` | _TBD_ |
| Reporting & Export + Adapter | `central/modules/reporting-export`, `central/modules/adapter` | _TBD_ |
| Kiosk app | `kiosk/kiosk-app` | _TBD_ |

Mỗi class hiện có Javadoc ghi rõ phần tương ứng trong report (FR, ADR, Figure, §). Tìm `TODO(M2)` để thấy các chỗ cần implement.
