package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 游戏设置
*/
@Table("nuby_wx_gamesettings")
public class Gamesettings {

	/**
	 * 参数唯一标识
	 */
	@Id
	@Column("set_id")
	private Integer setId;
	/**
	 * 背景图片
	 */
	@Column("news_img")
	private String newsImg;
    /**
     * 微信图文
     */
    @Column("wx_news")
    private String wxNews;
    /**
     * 微信图文消息链接
     */
    @Column("news_link")
    private String newsLink;
    /**
     * 微信图文消息标题
     */
    @Column("news_title")
    private String newsTitle;
	/**
	 * 广告图片
	 */
	@Column("ad_img")
	private String adImg;
	/**
	 * 关卡1时间
	 */
	@Column("time1")
	private Integer time1;
	/**
	 * 关卡2时间
	 */
	@Column("time2")
	private Integer time2;
	/**
	 * 关卡3时间
	 */
	@Column("time3")
	private Integer time3;
	/**
	 * 关卡4时间
	 */
	@Column("time4")
	private Integer time4;
	/**
	 * 关卡1图片
	 */
	@Column("image1")
	private String image1;
	/**
	 * 关卡2图片
	 */
	@Column("image2")
	private String image2;
	/**
	 * 关卡3图片
	 */
	@Column("image3")
	private String image3;
	/**
	 * 关卡4图片
	 */
	@Column("image4")
	private String image4;
	/**
	 * 关卡5图片
	 */
	@Column("image5")
	private String image5;
	/**
	 * 关卡6图片
	 */
	@Column("image6")
	private String image6;
	/**
	 * 关卡7图片
	 */
	@Column("image7")
	private String image7;
	/**
	 * 关卡8图片
	 */
	@Column("image8")
	private String image8;
	/**
	 * 关卡9图片
	 */
	@Column("image9")
	private String image9;
	/**
	 * 关卡10图片
	 */
	@Column("image10")
	private String image10;
	/**
	 * 关卡11图片
	 */
	@Column("image11")
	private String image11;
	/**
	 * 关卡12图片
	 */
	@Column("image12")
	private String image12;
	/**
	 * 关卡13图片
	 */
	@Column("image13")
	private String image13;
	/**
	 * 关卡14图片
	 */
	@Column("image14")
	private String image14;
	/**
	 * 关卡15图片
	 */
	@Column("image15")
	private String image15;
	/**
	 * 关卡16图片
	 */
	@Column("image16")
	private String image16;
	/**
	 * 关卡17图片
	 */
	@Column("image17")
	private String image17;
	/**
	 * 关卡18图片
	 */
	@Column("image18")
	private String image18;
	/**
	 * 关卡19图片
	 */
	@Column("image19")
	private String image19;
	/**
	 * 关卡20图片
	 */
	@Column("image20")
	private String image20;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private Date updateTime;

	public Gamesettings() {
	}

	public Gamesettings(Integer setId) {
		this.setId = setId;
	}

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getWxNews() {
        return wxNews;
    }

    public void setWxNews(String wxNews) {
        this.wxNews = wxNews;
    }

    public String getAdImg() {
		return adImg;
	}

	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}

	public Integer getTime1() {
		return time1;
	}

	public void setTime1(Integer time1) {
		this.time1 = time1;
	}

	public Integer getTime2() {
		return time2;
	}

	public void setTime2(Integer time2) {
		this.time2 = time2;
	}

	public Integer getTime3() {
		return time3;
	}

	public void setTime3(Integer time3) {
		this.time3 = time3;
	}

	public Integer getTime4() {
		return time4;
	}

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    public String getImage10() {
        return image10;
    }

    public void setImage10(String image10) {
        this.image10 = image10;
    }

    public String getImage11() {
        return image11;
    }

    public void setImage11(String image11) {
        this.image11 = image11;
    }

    public String getImage12() {
        return image12;
    }

    public void setImage12(String image12) {
        this.image12 = image12;
    }

    public String getImage13() {
        return image13;
    }

    public void setImage13(String image13) {
        this.image13 = image13;
    }

    public String getImage14() {
        return image14;
    }

    public void setImage14(String image14) {
        this.image14 = image14;
    }

    public String getImage15() {
        return image15;
    }

    public void setImage15(String image15) {
        this.image15 = image15;
    }

    public String getImage16() {
        return image16;
    }

    public void setImage16(String image16) {
        this.image16 = image16;
    }

    public String getImage17() {
        return image17;
    }

    public void setImage17(String image17) {
        this.image17 = image17;
    }

    public String getImage18() {
        return image18;
    }

    public void setImage18(String image18) {
        this.image18 = image18;
    }

    public String getImage19() {
        return image19;
    }

    public void setImage19(String image19) {
        this.image19 = image19;
    }

    public String getImage20() {
        return image20;
    }

    public void setImage20(String image20) {
        this.image20 = image20;
    }

    public void setTime4(Integer time4) {
		this.time4 = time4;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    @Override
    public String toString() {
        return "Gamesettings{" +
                "setId=" + setId +
                ", newsImg='" + newsImg + '\'' +
                ", wxNews='" + wxNews + '\'' +
                ", newsLink='" + newsLink + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", adImg='" + adImg + '\'' +
                ", time1=" + time1 +
                ", time2=" + time2 +
                ", time3=" + time3 +
                ", time4=" + time4 +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", image5='" + image5 + '\'' +
                ", image6='" + image6 + '\'' +
                ", image7='" + image7 + '\'' +
                ", image8='" + image8 + '\'' +
                ", image9='" + image9 + '\'' +
                ", image10='" + image10 + '\'' +
                ", image11='" + image11 + '\'' +
                ", image12='" + image12 + '\'' +
                ", image13='" + image13 + '\'' +
                ", image14='" + image14 + '\'' +
                ", image15='" + image15 + '\'' +
                ", image16='" + image16 + '\'' +
                ", image17='" + image17 + '\'' +
                ", image18='" + image18 + '\'' +
                ", image19='" + image19 + '\'' +
                ", image20='" + image20 + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}