package com.qf.pass;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by  .Life on 2019/07/20/0020 16:18
 */
public class BCryptUtil {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public static boolean checkPassword(String hash,String password){
        return BCrypt.checkpw(password,hash);
    }
}
