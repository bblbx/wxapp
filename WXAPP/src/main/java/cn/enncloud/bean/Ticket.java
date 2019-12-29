package cn.enncloud.bean;

public class Ticket {
	private static final long serialVersionUID = 1L;
    // 接口访问凭证
    private String ticket;
    // 凭证有效期，单位：秒
    private String expiresIn;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
}
