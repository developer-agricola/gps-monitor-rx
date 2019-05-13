package com.sonda.transporte.consola.agente.firmware.infrastructure.integration.archiva;

import com.sonda.transporte.consola.agente.firmware.infrastructure.dto.FirmwareDTO;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.DataAccessException;
import com.sonda.transporte.consola.agente.firmware.infrastructure.exceptions.GatewayException;
import com.sonda.transporte.consola.agente.firmware.infrastructure.integration.Gateway;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

@Getter
@Setter
public class ArchivaGateway implements Gateway {

    private URI host;

    private CloseableHttpClient httpClient;

    private HttpClientContext httpClientContext;

    private static final String HTTP_AUTHORIZATION_TYPE = "Basic ";

    //server-base-path/repository
    private static final String FILE_URL_BASE_FORMAT = "/repository/{0}";

    //artifactId-version.packageId
    private static final String FILE_FORMAT = "{0}-{1}.{2}";

    //groupId/artifactId/version/artifactId-version.packageId
    private static final String FILE_URL_FORMAT = "/{0}/{1}/{2}/{3}-{4}.{5}";

    private static final String FILE_MD5_FORMAT = "{0}-{1}.{2}.md5";

    //groupId/artifactId/version/artifactId-version.packageId
    private static final String FILE_MD5_URL_FORMAT = "/{0}/{1}/{2}/{3}-{4}.{5}.md5";

    protected static final Properties appProperties = UtilProperties.getProperties();

    public static final UtilLogger log = UtilLogger.getLogger(ArchivaGateway.class);

    public static ArchivaGateway newGateway() {
        return new ArchivaGateway();
    }

    public void initialize() throws URISyntaxException {
        // credentials
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_USERNAME_PROPERTY_KEY),
                appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_PASSWRD_PROPERTY_KEY));
        provider.setCredentials(AuthScope.ANY, credentials);

        AuthCache authCache = new BasicAuthCache();
        authCache.put(new HttpHost(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_PROPERTY_KEY)), new BasicScheme());

        // Add AuthCache to the execution context
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCredentialsProvider(provider);
        httpClientContext.setAuthCache(authCache);
        //
        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectionRequestTimeout(
                                        Integer.parseInt(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_REQUEST_CONNECT_TIMEOUT)))
                                .setConnectTimeout(
                                        Integer.parseInt(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_CONNECT_TIMEOUT)))
                                .setSocketTimeout(
                                        Integer.parseInt(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_SOCKET_TIMEOUT)))
                                .setContentCompressionEnabled(true)
                                .build())
                .setDefaultCredentialsProvider(provider)
                .build();

        // host
        host = new URIBuilder()
                .setScheme("http")
                    .setHost(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_PROPERTY_KEY))
                .setPort(Integer.valueOf(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_PORT_PROPERTY_KEY)))
         .build();
    }

    public boolean isAvailable() {
        return ping();
    }

    public URI getFirmwareURI(FirmwareDTO firmwareDTO) throws GatewayException {
        URI firmwareUri = null;
        try {
            String path =
                    MessageFormat.format(FILE_URL_BASE_FORMAT,
                            appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_REPOSITORY_PROPERTY_KEY))
                            .concat(
                    MessageFormat.format(FILE_URL_FORMAT,
                            firmwareDTO.getGroupId().replace(".", "/"),
                            firmwareDTO.getArtifactId(), firmwareDTO.getVersion(),
                            firmwareDTO.getArtifactId(), firmwareDTO.getVersion(), firmwareDTO.getPackageId()));
            // set uri
            firmwareUri =  new URIBuilder()
                    .setScheme(host.getScheme())
                    .setHost(host.getHost())
                    .setPort(host.getPort())
                    .setPath(path)
            .build();

        } catch (URISyntaxException e) {
            throw new GatewayException("Error al crear la URI http de archiva", e);
        }
        return firmwareUri;
    }

    public URI getFirmwareMd5URI(FirmwareDTO firmwareDTO) throws GatewayException {
        URI firmwareUri = null;
        try {
            String path =
                    MessageFormat.format(FILE_URL_BASE_FORMAT,
                            appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_REPOSITORY_PROPERTY_KEY))
                            .concat(
                                    MessageFormat.format(FILE_MD5_URL_FORMAT,
                                            firmwareDTO.getGroupId().replace(".", "/"),
                                            firmwareDTO.getArtifactId(), firmwareDTO.getVersion(),
                                            firmwareDTO.getArtifactId(), firmwareDTO.getVersion(), firmwareDTO.getPackageId()));
            // set uri
            firmwareUri =  new URIBuilder()
                    .setScheme(host.getScheme())
                    .setHost(host.getHost())
                    .setPort(host.getPort())
                    .setPath(path)
                    .build();

        } catch (URISyntaxException e) {
            throw new GatewayException("Error al crear la URI http de archiva para el archivo md5", e);
        }
        return firmwareUri;
    }

    public long getFirmwareLength(URI from) throws GatewayException {
        long length;
        try {
            HttpGet httpGet = new HttpGet(from);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpClientContext)) {

                if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (Objects.nonNull(httpEntity)) {
                        length = httpEntity.getContentLength();
                    } else {
                        throw new DataAccessException(String.format("Error al procesar las respuesta del recurso [%s]", from));
                    }

                } else {
                    throw new IOException(
                            String.format("No se puede encontrar el recurso [%s] HTTPStatus[%s][%s]", from, httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GatewayException(e.getMessage(), e);
        }
        return length;
    }

    public boolean ping() {
        boolean isAvailable = true;
        try {
            URI endPoint = new URIBuilder()
                        .setScheme(host.getScheme())
                            .setHost(host.getHost())
                            .setPort(host.getPort())
                    .setPath(appProperties.getProperty(UtilProperties.ARCHIVA_SERVER_API_PINGSERVICE_PING_ENDPOINT))
            .build();

        // httpGet with out context
        HttpGet httpGet = new HttpGet(endPoint);

        // set custom header setting
        httpGet.setHeader(HttpHeaders.REFERER, host.toString());

        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet, httpClientContext)) {
            if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                isAvailable = true;
            } else {
                isAvailable = false;

                log.warning("El servicio de ping en archiva no esta disponible en este momento!!!");
            }
        }

        }catch (URISyntaxException e){
            isAvailable = false;

            log.error("Se a provocado una exception al crear la url del host para el servicio de ping");
            log.error(e.getMessage(), e);

        } catch (Exception e) {
            isAvailable = false;

            log.warning("Se ha provocado una exception al invocar al servicio de ping de archiva");
            log.error(e.getMessage(), e);
        }
        return isAvailable;
    }

    public static String getFileFormat() {
        return FILE_FORMAT;
    }

    public static String getFileMd5Format() {
        return FILE_MD5_FORMAT;
    }
}
