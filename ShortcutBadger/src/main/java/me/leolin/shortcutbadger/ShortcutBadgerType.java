package me.leolin.shortcutbadger;

import android.content.Context;

import me.leolin.shortcutbadger.impl.AdwHomeBadger;
import me.leolin.shortcutbadger.impl.AndroidHomeBadger;
import me.leolin.shortcutbadger.impl.ApexHomeBadger;
import me.leolin.shortcutbadger.impl.HtcHomeBadger;
import me.leolin.shortcutbadger.impl.LGHomeBadger;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import me.leolin.shortcutbadger.impl.NovaHomeBadger;
import me.leolin.shortcutbadger.impl.SamsungHomeBadger;
import me.leolin.shortcutbadger.impl.SonyHomeBadger;

public enum ShortcutBadgerType {
    SONY("com.sonyericsson.home") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new SonyHomeBadger(context)};
        }
    },
    SAMSUNG("com.sec.android.app.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new SamsungHomeBadger(context)};
        }
    },
    LG("com.lge.launcher2") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new LGHomeBadger(context)};
        }
    },
    HTC("com.htc.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new HtcHomeBadger(context), new NewHtcHomeBadger(context)};
        }
    },
    ANDROID("com.android.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new AndroidHomeBadger(context)};
        }
    },
    APEX("com.anddoes.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new ApexHomeBadger(context)};
        }
    },
    ADW("org.adw.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new AdwHomeBadger(context)};
        }
    },
    ADW_EX("org.adwfreak.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new AdwHomeBadger(context)};
        }
    },
    NOVA("com.teslacoilsw.launcher") {
        @Override
        public ShortcutBadger[] createBadger(Context context) {
            return new ShortcutBadger[]{new NovaHomeBadger(context)};
        }
    };
    private String mPackageName;

    ShortcutBadgerType(String packageName) {
        mPackageName = packageName;
    }


    public String getPackageName() {
        return mPackageName;
    }

    public abstract ShortcutBadger[] createBadger(Context context);

    public static ShortcutBadgerType byPackageName(String packageName) {
        for (ShortcutBadgerType shortcutBadgerType : values()) {
            if (!shortcutBadgerType.getPackageName().equals(packageName)) continue;
            return shortcutBadgerType;
        }
        return null;
    }
}
