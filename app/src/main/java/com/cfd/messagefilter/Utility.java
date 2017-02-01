package com.cfd.messagefilter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by rahul on 1/2/17.
 */
public class Utility {
    private static final String TAG = Utility.class.getSimpleName();

    public static List<SmsData> parseList(Map<String, List<SmsData> > convList ){

        Object[] list = convList.values().toArray();
        List<SmsData> finalList = new ArrayList<SmsData>();
        for(int i=0;i<list.length;i++){
            List<SmsData> sd = (List<SmsData>) list[i];
            finalList.add(sd.get(0));
        }
        return finalList;
    }
}
