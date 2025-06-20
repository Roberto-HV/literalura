package com.example.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosRespuesta {
    @JsonProperty("docs")
    private List<DatosLibro> docs;

    public List<DatosLibro> getDocs() {
        return docs;
    }

    public void setDocs(List<DatosLibro> docs) {
        this.docs = docs;
    }
}
