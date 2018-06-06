package revolhope.splanes.com.mysites.helper;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import revolhope.splanes.com.mysites.R;

public class FlipAnimator
{
    private static final int distance = 8000;
    private static float scale;
    private static AnimatorSet animatorDownOut;
    private static AnimatorSet animatorUpIn;
    private static AnimatorSet animatorDownIn;
    private static AnimatorSet animatorUpOut;

    private static void load(Context context)
    {
        animatorDownOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_down_out);
        animatorUpIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_up_in);
        animatorDownIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_down_in);
        animatorUpOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_up_out);
        scale = context.getResources().getDisplayMetrics().density * distance;
    }

    public static void flip(Context context, View frontView, View backView)
    {
        if (animatorDownOut == null || animatorUpIn == null)
        {
            load(context);
        }

        frontView.setCameraDistance(scale);
        backView.setCameraDistance(scale);

        animatorDownOut.setTarget(frontView);
        animatorUpIn.setTarget(backView);
        animatorDownOut.start();
        animatorUpIn.start();
        backView.setVisibility(View.VISIBLE);
    }

    public static void flipBack(Context context, View frontView, View backView)
    {
        if (animatorDownIn == null || animatorUpOut == null)
        {
            load(context);
        }

        frontView.setCameraDistance(scale);
        backView.setCameraDistance(scale);

        animatorDownIn.setTarget(frontView);
        animatorUpOut.setTarget(backView);
        animatorDownOut.start();
        animatorUpIn.start();
        frontView.setVisibility(View.VISIBLE);
    }
}
