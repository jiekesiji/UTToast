package com.utsoft.uttoastlibary;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cj on 2016/12/15.
 * Function:
 * Desc:
 */

public class UTToast {
    public static final int LENGTH_ALWAYS = 0;
    public static final int LENGTH_SHORT = 2;

    //图片位置
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BUTTOM = 3;

    private Toast toast;
    private Context mContext;
    private int mDuration = LENGTH_SHORT;
    private int animations = -1;
    private boolean isShow = false;

    private Object mTN;
    private Method show;
    private Method hide;
    private WindowManager.LayoutParams params;
    private static FrameLayout mView;

    private String text;

    private Drawable drawable;
    private GradientDrawable gd;

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static UTToast utToast;
    private static TextView tv;

    private int gravity;//屏幕中的位置
    private int xOffset;//x轴的偏距
    private int yOffset;//y轴的偏距

    //textview的内边距
    private int paddingLeft = 8;
    private int paddingTop = 8;
    private int paddingRight = 8;
    private int paddingButtom = 8;

    public UTToast(final Context context,final CharSequence text) {
        this.mContext = context;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                mView = new FrameLayout(context);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                tv.setGravity(Gravity.CENTER);
                tv.setIncludeFontPadding(false);
                tv.setTextSize(textSize);
                utToast.toast = toast;
                mView.addView(tv, params);
            }
        });
    }

    public static UTToast makeUTToast(final Context context, final CharSequence text, final int duration) {
        if (tv == null){
            tv = new TextView(context);
        }

        if (!(context instanceof Application)) {
            throw new IllegalAccessError("Please use ApllicationContext");
        }

        //处理单例
        if (utToast == null) {
            synchronized (UTToast.class) {
                if (utToast == null) {
                    utToast = new UTToast(context,text);
                }
            }
        }
        utToast.mDuration = duration;
        utToast.text = (String) text;
        return utToast;
    }


    /**
     * 设置背景颜色
     *
     * @return
     */
    public UTToast setBackgroundColor(final int color) {
        modifiUI(new Runnable() {
            @Override
            public void run() {
                gd.setColor(color);
            }
        });
        return utToast;
    }

    public void show() {
        setUTToast();
        if (isShow) return;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                initTN();
                setGravity(gravity, xOffset, yOffset);
                try {
                    show.invoke(mTN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isShow = true;
                //判断duration，如果大于#LENGTH_ALWAYS 则设置消失时间
                if (mDuration > LENGTH_ALWAYS) {
                    handler.postDelayed(hideRunnable, mDuration * 1000);
                }
            }
        });
    }

    /**
     * 初始化Toast
     */
    private void initToastBackGround() {
        modifiUI(new Runnable() {
            @Override
            public void run() {
                if (gd == null) {
                    gd = new GradientDrawable();
                    gd.setColor(Color.parseColor("#96000000"));
                    gd.setCornerRadius(roundRadius);
                }
                mView.setBackgroundDrawable(gd);
            }
        });
    }

    private int roundRadius = 10;

    /**
     * 设置背景的圆角
     *
     * @param roundRadius
     * @return
     */
    public UTToast setBackgroundRadius(final int roundRadius) {
        modifiUI(new Runnable() {
            @Override
            public void run() {
                gd.setCornerRadius(roundRadius);
            }
        });
        return utToast;
    }

    private static void modifiUI(Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * 设置背景的透明度
     *
     * @param alpha
     * @return
     */
    public UTToast setBackgroundAlpha(final int alpha) {
        if (alpha > 255 || alpha < 0) return utToast;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                gd.setAlpha(alpha);
            }
        });
        return utToast;
    }

    private void setUTToast() {
        initToastBackGround();
        modifiUI(new Runnable() {
            @Override
            public void run() {
                tv.setText(text);
                tv.setTextColor(textColor);//默认颜色是白色
                initToastPadding();
                toast.setView(mView);
            }
        });
    }

    private int textColor = Color.WHITE;
    private float textSize = 18;


    /**
     * 设置需要显示的图片
     *
     * @param resId
     * @return
     */
    public UTToast setTextImage(final int resId) {
        drawable = mContext.getResources().getDrawable(resId);
        int boundsSize = (int) tv.getTextSize();
        drawable.setBounds(0, 0, boundsSize, boundsSize);
        modifiUI(new Runnable() {
            @Override
            public void run() {
                tv.setCompoundDrawables(drawable, null, null, null);
                tv.setCompoundDrawablePadding(imagePad);
            }
        });
        return utToast;
    }


    /**
     * 设置imageview的大小
     * @param width
     * @param high
     * @return
     */
    public UTToast setTextImageSize(final int width, final int high) {
        if (drawable == null) {
            throw new IllegalAccessError("Please use the method of setTextImage first");
        }
        drawable.setBounds(0, 0, width, high);
        setTextImageLocation(LEFT);
        tv.setGravity(Gravity.CENTER);
        return utToast;
    }

    /**
     * 设置图片和文字之间的距离
     * @param pad
     * @return
     */
    public UTToast setImagePadding(final int pad) {
        imagePad = pad;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                tv.setCompoundDrawablePadding(pad);
            }
        });
        return utToast;
    }

    /**
     * 如果要设置图片的大小，先设置图片的大小再调用该方法
     * @param imageLocation 图片位置
     * @return
     */
    public UTToast setTextImageLocation(final int imageLocation) {

       modifiUI(new Runnable() {
           @Override
           public void run() {
               if (imageLocation == LEFT) {
                   tv.setCompoundDrawables(drawable, null, null, null);
               } else if (imageLocation == TOP) {
                   tv.setCompoundDrawables(null, drawable, null, null);
               } else if (imageLocation == RIGHT) {
                   tv.setCompoundDrawables(null, null, drawable, null);
               } else if (imageLocation == BUTTOM) {
                   tv.setCompoundDrawables(null, null, null, drawable);
               }
           }
       });
        return utToast;
    }

    private int imagePad = 10;

    /**
     * 设置toast文字的颜色
     * @param color
     * @return
     */
    public UTToast setToastTextColor(final int color) {
        textColor = color;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                tv.setTextColor(color);
            }
        });
        return utToast;
    }

    /**
     * 设置toast的文字大小
     * @param size
     * @return
     */
    public UTToast setToastTextSize(final float size) {
        textSize = size;
        modifiUI(new Runnable() {
            @Override
            public void run() {
                tv.setTextSize(size);
            }
        });
        return utToast;
    }

    private void initToastPadding() {
        tv.setPadding(paddingLeft, paddingTop, paddingRight, paddingButtom);
    }

    /**
     * 设置textview的padding值
     * @param left
     * @param top
     * @param right
     * @param buttom
     * @return
     */
    public UTToast setTextPadding(int left, int top, int right, int buttom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingButtom = buttom;
        return utToast;
    }


    private void setGravity(final int gravity, final int xOffset, final int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }


    private void initTN() {
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");

            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.windowAnimations = animations;
            /**调用tn.show()之前一定要先设置mNextView*/
            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());

//            mWM = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置Toast的位置
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @return
     */
    public UTToast setToastGravity(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        return utToast;
    }


    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * 隐藏Toast逻辑
     */
    public void hide() {
        if (!isShow) return;
        try {
            hide.invoke(mTN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = false;
    }

}
