package com.john.testproject.network;

import com.john.testproject.entity.HttpResult;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/6 0006
 * <p/>
 * Description:
 */

public class ApiException extends RuntimeException {

    private HttpResult result;

    public ApiException(HttpResult result) {
        this.result = result;
    }

    public HttpResult getResult() {
        return result;
    }

    public void setResult(HttpResult result) {
        this.result = result;
    }

}
