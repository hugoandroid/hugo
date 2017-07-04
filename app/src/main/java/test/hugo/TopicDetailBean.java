package test.hugo;

import java.util.List;

/**
 * 新版话题详情
 * from 4.2.0
 */
public class TopicDetailBean{
    public TopicDetail res;
    public static class TopicDetail{
        public String id;
        public String cid;
        public String title;
        public String desc;
        public String type;
        public int send_type;
        public String img;
        public String publish_time;
        public String status;
        public String join_num;
        public String fans_num;
        public String post_num;
        public String sign_num;     //签到人数
        public int is_focus;        //是否关注
        public List<UserSign> user_sign; //用户签到信息
        public List<UserSignTop5> sign_top5; //用户签到排名前5

    }
    public static class UserSign{
        public String user_id;
        public String last_time;
        public String days;
        public int is_sign;
        public String avatar;
    }
    public static class UserSignTop5 {
        public String user_id;
        public String last_time;
        public String days;
    }
}
