# MusicSearch-MVVM-Retrofit-Gson/SimpleXML-Dagger-Picasso-Kotlin_DSL_with_Tests

# This is a MVVM Android App developed using some of latest Android libraries as of 2021

1. Model-View-ViewModel architecture
2. Dagger for Dependency Injection
3. Retrofit for Service with Gson for Json Parsing and SimpleXML for XML Parsing
4. Kotlin for development
5. Kotlin DSL for converting gradle scripts from Groovy to Kotlin
6. Coroutines for viewModel handling
7. RecyclerView and CardView for list and individual list item views
8. Picasso Image API for image loading/caching
9. Mockito/Espresso/extJunit/Robolectric for unit testing and android tests for viewmodel, views
10. OKHttp Interceptor/Mockserver for logging service responses on Logcat and creating mock server for testing Repository

Had to use Gson instead of Moshi which is recommended library for Json parsing by Square, because the response from
iTunes curl gives out a Json text file instead of Json as application/json content-type. 
Could'nt figure out a way to solve it throught Moshi, hence I had to settle with Gson which earlier older version used and worked

In lyric's curl the response is a XML, used SimpleXML library, JAXB and Jackson did not work with many combination of 
annotation and data class changes. May be in future worth looking at other libraries given that SimpleXML lib is depcreted
