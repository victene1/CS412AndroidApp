# Assignments 2 and 3

Android app for CS412. Written in Kotlin with Jetpack Compose using the [Android Basics tutorial](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2).

- Main Activity: displays Airean Ashmore and student ID 1174900. The first two buttons open the second activity using explicit and implicit intents.
- Second Activity: lists five mobile software engineering challenges. The Main Activity button returns to the first screen.

## Assignment 3

- Start Service starts a foreground service and displays a notification saying "The service has started."
- Bind Service connects to the service, calls `getMyGrade()`, and displays the grade beside the button.
- Send Broadcast sends a custom broadcast. The broadcast receiver displays "Broadcast received!" when it receives the broadcast.
- The broadcast receiver is registered in `onStart()` and unregistered in `onStop()`.

## Device used

Pixel 5 emulator, Android 16 (API 36). The Assignment 2 version ran successfully, and both intent buttons and the return button worked.

## Run it

Open this folder in Android Studio and run the `app` configuration.
