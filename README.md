# Latte

Latte is a lightweight and easy to use UI testing framework for 
Android utilizing Kotlin DSL. 

#### Features

- Small footprint
- Uses Kotlin DSL
- Uses standard Android API for matching, interactions and verifications
- Matching, interactions and verifications are performed on a given type of View
- Gives a fine grained control over matching process
- Provides a separate artifact with helper interactions and verifications

## Requirements

- Minimum Android SDK: 21
- Compile Android SDK: 28 or later

## Setup

Gradle:

```gradle
repositories {
    jcenter()
}

dependencies {
    androidTestImplementation "com.test.latte:core:LATEST_VERSION"
    androidTestImplementation "com.test.latte:extensions:LATEST_VERSION"
}
```

Maven:

```xml
<dependency>
    <groupId>com.test.latte</groupId>
    <artifactId>core</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
<dependency>
    <groupId>com.test.latte</groupId>
    <artifactId>extensions</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

For current `LATEST_VERSION` please check GitHub's `releases` tab. 

## Usage

Matching `View` of a specific type:

```kotlin
match<View> {
    // matching view's conditions
}
```

Ensuring that no matching `View` exists in the view hierarchy:

```kotlin
noMatch<View> {
    // matching view's conditions
}
```

Performing actions with matched `View`:

```kotlin
match<View> {
    // matching view's conditions
}.interact {
    // actions performed on a view
}
```

Verifying `View`'s expected state:

```kotlin
match<View> {
    // matching view's conditions
}.verify {
    // assertions performed on a view
}
```

Verifying `View`'s expected state and providing custom error message when 
assertion fails:

```kotlin
match<View> {
    // matching view's conditions
}.verify("Something has gone wrong with this view!") {
    // assertions performed on a view
}
```

Sample use case matching `EditText` with a id `R.id.edit1`:

```kotlin
match<EditText> {
    id == R.id.edit1
}.interact {
    performClick()
    inputText("123")
}.verify("EditText is not focused") {
    isFocused
}.verify("EditText does not have text '123'") {
    hasText("123")
}
```