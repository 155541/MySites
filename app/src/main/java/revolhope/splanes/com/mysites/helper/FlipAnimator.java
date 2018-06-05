package revolhope.splanes.com.mysites.helper;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import revolhope.splanes.com.mysites.R;

public class FlipAnimator
{

    private static AnimatorSet animatorDownOut;
    private static AnimatorSet animatorUpIn;

    private static void load(Context context)
    {
        animatorDownOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_down_out);
        animatorUpIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_up_in);
    }

    public static void flip(Context context, View frontView, View backView)
    {
        if (animatorDownOut == null || animatorUpIn == null)
        {
            load(context);
        }
        animatorDownOut.setTarget(frontView);
        animatorUpIn.setTarget(backView);
        animatorDownOut.start();
        animatorUpIn.start();
    }

    public static void flipBack(Context context, View frontView, View backView)
    {
        if (animatorDownOut == null || animatorUpIn == null)
        {
            load(context);
        }
        animatorDownOut.setTarget(backView);
        animatorUpIn.setTarget(frontView);
        animatorDownOut.start();
        animatorUpIn.start();
    }
}
