package com.green.util;

public class HTMLUtil {
	public static String forGotPass(String username, String code, String anyMessages) {
		StringBuilder str = new StringBuilder();
		return str.append(
				"<html><head><meta charset='UTF-8'><style>h1,h3 {text-align: center;color: orange;}h3 {color: #0055ff;}button {border-radius: .5rem;background: linear-gradient(135deg, green, yellow)")
				.append(";color: white;font-weight: bolder;border: 1px solid grey;padding: .5rem 1rem;font-size: 1.2em;}</style></head><body><div style='margin: auto; width: 80%;'><h1>Quên mật khẩu người dùng")
				.append("</h1><hr><h3>Green Store nhận được yêu cầu thay đổi mật khẩu tài khoản ").append(username)
				.append("</h3><p>").append(anyMessages == null ? "" : anyMessages)
				.append("</p><form action='http://localhost:8080/security/forget-pass' method='GET'><input type='hidden' value='")
				.append(code).append("' name='code'><input type='hidden' value='").append(username)
				.append("' name='username'><div style='text-align: end;'><button type='submit'>Thay đổi mật khẩu</button></div></form></div></body></html>").toString();
	}
}