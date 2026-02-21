# ---------- FRONTEND BUILD ----------
    FROM node:20-alpine AS frontend-build
    WORKDIR /app/frontend
    COPY front-end/package*.json ./
    RUN npm install
    COPY front-end/ .
    RUN npm run build
    
    
    # ---------- BACKEND BUILD ----------
    FROM gradle:8.5-jdk21 AS backend-build
    WORKDIR /app
    COPY back-end/ ./back-end/
    
    # Copy frontend build into Spring static folder
    COPY --from=frontend-build /app/frontend/build \
        /app/back-end/src/main/resources/static
    
    WORKDIR /app/back-end
    RUN gradle bootJar --no-daemon
    
    
    # ---------- RUNTIME ----------
    FROM eclipse-temurin:21-jre-alpine
    WORKDIR /app
    
    COPY --from=backend-build /app/back-end/build/libs/*.jar app.jar
    
    EXPOSE 8080
    
    ENTRYPOINT ["java","-jar","app.jar"]