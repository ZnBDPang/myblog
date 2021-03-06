package com.pjl.blog.myblog.mapper;

import com.pjl.blog.myblog.dto.AvatarDto;
import com.pjl.blog.myblog.dto.UserDto;
import com.pjl.blog.myblog.model.NotificationVO;
import com.pjl.blog.myblog.model.UserVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insertUser(UserVO user);

    @Select("select * from user where token = #{token} order by gmt_create asc limit 0,1")
    UserVO findByToken(String token);

    @Select("select * from user where id = #{creator}")
    UserVO findById(Integer creator);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl},user_name = #{userName},ip_address = #{ipAddress} " +
            "where id = #{id}")
    void updateUser(UserVO dbUser);

    @Select("select * from user where account_id = #{token}")
    UserVO findByAccountId(String token);

    @Select("select count(1) from user where user_name = #{userName} and del_flag = 0")
    @Options(keyColumn = "count(1)")
    int findByUserName(String userName);

    @Select("select count(1) from user where mobile = #{mobile} and del_flag = 0")
    @Options(keyColumn = "count(1)")
    int findByMobile(String mobile);

    @Insert("insert into user (name,gmt_create,gmt_modified,avatar_url,user_name,password,mobile,bio,mail,user_type,ip_address) values" +
            "(#{name},#{gmtCreate},#{gmtModified},#{avatarUrl},#{userName},#{password},#{mobile},#{bio},#{mail},#{userType},#{ipAddress})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int registered(UserVO user);

    @Select("select * from user where user_name = #{userName} and password = #{password} and del_flag = 0")
    UserVO verifyUser(UserDto userDto);

    @Update("update user set avatar_url = #{avatarUrl} where id = #{userId}")
    int modifyAvatar(AvatarDto avatarDto);

    @Insert("insert into notification (target_id,target_type,sender_id,receive_id,noti_content,action,gmt_create) values " +
            "(#{targetId},#{targetType},#{senderId},#{receiveId},#{notiContent},#{action},#{gmtCreate})")
    void insertNotification(NotificationVO notification);

    @Select("select * from user where mail = #{mail} and del_flag = 0")
    UserVO findByMail(String mail);

    @Update("update notification set status = 1 where target_id = #{id} and del_flag = 0")
    void readNotification(Integer id);

    @Select("select user_type from user where id = #{id} and del_flag = 0")
    int verifyUserType(Integer id);

    @Select("select count(1) from article where creator = #{userId} and type = #{type} and del_flag = 0")
    Integer countDraft(Integer userId, int type);
}
