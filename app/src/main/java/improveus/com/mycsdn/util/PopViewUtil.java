/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package improveus.com.mycsdn.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 描述：提示框
 *http://blog.csdn.net/chang_1134/article/details/51367770
 * @version v1.0
 * @date：2016-8-30 下午2:02:16
 */
public class PopViewUtil {
	
	
	 private PopupWindow window;
	    //窗口在x轴偏移量
	    private int xOff = 0;
	    //窗口在y轴的偏移量
	    private int yOff = 0;

	    public PopViewUtil(Context context, View localView) {

	        window = new PopupWindow(context);
	        //ViewGroup.LayoutParams.WRAP_CONTENT，自动包裹所有的内容
	        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
	        window.setFocusable(true);
	        //点击 back 键的时候，窗口会自动消失
	        window.setBackgroundDrawable(new BitmapDrawable());
	        
	        localView.setTag(window);
	        //设置显示的视图
	        window.setContentView(localView);
	    }

	 

	    public void dismiss() {
	        window.dismiss();
	    }

	    /**
	     * @param xOff x轴（左右）偏移
	     * @param yOff y轴（上下）偏移
	     */
	    public void setOff(int xOff, int yOff) {
	        this.xOff = xOff;
	        this.yOff = yOff;
	    }

	    /**
	     * @param paramView 点击的按钮
	     */
	    public void show(View paramView, int count) {
	        //该count 是手动调整窗口的宽度
	        window.setWidth(paramView.getWidth() * count);
	        //设置窗口显示位置, 后面两个0 是表示偏移量，可以自由设置
	        window.showAsDropDown(paramView, xOff, yOff);
	        //更新窗口状态
	        window.update();
	    }
	    /**
	     * @param paramView 点击的按钮
	     */
	    public void showCreate(View paramView, int count) {
	        //该count 是手动调整窗口的宽度
	        window.setWidth(paramView.getWidth() * count);
	        //设置窗口显示位置, 后面两个0 是表示偏移量，可以自由设置
	        window.showAsDropDown(paramView, xOff, 5);
	        //更新窗口状态
	        window.update();
	    }
	
}
