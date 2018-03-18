package com.codingchallenge.subscriptionsystem.common.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class User implements IUser, ConsistentObject{

    @ApiModelProperty(position = 1, required = true, dataType = "string", name = "email")
    @JsonInclude
    private String email;
    @ApiModelProperty(position = 2, required = true, dataType = "string", name = "birthDate")
    @JsonInclude
    private Date birthDate;
    @ApiModelProperty(position = 3, required = true, dataType = "string", name = "consent",
            allowableValues = "true, false")
    @JsonInclude
    private Boolean consent;
    @ApiModelProperty(position = 4, dataType = "string", name = "firstName",
            allowEmptyValue = true)
    @JsonInclude
    private String firstName;
    @ApiModelProperty(position = 5, dataType = "string", name = "gender",
            allowEmptyValue = true, allowableValues = "MALE, FEMALE")
    @JsonInclude
    private Gender gender;

    @Override
    public boolean validateConsistency(){
        return ! StringUtils.isEmpty(email)
                && birthDate != null
                && consent != null
                && validateUserEmail(email);
    }

    private static boolean validateUserEmail(String userEmail){
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }

}
