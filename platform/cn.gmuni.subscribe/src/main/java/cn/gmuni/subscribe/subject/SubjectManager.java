package cn.gmuni.subscribe.subject;

import java.util.HashMap;
import java.util.Map;

public class SubjectManager {

    private static Map<String, BaseSubject> subjects = new HashMap<>();

    public static void create(String flag){
        BaseSubject ba = subjects.get(flag);
        if(ba==null){
            synchronized (SubjectManager.class){
                if(subjects.get(flag)==null){
                    subjects.put(flag,new BaseSubject());
                }
            }
        }

    }

    public static BaseSubject get(String flag){
        return subjects.get(flag);
    }

    public static void remove(String flag){
        subjects.remove(flag);
    }
}
