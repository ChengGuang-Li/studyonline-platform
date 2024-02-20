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
public class FindPswDto {
    String cellphone;
    String email;
    String checkcodekey;
    String checkcode;
    String password;
    String confirmpwd;
}
