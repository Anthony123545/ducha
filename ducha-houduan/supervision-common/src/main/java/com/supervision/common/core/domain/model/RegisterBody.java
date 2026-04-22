package com.supervision.common.core.domain.model;

/**
 * 用户注册对象
 *
 * @author supervision
 */
public class RegisterBody extends LoginBody {
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    private String idcard;
}
