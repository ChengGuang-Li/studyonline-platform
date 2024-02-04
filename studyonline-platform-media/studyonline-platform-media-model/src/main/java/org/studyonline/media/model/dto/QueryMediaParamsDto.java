package org.studyonline.media.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryMediaParamsDto {

    @ApiModelProperty("Media asset file name")
    private String filename;

    @ApiModelProperty("Media asset type")
    private String fileType;

    @ApiModelProperty("Approval Status")
    private String auditStatus;
}
