package com.xfc.common.response;

/**
 * @author jiao xn
 * @date 2022/8/27
 */
public enum ResponseStatusCodeEnum implements IResponseStatusCode {
    //服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
    SUCCESS(200, "OK"),
    //用户新建或修改数据成功。
    UPDATE_RETURN_201(201, "CREATED"),
    //表示一个请求已经进入后台排队（异步任务）
    ALL_RETURN_202(202, "Accepted"),
    //用户删除数据成功。
    DELETE_RETURN_204(204, "NO CONTENT"),
    //用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
    UPDATE_RETURN_400(400, "INVALID REQUEST"),
    //用户发出的请求参数有误,服务器没有找到对应资源 这是新加的
    WRONG_PARAMETER_NOT_FIND_400(400,"请求参数有误，资源不存在"),
    //401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
    ALL_RETURN_401(401, "Unauthorized"),
    TOKEN_PAST(1401, "身份过期，请求重新登录！"),
    //403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
    ALL_RETURN_403(403, "Forbidden"),
    //404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
    ALL_RETURN_404(404, "NOT FOUND"),
    //406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
    GET_RETURN_406(406, "Not Acceptable"),
    //410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
    GET_RETURN_410(410, "Gone"),
    //422 Unprocessable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
    UPDATE_RETURN_422(422, "Unprocessable entity"),
    //500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。*/
    GET_RETURN_500(500, "INTERNAL SERVER ERROR"),
    CONFLICT_RETURN_409(409,"CONFLICT");

    private final Integer code;
    private final String message;

    ResponseStatusCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
