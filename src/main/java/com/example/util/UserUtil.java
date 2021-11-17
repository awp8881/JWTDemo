package com.example.util;


/**
 * @Description: 获取/设置本次用户数据
 */
public class UserUtil {

    public static String getUserName(){
        return ThreadLocalUtil.get( "USER_NAME" );
    }

    public static Long getUsId(){
        return ThreadLocalUtil.get( "USER_ID" );
    }

    public static void setUsId( Long user_id ){
        ThreadLocalUtil.set( "USER_ID", user_id );
    }

    public static void setUserName( String  userName ){
        ThreadLocalUtil.set( "USER_NAME",userName );
    }

}
