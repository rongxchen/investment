package rongxchen.investment.models.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;

@TableName("users")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private String appId;

    private String username;

    private String email;

    private String password;

    private String salt;

    @TableLogic
    @TableField("is_deleted")
    private int isDeleted;

    @TableField("is_active")
    private int isActive;

    @TableField("created_at")
    private LocalDate createdAt;

    @TableField("updated_at")
    private LocalDate updatedAt;

}
