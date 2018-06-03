package revolhope.splanes.com.mysites.helper;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {


    private int mCurrentState = Constants.STATE_IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != Constants.STATE_EXPANDED) {
                onStateChanged(appBarLayout, Constants.STATE_EXPANDED);
            }
            mCurrentState = Constants.STATE_EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != Constants.STATE_COLLAPSED) {
                onStateChanged(appBarLayout, Constants.STATE_COLLAPSED);
            }
            mCurrentState = Constants.STATE_COLLAPSED;
        } else {
            if (mCurrentState != Constants.STATE_IDLE) {
                onStateChanged(appBarLayout, Constants.STATE_IDLE);
            }
            mCurrentState = Constants.STATE_IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, int state);
}
