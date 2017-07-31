
# Eaze Android Homework

![device-2017-07-31-003246](https://user-images.githubusercontent.com/593594/28767436-839ad246-7588-11e7-9603-4ecbcf81f013.png) ![gif](https://user-images.githubusercontent.com/593594/28767427-7d459a20-7588-11e7-99fd-fe867f7fc27a.gif)

## Installation and use
- A handful of Gradle dependencies have been added, and the build tools have been updated to version 25. The project should be easy to run from Android Studio, or an APK can be downloaded directly [here](https://github.com/jmschultz/homework-android/releases/tag/v1.0.0).

## Open Source Attribution
- [Butterknife](http://jakewharton.github.io/butterknife/), for view binding
- [Picasso](http://square.github.io/picasso/), for image downloading
- [Glide](https://github.com/bumptech/glide), for gif downloading
- [Retrofit](http://square.github.io/retrofit/), for API interactions
- [Moshi](https://github.com/square/moshi), for Json deserialization
- [Android Gif Drawable](https://github.com/koral--/android-gif-drawable), for displaying animated gifs

## Known issues
- There's some kind of bug in the way Glide loads images of variable size for a `StaggeredGridLayoutManager`, so this app uses Picasso for static image downloading and Glide for the gifs on the details screen. I should've spent more time digging into Glide's bug to consolidate on one library, but I time-boxed it for the demo.
- The app should have better separation of concerns everywhere. I tend to use something like the VIPER pattern in iOS and MVP in Android. Due to time constraints there's a lot of work happening in the Activities here where it doesn't belong.
- The code could also be made more DRY, styles should be genericized for reuse and the image downloading should be moved into a common place.
- Images should've been sized according to device, but I just used the default `still` and `original` URLs from the API response. Also, because all deal URLs are the same.
- There are a bunch of `TODOs` in the code. Most are notes for whomever gets stuck reviewing this.

## Improvements / things I like to use
- Dependency injection with [Dagger 2](http://google.github.io/dagger/)
- Analytics
- [Crash reporting](http://crashery.com/)
- Accessibility
- Nicer progress indicators during API calls
- A beautiful Material launcher icon 🎷 🐢
- Better tablet support.
- Unit tests and instrumentation tests with screenshots from [Spoon](http://square.github.io/spoon/)
- Caching for API responses and images
- [RxJava](https://github.com/ReactiveX/RxJava) for wrapping cloud responses in better `onSuccess(T type)` and `onFailure(Throwable t)` callbacks
- Better error handling and more defensive logic
- Fit-and-finish, some kind of loading animation for the gif cards
