# [Nutz](http://nutzam.com/)+easyui+[mpsdk4j](http://git.oschina.net/lisenhui/mpsdk4j/)+[七牛云服务](http://www.qiniu.com)微信应用开发示例项目

* 1. 实现微信基本的消息互动功能，菜单响应，用户信息同步等
* 2. 最新的JSSDK功能，如分享，图片上传， 网页授权， 一键关注等
* 3. 后台管理界面用Easyui搭建，有点小粗糙别见怪
* 4. 增加了个小小拼图游戏（原型非本人实现）， 只是对其它改造，实现屏幕响应式， 支持手机到平板
* 5. 其它的自行发现吧

注意：

下载好代码后，在`org.elkan1788.extra.nuby.init.InitDB`类中，把自己的微信信息，七牛存储信息填入进去， 其它信息进入后台编辑即可。

```java 

	// settings
	Settings set = new Settings();
	set.setMpId("your weixin mp origin id");
	set.setAppId("your weixin appid");
	set.setSecret("your weixin appsecret");
	set.setWelcome("new user subscribe message");
	set.setQnUK("your qiniu uk");
	set.setQnAK("your qiniu ak");
	set.setQnSK("your qiniu sk");
	dao.insert(set);
		
```


