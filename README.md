# Latte

Latte is a lightweight and easy to use UI testing framework for 
Android utilizing Kotlin DSL. 

#### Features

- Has a very small footprint
- Is a standalone framework
- Utilizes Kotlin DSL
- Uses Android API for matching, interactions and verifications
- Narrows the scope of matches, interactions and verifications to a given type of View
- Enables easy debugging of View's current state
- Gives a fine grained control over view matching process
- Provides a separate library artifact with helper interactions and verifications

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
<dependencies>
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
</dependencies>
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
}.verifyWithResult {
    someAssertion() orFail "Something has gone wrong with this view!"
}
```

Sample use case matching `EditText` with an id `R.id.edit1`:

```kotlin
match<EditText> {
    id == R.id.edit1
}.interact {
    performClick()
    inputText("123")
}.verify {
    isFocused
}.verifyWithResult {
    hasText("123") orFail "EditText has text '$text' instead of '123'"
}
```

## Sample

Inside this repository there's a sample app module with a couple of UI tests, 
showing how to write those using this framework.

To run all the tests:

```shell
./gradlew :sample:connectedAndroidTest
```