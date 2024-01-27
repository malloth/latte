[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

# Latte

Latte is a lightweight and easy to use UI testing framework for Android utilizing Kotlin DSL.

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
- Compile Android SDK: 34 or later

## Setup

In `{root}/build.gradle` add:

```gradle
allprojects {
    repositories {
        maven {
            url "https://maven.pkg.github.com/malloth/latte"
        }
    }
}
```

In `{module}/build.gradle` add:

```gradle
dependencies {
    androidTestImplementation "pl.codesamurai.latte:core:{LATEST_VERSION}"
    androidTestImplementation "pl.codesamurai.latte:core-ktx:{LATEST_VERSION}"
}
```

For current `{LATEST_VERSION}` please check GitHub's `releases` tab.

## Usage

Matching `View` of a specific type:

```kotlin
val viewMatcher: (View) -> Boolean = { /* matching view's conditions */ }

match(viewMatcher)
```

Ensuring that no matching `View`s exists in the view hierarchy:

```kotlin
val viewMatcher: (View) -> Boolean = { /* matching view's conditions */ }

noMatch(viewMatcher)
```

Performing actions with matched `View`:

```kotlin
val viewMatcher: (View) -> Boolean = { /* matching view's conditions */ }

match(viewMatcher) {
    // actions performed on a view(s)
}
```

Verifying `View`'s expected state:

```kotlin
val viewMatcher: (View) -> Boolean = { /* matching view's conditions */ }

match(viewMatcher) {
    verify {
        // assertion(s) performed on a view(s)
    }
}
```

or

```kotlin
// GIVEN
val viewMatcher: (View) -> Boolean = { /* matching view's conditions */ }

// WHEN
val matching = match(viewMatcher) {
    // actions performed on a view(s)
}

// THEN
matching.verify {
    // assertion(s) performed on a view(s)
}
```

Sample use case matching `EditText` with an id `R.id.edit1`:

```kotlin
// GIVEN
val editTextWithId: (EditText) -> Boolean = { id == R.id.edit1 }

// WHEN
val matching = match(editTextWithId) {
    tap()
    type("123")
}

// THEN
matching.verify {
    isFocused && hasText("123")
}
```

## Sample

Inside this repository there's a sample app module with a couple of UI tests,
showing how to write those using this framework.

To run all the tests:

```shell
./gradlew :sample:pixelCDebugAndroidTest
```