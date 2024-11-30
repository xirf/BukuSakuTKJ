# Preserve annotations
-keepattributes *Annotation*

# Preserve class members for serialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Preserve classes with specific annotations
-keep @interface com.google.gson.annotations.SerializedName

# Preserve all public classes, methods, and fields
-keep public class * {
    public protected *;
}

# Preserve all classes that extend android.app.Activity
-keep class * extends android.app.Activity {
    public protected *;
}

# Preserve all classes that extend android.app.Service
-keep class * extends android.app.Service {
    public protected *;
}

# Preserve all classes that extend android.content.BroadcastReceiver
-keep class * extends android.content.BroadcastReceiver {
    public protected *;
}

# Preserve all classes that extend android.content.ContentProvider
-keep class * extends android.content.ContentProvider {
    public protected *;
}

# Preserve all classes that extend android.app.Application
-keep class * extends android.app.Application {
    public protected *;
}

# Preserve all classes that implement android.os.Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Preserve all Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

# Preserve all View binding classes
-keep class **.databinding.* {
    *;
}

# Preserve all Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.** { *; }

# Preserve Retrofit interfaces
-keep interface * {
    @retrofit2.http.* <methods>;
}

# Preserve Retrofit classes
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Preserve Room database classes
-keep class androidx.room.RoomDatabase { *; }

# Preserve Room DAO classes
-keep class * extends androidx.room.RoomDatabase {
    public static final **_Impl INSTANCE;
}

# Preserve Room entities
-keep @androidx.room.Entity class * { *; }

# Preserve Room DAO methods
-keepclassmembers class * {
    @androidx.room.Dao <methods>;
}

# Preserve Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }

# Preserve Kotlin serialization
-keep class kotlinx.serialization.** { *; }

# Preserve Jetpack Compose classes
-keep class androidx.compose.** { *; }
-keep class androidx.activity.ComponentActivity { *; }
-keep class androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.LiveData { *; }
-keep class androidx.lifecycle.MutableLiveData { *; }