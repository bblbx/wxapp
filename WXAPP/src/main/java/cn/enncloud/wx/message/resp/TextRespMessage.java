package cn.enncloud.wx.message.resp;

public class TextRespMessage extends BaseRespMessage{
    // 回复的消息内容
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
