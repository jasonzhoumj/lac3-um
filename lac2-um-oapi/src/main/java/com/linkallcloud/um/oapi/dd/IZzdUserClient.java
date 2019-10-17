package com.linkallcloud.um.oapi.dd;
import java.net.ConnectException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.linkallcloud.exception.BaseRuntimeException;
import com.linkallcloud.um.oapi.dd.dto.ZZDOrgBean;
import com.linkallcloud.um.oapi.dd.dto.ZZDSubOrgBean;
import com.linkallcloud.um.oapi.dd.dto.ZZDSubUserBean;
import com.linkallcloud.um.oapi.dd.dto.ZZDUserBean;

/**
 * 浙政钉用户对接相关工具类
 * 1.获取accessToken
 * 2.获取jsapi中的ticket
 * 3.根据传入的临时code获取用户的基本信息
 * 4.根据userid获取详细用户信息
 *
 */
public interface IZzdUserClient {

    /**
     * 获取accessToken
     * @param corpId
     * @param corpSecert
     * @return 与数梦服务器请求生成的accessToken
     * @throws ApiException 
     */
    String getAccessToken() throws BaseRuntimeException;

    /**
     * 获取用户信息
     * 
     * @param userId 员工在企业内的UserID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    ZZDUserBean getUserInfo(String accessToken, String userId);
    /**
     * 获取节点信息
     * 
     * @param orgNumber 节点唯一标识ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    ZZDOrgBean getOrgInfo(String accessToken, Long orgNumber);
    
    /**
     * 获取单层子节点信息
     * 
     * @param orgNumber 节点唯一标识ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<ZZDSubOrgBean> getSubOrgInfo(String accessToken, Long orgNumber);
    
    /**
     * 获取所有子节点信息
     * 
     * @param orgNumber 节点唯一标识ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<ZZDSubOrgBean> getAllSubOrgInfo(String accessToken, Long orgNumber);
    
    /**
     * 获取节点成员列表
     * 
     * @param orgNumber 节点唯一标识ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<ZZDSubUserBean> getAllMemberInfo(String accessToken, Long orgNumber);
    
    /**
     * 获取所有节点列表
     * 
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<ZZDSubOrgBean> getAllOrgInfo(String accessToken);
    
    /**
     * 获取所有根节点列表
     * 
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<ZZDSubOrgBean> getAllRootOrgInfo(String accessToken);
    
    /**
     * 根据手机号码获取用户ID
     * @param mobile    手机号
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    Long getSingleUserIdByMobile(String accessToken, String mobile);
    
    /**
     * 根据手机号批量获取用户ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     * @throws ConnectException 
     */
    //{"retCode":36009,"retMessage":"用户所在组织未授权","retData":{"15088629515":null}}
    JSONObject getBatchUserIdByMobile(String accessToken, String mobiles) throws ConnectException;
    
    /**
     * 根据dingUserId获取用户ID
     * @param dingUserId    钉钉中，该用户的ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    String getSingleUserIdByDingId(String accessToken, String dingUserId);
    
    /**
     * 根据dingUserId批量获取用户ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     * @throws ConnectException 
     */
    JSONObject getBatchUserIdByDingId(String accessToken, String dingUserIds) throws ConnectException;
    
    /**
     *人员跨地域兼职
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @param orgNumber 节点编号
     * @param userId 人员ID
     * @return 用户相关信息
     * @throws ConnectException 
     */
    boolean associate(String accessToken, Long userId);//Long orgNumber, 
    
    /**
     *人员跨地域离职
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @param orgNumber 节点编号
     * @param userId 人员ID
     * @return 用户相关信息
     * @throws ConnectException 
     */
    boolean disassociate(String accessToken, Long userId);//Long orgNumber, 
    
    /**
     * 根据用户Id获取dingUserId
     * @param userId    员工唯一标识ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    String getSingleDingUserIdByUserId(String accessToken, String userId);
    
    /**
     * 根据用户ID批量获取dingUserId
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     * @throws ConnectException 
     */
    JSONObject getBatchDingUserIdByUserId(String accessToken, String userIds) throws ConnectException;
    
    /**
     * 根据orgId获取dingOrgId
     * @param orgNumber    节点ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    String getDingOrgIdByOrgId(String accessToken, Long orgNumber);
    
    /**
     * 获取某个节点下的管理员列表
     * @param orgNumber    节点ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<Long> getAdmin(String accessToken, Long orgNumber);
    
    /**
     * 根据管理员ID获取管理员的管理范围
     * @param userId    人员ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<Long> getAdminScope(String accessToken, Long userId);
    
    /**
     * 获取某个节点的所有父节点ID列表
     * @param userId    人员ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 用户相关信息
     */
    List<Long> getOrgPath(String accessToken, Long orgNumber);
    
    /**
     * 获取人员的额外属性，包括主节点
     * @param userId    人员ID
     * @param accessToken 根据相应appid和appsecret生成的access_token
     * @return 人员额外属性
     */
    ZZDUserBean getExtend(String accessToken, Long userId);
}