package org.studyonline.ucenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:10 pm
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String cellphone;
    private String checkcode;
    private String checkcodekey;
    private String confirmpwd;
    private String email;
    private String nickname;
    private String password;
    private String username;
}
