package org.elkan1788.extra.nuby.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
* 微信公众号设置
*/
@Table("nuby_wx_settings")
public class Settings {

	/**
	 * 配置唯一标识
	 */
	@Id
	@Column("set_id")
	private Integer setId;
    /**
     * 微信原始ID
     */
    @Column("mp_id")
    private String mpId;
	/**
	 * 微信公众号应用ID
	 */
	@Column("app_id")
	private String appId;
	/**
	 * 微信公众号密钥
	 */
	@Column("secret")
	private String secret;
	/**
	 * 微信公众号凭证
	 */
	@Column("token")
	private String token;
	/**
	 * 加密密钥
	 */
	@Column("aes_key")
	private String aesKey;
	/**
	 * 高级接口凭证
	 */
	@Column("access_token")
	private String accessToken;
	/**
	 * 凭证有效时间
	 */
	@Column("expires_in")
	private Long expiresIn;
    /**
     * JSSDK票据
     */
    @Column("jsapi_ticket")
    private String jsapiTicket;
    /**
     * JSSDK票据有效时间
     */
    @Column("jsapi_expires_in")
    private Long jsapiExpiresIn;
    /**
     * 七牛AK
     */
    @Column("qn_ak")
    private String qnAK;
    /**
     * 七牛SK
     */
    @Column("qn_sk")
    private String qnSK;
    /**
     * 七牛UK
     */
    @Column("qn_uk")
    private String qnUK;
    /**
     * 七牛UPLOAD_AK有效时间
     */
    @Column("qn_expires_in")
    private Long qnExpiresIn;
	/**
	 * 欢迎语
	 */
	@Column("welcome")
	private String welcome;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private Date updateTime;
    /**
     * 是否生效
     */
    @Column("valid")
    private String valid;

	public Settings() {
	}

	public Settings(Integer setId) {
		this.setId = setId;
	}

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

    public Long getExpiresIn() {
        if (null == expiresIn) {
            return 0l;
        }
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public Long getJsapiExpiresIn() {
        if (null == jsapiExpiresIn) {
            return 0l;
        }
        return jsapiExpiresIn;
    }

    public void setJsapiExpiresIn(Long jsapiExpiresIn) {
        this.jsapiExpiresIn = jsapiExpiresIn;
    }

    public String getQnAK() {
        return qnAK;
    }

    public void setQnAK(String qnAK) {
        this.qnAK = qnAK;
    }

    public String getQnSK() {
        return qnSK;
    }

    public void setQnSK(String qnSK) {
        this.qnSK = qnSK;
    }

    public String getQnUK() {
        return qnUK;
    }

    public void setQnUK(String qnUK) {
        this.qnUK = qnUK;
    }

    public Long getQnExpiresIn() {
        if (null == qnExpiresIn) {
            return 0l;
        }
        return qnExpiresIn;
    }

    public void setQnExpiresIn(Long qnExpiresIn) {
        this.qnExpiresIn = qnExpiresIn;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "setId=" + setId +
                ", mpId='" + mpId + '\'' +
                ", appId='" + appId + '\'' +
                ", secret='" + secret + '\'' +
                ", token='" + token + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", jsapiTicket='" + jsapiTicket + '\'' +
                ", jsapiExpiresIn=" + jsapiExpiresIn +
                ", qnAK='" + qnAK + '\'' +
                ", qnSK='" + qnSK + '\'' +
                ", qnUK='" + qnUK + '\'' +
                ", qnExpiresIn=" + qnExpiresIn +
                ", welcome='" + welcome + '\'' +
                ", updateTime=" + updateTime +
                ", valid='" + valid + '\'' +
                '}';
    }
}