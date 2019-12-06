package com.nts.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class User implements Serializable {
    @Id
    @KeySql(sql = "select uuid()", order = ORDER.BEFORE)
    @ExcelProperty("id")
    private String id;
    @ExcelProperty("手机号")
    private String tel;
    @ExcelProperty("密码")
    private String password;
    @ExcelProperty("盐")
    private String salt;
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("昵称")
    private String nickName;
    @ExcelProperty("性别")
    private String sex;
    @ExcelProperty("个性签名")
    private String sign;
    @ExcelProperty("地址")
    private String address;
    @ExcelProperty("头像")
    private String photo;
    @ExcelProperty("注册时间")
    private Date registDate;
    @ExcelProperty("最后登录时间")
    private Date lastLogin;
}
