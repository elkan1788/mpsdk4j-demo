package org.elkan1788.extra.nuby.service;

import org.elkan1788.extra.nuby.bean.Gamesettings;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 拼图游戏业务逻辑
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/20
 * @version 1.0.0
 */
@IocBean(name = "gameSetService",fields = {"dao"})
public class GameSettingService extends BaseService<Gamesettings> {

    private static final Log log = Logs.get();

    public Gamesettings get() {
        Gamesettings gs = get("valid", 1);
        if (null != gs) {
            log.info("Random game image start...");
            Set<Integer> nums = new HashSet<>();
            while(true) {
                nums.add(new Random().nextInt(20)+1);
                if (nums.size() == 4){
                    break;
                }
            }
            Integer[] indexs = new Integer[nums.size()];
            nums.toArray(indexs);
            log.infof("Random image numbers: %d, %d, %d, %d", indexs);
            gs.setImage1(getImgSrc(indexs[0], gs));
            gs.setImage2(getImgSrc(indexs[1], gs));
            gs.setImage3(getImgSrc(indexs[2], gs));
            gs.setImage4(getImgSrc(indexs[3], gs));
        }
        return gs;
    }

    protected String getImgSrc(Integer index, Gamesettings gs) {
        String src = "";
        switch (index) {
            case 1:
                src = gs.getImage1();
                break;
            case 2:
                src = gs.getImage2();
                break;
            case 3:
                src = gs.getImage3();
                break;
            case 4:
                src = gs.getImage4();
                break;
            case 5:
                src = gs.getImage5();
                break;
            case 6:
                src = gs.getImage6();
                break;
            case 7:
                src = gs.getImage7();
                break;
            case 8:
                src = gs.getImage8();
                break;
            case 9:
                src = gs.getImage9();
                break;
            case 10:
                src = gs.getImage10();
                break;
            case 11:
                src = gs.getImage11();
                break;
            case 12:
                src = gs.getImage12();
                break;
            case 13:
                src = gs.getImage13();
                break;
            case 14:
                src = gs.getImage14();
                break;
            case 15:
                src = gs.getImage15();
                break;
            case 16:
                src = gs.getImage16();
                break;
            case 17:
                src = gs.getImage17();
                break;
            case 18:
                src = gs.getImage18();
                break;
            case 19:
                src = gs.getImage19();
                break;
            default:
                src = gs.getImage20();
                break;
        }
        return src;
    }

    public Gamesettings getForBack() {
        Gamesettings gs = get("valid", 1);
        return gs;
    }

    public int updateGameset(Gamesettings gs) {
        Gamesettings old_gs = get();
        if (null == old_gs) {
            gs = dao().insert(gs);
            if(gs.getSetId().equals(0)) {
                log.error("新增游戏配置信息失败!!!");
            }
            return 1;
        }
        int rows = dao().updateIgnoreNull(gs);
        if (rows < 1) {
            log.error("更新游戏配置信息失败!!!");
        }
        return rows;
    }
}
