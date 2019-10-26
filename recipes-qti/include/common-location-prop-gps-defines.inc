# Common defines applicable for all location-prop BB files

# enabling logcat for target which supports logd in both internal and external builds.
CFLAGS_append_apq8096 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_apq8096 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_apq8096 = " -llog"

CFLAGS_append_apq8098 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_apq8098 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_apq8098 = " -llog"

CFLAGS_append_sda845 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_sda845 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_sda845 = " -llog"

CFLAGS_append_qcs605 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_qcs605 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_qcs605 = " -llog"

CFLAGS_append_apq8017 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_apq8017 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_apq8017 = " -llog"

CFLAGS_append_apq8009 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_apq8009 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_apq8009 = " -llog"

CFLAGS_append_apq8053 = " -DUSE_ANDROID_LOGGING"
CPPFLAGS_append_apq8053 = " -DUSE_ANDROID_LOGGING"
LDFLAGS_append_apq8053 = " -llog"
