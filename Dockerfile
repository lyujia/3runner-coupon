FROM eclipse-temurin:21-jre

WORKDIR /coupon

COPY target/coupon.jar /coupon/coupon.jar

EXPOSE 8083

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Run the entrypoint script
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
