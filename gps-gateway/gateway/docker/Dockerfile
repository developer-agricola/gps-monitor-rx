FROM debian:jessie-slim
MAINTAINER daniel.carvajal <jdanieldeveloper@gmail.com>

# binaries
COPY ${project.build.directory}/${gps-gateway.final.name}-image /${gps-gateway.final.name}-image

# change permissions
RUN chmod +x -R /${gps-gateway.final.name}-image

WORKDIR /${gps-gateway.final.name}-image/bin

ENTRYPOINT ["./GpsGatewayApp"]









