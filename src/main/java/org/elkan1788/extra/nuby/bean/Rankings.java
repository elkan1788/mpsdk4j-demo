package org.elkan1788.extra.nuby.bean;

import org.nutz.repo.Base64;

/**
 * 游戏排行榜
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/5/4
 */
public class Rankings {

    private String openId;
    private String nickName;
    private String headImg;
    private Integer passTime;
    private String ptFormat;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        byte[] decode = Base64.decodeFast(nickName.getBytes());
        this.nickName = new String(decode);
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    public String getPtFormat() {
        if (passTime > 60) {
            int min = passTime / 60;
            int sec = passTime % 60;
            ptFormat = min + "'" + sec + "''";
        } else {
            ptFormat = passTime + "''";
        }
        return ptFormat;
    }

    public void setPtFormat(String ptFormat) {
        this.ptFormat = ptFormat;
    }

    @Override
    public String toString() {
        return "Rankings{" +
                "openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImg='" + headImg + '\'' +
                ", passTime=" + passTime +
                ", ptFormat=" + ptFormat +
                '}';
    }
}
