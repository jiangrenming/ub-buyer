package youbao.shopping.ninetop.bean.user;

/**
 * Created by Administrator on 2018/3/27/027.
 */

public class ShareBean {

    /**
     * statement : 活动说明:
     邀请朋友下载”彭友聚汇”, 新用户在注册的时候填写邀请码,双方都可以获得随机余额奖励,奖励金额1.8元—88.8元不等.
     1.直接将您的邀请码告诉您的朋友
     2.点击下方立即分享按钮分享到朋友圈或者微信好友
     最终解释权归彭友聚汇所有
     * invitationCode :
     * status : 200
     */

    private String statement;
    private String invitationCode;
    private String status;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
