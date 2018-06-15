package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.VideoBean;

/**
 * Created by dhunter on 2018/3/6.
 */

public class VideoContract {
    public interface View {
        void setVideoData(VideoBean video);
        void setMoreVideoData(VideoBean video);
    }

    public interface Presenter {
        void getVideoData();
        void getMoreVideoData();
    }
}
