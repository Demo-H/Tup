package com.tupperware.huishengyi.utils.logutils;

import com.tupperware.huishengyi.config.Constant;

/**
 * Created by dhunter on 2018/3/5.
 */

public interface Parser<T>  {

    String LINE_SEPARATOR = Constant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
