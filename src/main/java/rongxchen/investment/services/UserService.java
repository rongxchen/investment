package rongxchen.investment.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rongxchen.investment.mappers.UserMapper;
import rongxchen.investment.models.JwtParamMap;
import rongxchen.investment.models.Response;
import rongxchen.investment.models.dto.users.LoginDTO;
import rongxchen.investment.models.dto.users.RegisterDTO;
import rongxchen.investment.models.po.User;
import rongxchen.investment.models.vo.users.LoginVO;
import rongxchen.investment.models.vo.users.UserInfoVO;
import rongxchen.investment.util.EncUtil;
import rongxchen.investment.util.IdGenerator;
import rongxchen.investment.util.JwtUtil;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public Response<Object> login(LoginDTO loginDTO) {
        User user = userMapper.selectByEmail(loginDTO.getEmail());
        if (user == null || user.getIsDeleted() == 1 ||
                !user.getPassword().equals(EncUtil.calculateSHA256(loginDTO.getPassword() + user.getSalt()))) {
            return Response.failed("wrong email or password").body(null);
        }
        if (user.getIsActive() == 0) {
            return Response.failed("account is inactive");
        }
        LoginVO loginVO = new LoginVO();
        // set token
        JwtParamMap map = new JwtParamMap();
        map.setAppId(user.getAppId());
        String accessToken = JwtUtil.generateToken(map, 30);
        String refreshToken = JwtUtil.generateToken(map, 60 * 24 * 30);
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        // set user info
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAppId(user.getAppId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setEmail(user.getEmail());
        loginVO.setUserInfo(userInfoVO);
        return Response.builder()
                .message("login ok")
                .body(loginVO);
    }

    public Response<Boolean> register(RegisterDTO registerDTO) {
        if (userMapper.selectByEmail(registerDTO.getEmail()) != null) {
            return Response.<Boolean>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("email already registered")
                    .body(false);
        }
        String appId;
        do {
            appId = IdGenerator.UUID("users");
        } while (userMapper.selectByAppId(appId) != null);
        String salt = EncUtil.calculateMD5(LocalDate.now().toString());
        // insert to db
        User user = new User();
        user.setAppId(appId);
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(EncUtil.calculateSHA256(registerDTO.getPassword() + salt));
        user.setSalt(salt);
        user.setIsDeleted(0);
        user.setIsActive(1);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        userMapper.insert(user);
        return Response.<Boolean>builder()
                .message("register ok")
                .body(true);
    }

    public String refreshToken(String token) {
        JwtParamMap paramMap = JwtUtil.decodeToken(token);
        String appId = paramMap.getAppId();
        if (appId != null) {
            User user = userMapper.selectByAppId(appId);
            if (user.getIsDeleted() != 1 || user.getIsActive() != 0) {
                return JwtUtil.generateToken(paramMap, 30);
            }
        }
        return null;
    }

}
