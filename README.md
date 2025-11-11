# 🛡️ Todo List: Hybrid Security API & Web Application

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://www.java.com/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

***

## 💡 Tổng quan Dự án

Đây là một ứng dụng quản lý công việc (To-Do List) được xây dựng trên nền tảng **Spring Boot 3+** với kiến trúc **Hybrid Security**. Dự án được thiết kế để phục vụ đồng thời hai loại client: các ứng dụng client sử dụng **Stateless REST API** (dùng JWT) và giao diện Web truyền thống **Stateful Web Interface** (dùng Session).

## 🔑 Cấu trúc Công nghệ Nổi bật

| Thành phần | Công nghệ | Mục đích/Tính năng |
| :--- | :--- | :--- |
| **Backend Core** | Spring Boot 3, Java 17 | API RESTful và MVC Controllers. |
| **Bảo mật** | **Spring Security 6+** | Khung bảo mật chính (Xác thực và Ủy quyền). |
| **Xác thực API** | **JWT (JJWT)** | Cơ chế **Stateless** cho các ứng dụng client. |
| **Web Interface** | **Thymeleaf** | Giao diện quản lý User/Todo (mô hình **Stateful**). |
| **Persistence** | Spring Data JPA | Tải User và dữ liệu Todo từ Database. |

***

## ⚔️ Các Quyết định Kỹ thuật Chính (Technical Highlights)

1.  **Phân tách Hybrid Security:**
    * Hệ thống cấu hình **`SecurityFilterChain`** để hỗ trợ đồng thời hai luồng: Stateless (JWT) và Stateful (Session/Form Login) trên cùng một ứng dụng.
    * Đã giải quyết thành công vấn đề xung đột Filter bằng cách sử dụng phương thức **`shouldNotFilter`** trong `JwtAuthenticationFilter` để giữ cho Filter JWT chỉ chạy trên các endpoint API (`/project/**`).
2.  **Ủy quyền Cấp độ Dữ liệu (`@PreAuthorize`):**
    * Triển khai **`@EnableMethodSecurity`** và sử dụng biểu thức **SpEL** (ví dụ: `@userServiceImpl.isOwner(#id)`) trên Service Layer để thực thi **Data Ownership** (Quyền sở hữu dữ liệu). Điều này đảm bảo User chỉ có thể thao tác với dữ liệu của chính họ.
3.  **Quản lý Role Tùy chỉnh:**
    * Tích hợp **`CustomUserDetailsService`** để tải thông tin người dùng và phân quyền (ADMIN/USER) từ Database, áp dụng phân quyền chi tiết (ví dụ: `/project/users/**` chỉ dành cho ADMIN).
4.  **Thiết kế RESTful API:**
    * Tuân thủ các nguyên tắc RESTful cho các thao tác CRUD và xử lý lỗi xác thực bằng cách sử dụng `AuthenticationEntryPoint` tùy chỉnh (trả về 401 Unauthorized).

***

## 🚀 Hướng dẫn Khởi động Nhanh

### Yêu cầu Tiên quyết
* Java 17+
* Maven
* Docker (để triển khai)

### Khởi chạy Ứng dụng
1.  **Clone (Sao chép) Mã nguồn:**
    ```bash
    # Thay [LINK_CỦA_BẠN] bằng đường dẫn repository thực tế
    git clone [LINK_CỦA_BẠN]
    cd [TÊN_THƯ_MỤC_DỰ_ÁN] 
    ```

2.  **Cấu hình Database:** Cập nhật thông tin kết nối Database trong `application.properties`.

3.  **Build và Run (Sử dụng Docker):**
    * **Build Image:**
      ```bash
      docker build -t todolist-app:latest .
      ```
    * **Run Container:**
      ```bash
      docker run -d -p 8080:8080 --name todo-instance todolist-app:latest
      ```

### Kiểm thử và Truy cập

* **Tài khoản Mặc định:** `admin@test.com` / `123456` và `user@test.com` / `123456`.

| Loại Truy cập | Endpoint | Mục đích |
| :--- | :--- | :--- |
| **WEB Interface (Session)** | `http://localhost:8080/login` | Truy cập bằng trình duyệt để đăng nhập và kiểm tra giao diện quản lý User/Todo. |
| **API Testing (JWT)** | `POST http://localhost:8080/project/auth/login` | Dùng Postman để lấy JWT Token và kiểm tra các endpoint API. |