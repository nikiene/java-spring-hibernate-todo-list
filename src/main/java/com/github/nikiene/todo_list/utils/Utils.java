package com.github.nikiene.todo_list.utils;

import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }

    public static String[] getNullProperties(Object object) {
        final BeanWrapper wrapper = new BeanWrapperImpl(object);

        return Arrays.stream(wrapper.getPropertyDescriptors())
                .filter(pd -> wrapper.getPropertyValue(pd.getName()) == null)
                .map(pd -> pd.getName())
                .toArray(String[]::new);
    }
}
