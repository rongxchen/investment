package rongxchen.investment.models.vo.users;

import lombok.Data;

@Data
public class LoginVO {

    private UserInfoVO userInfo;

    private String accessToken;

    private String refreshToken;

}
