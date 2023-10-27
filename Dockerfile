FROM bitnami/wildfly:29.0.1
COPY target/WarehouseApi-1.0-SNAPSHOT.war /app
EXPOSE 3030
ENV WILDFLY_USERNAME=user, WILDFLY_PASSWORD=password