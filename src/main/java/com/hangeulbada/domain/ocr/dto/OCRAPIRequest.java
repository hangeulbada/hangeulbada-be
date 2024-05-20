package com.hangeulbada.domain.ocr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OCRAPIRequest {
    private String version;
    private String requestId;
    private String timestamp;
    private String lang;
    private JSONArray images;
    private String requestType;
}
