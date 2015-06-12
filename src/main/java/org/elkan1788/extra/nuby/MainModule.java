package org.elkan1788.extra.nuby;

import org.elkan1788.extra.nuby.init.SetupMethod;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.view.FreemarkerViewMaker;
import org.nutz.web.ajax.AjaxViewMaker;

/**
 * 努比微信应用主入口
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/12
 * @verion 1.0.0
 */
@Modules(scanPackage = true)
@IocBy( type = ComboIocProvider.class,
        args = {
        "*js",
        "ioc/",
        "*anno",
        "org.elkan1788.extra.nuby",
        "*org.nutz.integration.quartz.QuartzIocLoader"})
@Encoding(input="utf8",output="utf8")
@Ok("ajax")
@Fail("http:500")
@Views({ AjaxViewMaker.class, FreemarkerViewMaker.class})
@SetupBy(SetupMethod.class)
public class MainModule {

}
