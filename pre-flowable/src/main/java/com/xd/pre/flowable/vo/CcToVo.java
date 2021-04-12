package com.xd.pre.flowable.vo;

import lombok.Data;

/**
 * @author
 */
@Data
public class CcToVo {
    private String userId;
    private String userName;

    @Override
    public String toString(){
        return String.format("%s[%s]",this.userName,this.userId);
    }
}
