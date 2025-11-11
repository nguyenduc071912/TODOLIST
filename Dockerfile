# STAGE 1: Build Stage
# Sử dụng image Maven chính thức để build ứng dụng
FROM maven:3.8.7-openjdk-17 AS builder

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép pom.xml và mã nguồn
COPY pom.xml .
COPY src /app/src

# Chạy Maven build (bỏ qua kiểm thử)
RUN mvn clean install -DskipTests

# ----------------------------------------------------------------------

# STAGE 2: Production Stage
# Sử dụng image JRE mỏng (JRE-only) để giảm kích thước image
FROM openjdk:17-jre-slim

# Mở cổng mặc định của Spring Boot
EXPOSE 8080

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR đã build từ giai đoạn builder
# Đổi tên JAR thành app.jar cho tiện tham chiếu
COPY --from=builder /app/target/TodoList-0.0.1-SNAPSHOT.jar app.jar

# Lệnh chạy ứng dụng Spring Boot khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]