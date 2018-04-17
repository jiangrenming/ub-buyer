package youbao.shopping.ninetop.common.util;

/**
 * Created by Admin on 2017/12/1.
 *
 *      点击工具类
 */

public class ClickUtils {
    public static long time=0;
    public static boolean isContinuClick(){
        long nowtime = System.currentTimeMillis();
        if(nowtime-time>300){
            time=nowtime;
            return false;
        }
        return true;
    }
}
