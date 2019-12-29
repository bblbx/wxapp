package cn.enncloud.bean;

public class Token {
	private static final long serialVersionUID = 1L;
    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private String expiresIn;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
}
