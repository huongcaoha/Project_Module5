# 1. Sử dụng JDK 21
FROM eclipse-temurin:21-jdk-alpine

# 2. Đặt thư mục làm việc
WORKDIR /app

# 3. Copy và ĐỔI TÊN thành app.jar cho dễ quản lý
COPY build/libs/Module5_Project-0.0.1-SNAPSHOT.jar app.jar

# 4. Bây giờ lệnh này sẽ tìm thấy file app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]