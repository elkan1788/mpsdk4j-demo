// 微信公众信息配置
var ioc = {

    mp: {
        type : "org.elkan1788.osc.weixin.mp.vo.MPAct"
    },

    wxapi: {
        type : "org.elkan1788.osc.weixin.mp.core.WxApiImpl",
        args : [{
            refer: "mp"
        }]
    }
}