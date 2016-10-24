package com.zk.my.demo01.info;

import java.util.List;

/**
 * Created by My on 2016/10/19.
 */
public class DouyuBean {


    /**
     * error : 0
     * data : [{"room_id":"71017","room_src":"https://rpic.douyucdn.cn/a1610/19/20/71017_161019205621.jpg","vertical_src":"https://rpic.douyucdn.cn/a1610/19/20/71017_161019205621.jpg","isVertical":0,"cate_id":"1","room_name":"冯提莫 ：我在oppo r9s发布会","show_status":"1","subject":"","show_time":"1476877689","owner_uid":"2517434","specific_catalog":"fengtimo","specific_status":"1","vod_quality":"0","nickname":"冯提莫","online":344468,"game_name":"英雄联盟","child_id":"168","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/face/201609/25/1e847044602d002b3c80e0562b2b393d_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/face/201609/25/1e847044602d002b3c80e0562b2b393d_small.jpg","jumpUrl":"","ranktype":0},{"room_id":"265438","room_src":"https://rpic.douyucdn.cn/a1610/19/20/265438_161019205550.jpg","vertical_src":"https://rpic.douyucdn.cn/a1610/19/20/265438_161019205550.jpg","isVertical":0,"cate_id":"2","room_name":"飞儿 这个主播为什么要打炉石","show_status":"1","subject":"","show_time":"1476879265","owner_uid":"1974429","specific_catalog":"liufeier","specific_status":"1","vod_quality":"0","nickname":"刘飞儿faye","online":170086,"game_name":"炉石传说","child_id":"155","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/001/97/44/29_avatar_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/001/97/44/29_avatar_small.jpg","jumpUrl":"","ranktype":0},{"room_id":"216906","room_src":"https://rpic.douyucdn.cn/a1610/19/20/216906_161019205634.jpg","vertical_src":"https://rpic.douyucdn.cn/a1610/19/20/216906_161019205634.jpg","isVertical":0,"cate_id":"19","room_name":"逆风笑直播间","show_status":"1","subject":"","show_time":"1476876274","owner_uid":"11386731","specific_catalog":"nifengxiao","specific_status":"1","vod_quality":"0","nickname":"逆风笑的直播间","online":95044,"game_name":"主机游戏","child_id":"166","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/011/38/67/31_avatar_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/011/38/67/31_avatar_small.jpg","jumpUrl":"","ranktype":0},{"room_id":"28101","room_src":"https://rpic.douyucdn.cn/a1610/19/20/28101_161019205701.jpg","vertical_src":"https://rpic.douyucdn.cn/a1610/19/20/28101_161019205701.jpg","isVertical":0,"cate_id":"40","room_name":"翅：强化罐子天空搞起~","show_status":"1","subject":"","show_time":"1476871323","owner_uid":"85436","specific_catalog":"chibang","specific_status":"1","vod_quality":"0","nickname":"云彩上的翅膀","online":69295,"game_name":"DNF","child_id":"60","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/000/08/54/36_avatar_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/000/08/54/36_avatar_small.jpg","jumpUrl":"","ranktype":0}]
     */

    private int error;
    /**
     * room_id : 71017
     * room_src : https://rpic.douyucdn.cn/a1610/19/20/71017_161019205621.jpg
     * vertical_src : https://rpic.douyucdn.cn/a1610/19/20/71017_161019205621.jpg
     * isVertical : 0
     * cate_id : 1
     * room_name : 冯提莫 ：我在oppo r9s发布会
     * show_status : 1
     * subject :
     * show_time : 1476877689
     * owner_uid : 2517434
     * specific_catalog : fengtimo
     * specific_status : 1
     * vod_quality : 0
     * nickname : 冯提莫
     * online : 344468
     * game_name : 英雄联盟
     * child_id : 168
     * avatar_mid : https://apic.douyucdn.cn/upload/avatar/face/201609/25/1e847044602d002b3c80e0562b2b393d_middle.jpg
     * avatar_small : https://apic.douyucdn.cn/upload/avatar/face/201609/25/1e847044602d002b3c80e0562b2b393d_small.jpg
     * jumpUrl :
     * ranktype : 0
     */

    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


}
