package com.sonda.transporte.consola.agente.firmware.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by daniel.carvajal on 21-12-2018.
 */

@Data
public class ArtifactInfoDTO {

    //TODO complementar este artefacto cuando se traspase a ftp

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "repositoryId")
    private String repositoryId;

    @JsonProperty(value = "groupId")
    private String groupId;

    @JsonProperty(value = "artifactId")
    private String artifactId;

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "packaging")
    private String packaging;

    @JsonProperty(value = "size")
    private String size;

    @JsonProperty(value = "url")
    private String url;

}
