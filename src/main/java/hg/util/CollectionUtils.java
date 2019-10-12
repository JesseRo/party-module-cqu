package hg.util;

import java.util.List;

public class CollectionUtils {
    public static boolean isEquals(List<?> list1, List<?> list2){
        if(null != list1 && null != list2){
            return list1.containsAll(list2) && list2.containsAll(list1);
        }
        return true;
    }
}
