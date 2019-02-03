package com.tupperware.biz.utils.logutils;

import com.tupperware.biz.config.Constant;

/**
 * Created by dhunter on 2018/3/5.
 */

public interface Parser<T>  {

    String LINE_SEPARATOR = Constant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
