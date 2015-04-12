package spaceapps.nasa.com.spacehug.tools;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Nic on 4/11/2015.
 */
public class ScreenDimens {
    private int _width;
    private int _height;
    public ScreenDimens(Context c){
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        _width = metrics.widthPixels;
        _height = metrics.heightPixels;
    }

    public int getScreenWidth(){
            return _width;

    }

    public int getScreenHeight(){
            return _height;
    }
}
