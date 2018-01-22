package com.ninetop.update;

/**
 * Created by ${} on 2017/9/14.
 */

public class UpdateBean {

    /**
     * createUserId : 1
     * createTime : 2017-10-09 13:21:23
     * updateUserId : null
     * updateTime : null
     * delFlag : null
     * id : 3
     * versionCode : 1.1.1
     * versionInt : 2
     * downloadUrl : www.baidu.com
     * forceUpdate : 0
     * versionRemark : 测试数据2
     * 1.等你咳嗽and
     * 2.kosnc
     * 3.按承诺哦啊
     */

    private int createUserId;
    private String createTime;
    private Object updateUserId;
    private Object updateTime;
    private Object delFlag;
    private int id;
    private String versionCode;
    private int versionInt;
    private String downloadUrl;
    private int forceUpdate;
    private String versionRemark;

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionInt() {
        return versionInt;
    }

    public void setVersionInt(int versionInt) {
        this.versionInt = versionInt;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersionRemark() {
        return versionRemark;
    }

    public void setVersionRemark(String versionRemark) {
        this.versionRemark = versionRemark;
    }
}
