package com.codingchallenge.subscriptionsystem.common.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class Newsletter implements INewsletter, ConsistentObject {

    @ApiModelProperty(position = 1, required = true, dataType = "string", name = "email")
    @JsonInclude
    private String newsletterId;
    @ApiModelProperty(position = 1, dataType = "string", name = "email")
    @JsonInclude
    private String newsletterName;

    @Override
    public boolean validateConsistency(){
        return ! StringUtils.isEmpty(newsletterId);
    }

}
