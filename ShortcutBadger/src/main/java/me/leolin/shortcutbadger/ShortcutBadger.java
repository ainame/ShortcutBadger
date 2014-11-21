package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午5:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShortcutBadger {
    private static final String MESSAGE_NOT_SUPPORT_BADGE_COUNT = "ShortBadger is currently not support the badgeCount \"%d\"";
    private static final String MESSAGE_NOT_SUPPORT_THIS_HOME = "ShortcutBadger is currently not support the home launcher package \"%s\"";

    private static final int MIN_BADGE_COUNT = 0;
    private static final int MAX_BADGE_COUNT = 99;

    private ShortcutBadger() {
    }

    protected Context mContext;

    protected ShortcutBadger(Context context) {
        this.mContext = context;
    }

    protected abstract void executeBadge(int badgeCount) throws ShortcutBadgeException;

    public static void setBadge(Context context, int badgeCount) throws ShortcutBadgeException {
        //badgeCount should between 0 to 99
        if (badgeCount < MIN_BADGE_COUNT || badgeCount > MAX_BADGE_COUNT) {
            String exceptionMessage = String.format(MESSAGE_NOT_SUPPORT_BADGE_COUNT, badgeCount);
            throw new ShortcutBadgeException(exceptionMessage);
        }

        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        //different home launcher packages use different way adding badges
        ShortcutBadgerType badgerType = ShortcutBadgerType.byPackageName(currentHomePackage);

        //not support this home launcher package
        if (badgerType == null) {
            String exceptionMessage = String.format(MESSAGE_NOT_SUPPORT_THIS_HOME, currentHomePackage);
            throw new ShortcutBadgeException(exceptionMessage);
        }

        for (ShortcutBadger shortcutBadger : badgerType.createBadger(context)) {
            try {
                shortcutBadger.executeBadge(badgeCount);
            } catch (Throwable e) {
                throw new ShortcutBadgeException("Unable to execute badge:" + e.getMessage());
            }
        }
    }

    protected String getEntryActivityName() {
        ComponentName componentName = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        return componentName.getClassName();
    }

    protected String getContextPackageName() {
        return mContext.getPackageName();
    }
}
