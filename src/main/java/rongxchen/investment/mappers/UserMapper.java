package rongxchen.investment.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import rongxchen.investment.models.po.User;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users where email = #{email}")
    User selectByEmail(String email);

    @Select("select * from users where app_id = #{appId}")
    User selectByAppId(String appId);

}
