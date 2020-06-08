package com.example.smartagriculture.View.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.smartagriculture.Util.ResponseVo;
import com.example.smartagriculture.Service.login.LoginService;

import java.net.URL;

import static com.example.smartagriculture.Util.DataRequestUtil.*;
import static java.lang.Thread.sleep;

public class LoginAndRegisterLoader extends AsyncTaskLoader<String> {

    private String dataAddress;
    private String Account;
    private String Passwd;
    private String UserName;
    private boolean doWhat;
    private int DataPort;
    private LoginService service;

    /**
     * 注册使用的构造器
     * @param context 上下文
     * @param dataAddress 地址
     * @param dataPort 端口号
     * @param account 账户
     * @param passwd 用户密码
     * @param userName 用户名
     * @param doWhat 注册标记
     */
    public LoginAndRegisterLoader(@NonNull Context context, String dataAddress, int dataPort, String account, String passwd, String userName, boolean doWhat) {
        super(context);
        this.dataAddress = dataAddress;
        Account = account;
        Passwd = passwd;
        UserName = userName;
        this.doWhat = doWhat;
        DataPort = dataPort;
        service = new LoginService();
    }

    /**
     * 登录使用的构造器
     * @param context 上下文
     * @param dataAddress 地址
     * @param dataPort 端口号
     * @param account 账户
     * @param passwd 用户密码
     * @param doWhat 注册标记
     */
    public LoginAndRegisterLoader(@NonNull Context context, String dataAddress, int dataPort, String account, String passwd, boolean doWhat) {
        super(context);
        this.dataAddress = dataAddress;
        Account = account;
        Passwd = passwd;
        this.doWhat = doWhat;
        DataPort = dataPort;
        service = new LoginService();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        if (doWhat == true){
            try {
                ResponseVo responseVo = startLogin(dataAddress, DataPort);
                String reply = responseVo.getMsg();
                Log.e("当前的返回", reply);
                return reply;
            } catch (Exception e) {
                return null;
            }
        }
        else {
            try {
                ResponseVo responseVo = startRegister(dataAddress, DataPort);
                String reply = responseVo.getMsg();
                Log.e("当前的返回", reply);
                return reply;
            }catch (Exception e){
                return null;
            }
        }

    }

    /**
     * 进行网络交互
     * @param dataAddress 地址
     * @param Port  端口
     * @return  结果
     */
    private ResponseVo startLogin(String dataAddress, int Port) {
        URL url = getRequestUrl(dataAddress, Port, LOGIN_URL);
        ResponseVo responseVo = service.requestByPost(url, Account, Passwd,null);
        return responseVo;
    }

    private ResponseVo startRegister(String dataAddress, int Port){
        URL url = getRequestUrl(dataAddress, Port, REGISTER_URL);
        ResponseVo responseVo = service.requestByPost(url, Account, Passwd, UserName);
        return responseVo;
    }
}
