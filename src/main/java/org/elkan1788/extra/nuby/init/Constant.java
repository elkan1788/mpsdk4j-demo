package org.elkan1788.extra.nuby.init;

/**
 * 系统常量定义
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/19
 * @version 1.0.0
 */
public class Constant {

    // 操作代码
    public static final Integer REPEAT_APPLY = -6;
    public static final Integer DISACCORD = -5;
    public static final Integer NOT_EXIST = -4;
    public static final Integer PER_ERR = -3; //Persistence
    public static final Integer SQL_ERR = -2;
    public static final Integer SYS_ERR = -1;
    public static final Integer SUCCESS = 0;
    public static final Integer ILLEGAL_CONTROL = 2;


    // 操作消息
    public static final String DEF_MSG = "操作成功";

    // 微信自定菜单键值
    public static final String PUZZLE_GAME_MENU = "puzzle";
    public static final String BABY_EXPR_SHARE = "baby_expr_share";
    public static final String BABY_SPEC = "baby_spec";

    // Http Session相关
    public static final String CUR_USER = "current_user";

    // 微信提示信息
    public static final String DEF_WELCOME = "[玫瑰]感谢您的关注[微笑]";
    public static final String PRIZE_PREFIX = "恭喜你中奖了！奖品为《%s》，快去查看吧！";
    public static final String NO_PRIZE_TIP = "抱歉，目前奖品已经发送完了，请继续关注努比的其它活动！";

}
