# Preserve annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

# Kotlin-specific rules
-keep class kotlin.Metadata { *; }
-keepclassmembers class ** {
    @kotlin.Metadata *;
}
-keepnames class kotlin.coroutines.jvm.internal.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Room (Entity, DAO, Database)
-keep @androidx.room.Dao class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep class androidx.room.RoomDatabase { *; }
-keep class androidx.sqlite.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.http.** { *; }
-keepattributes Exceptions
-keepattributes *Annotation*

# Gson
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class okio.** { *; }
-dontwarn okio.**

# Hilt / Dagger
-keep class dagger.hilt.** { *; }
-keep @dagger.Module class * { *; }
-keep @dagger.Provides class * { *; }
-keep @dagger.hilt.InstallIn class * { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.**

# Compose-specific rules
-keep class androidx.compose.** { *; }
-keep class androidx.activity.compose.** { *; }
-keep class androidx.lifecycle.viewmodel.compose.** { *; }
-dontwarn androidx.compose.**

# Coil (Image Loading)
-keep class coil.** { *; }
-dontwarn coil.**

# Serialization
-keep class kotlinx.serialization.** { *; }
-keepattributes *Annotation*

# WorkManager
-keep class androidx.work.** { *; }
-dontwarn androidx.work.**

# IPAddress Library
-keep class inet.ipaddr.** { *; }
-dontwarn inet.ipaddr.**

# Keep generic signatures (required for Retrofit and Room)
-keepattributes Signature

# Preserve all classes and interfaces in specific packages
-keep class id.my.andka.data.** { *; }
-keep interface id.my.andka.data.** { *; }
-keep class id.my.andka.domain.** { *; }

# Continuation for suspend functions
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Logging Interceptor
-keep class okhttp3.logging.** { *; }

# Prevent obfuscation for build-config and R files
-keep class **.R$* { *; }
-keepclassmembers class **.R$* { *; }
-keepclassmembers class **.BuildConfig { *; }
