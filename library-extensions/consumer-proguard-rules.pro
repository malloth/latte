-dontwarn kotlin.**

-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-keep class org.jetbrains.annotations.** { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclassmembers class ** {
  @org.jetbrains.annotations.ReadOnly public *;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}