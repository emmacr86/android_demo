# android demo NYTimes

The demos is a NY Times Article Viewer App! This app is built in Kotlin using Jetpack Compose, designed to fetch and display articles from the New York Times API. Below you will find features and instructions to get started:

Features:

Home Screen:
Fetches and displays a list of articles from the New York Times API.
Each article entry shows the title, abstract, and a image of the article (Some articles does not contain images so a blue background is displayed).


![Screenshot_1719285690](https://github.com/emmacr86/android_demo/assets/33577805/63c64e87-1c78-455d-9cde-3c1f7e712172)


Article Details Screen:
Opens detailed information about a selected article.
Shows the full article title, abstract, images, topics and publish date of the article. In addition the user can share the article and open the original link from the new york times.


![Screenshot_1719285720](https://github.com/emmacr86/android_demo/assets/33577805/86a03ed4-462b-48d3-b770-aae3f771defb)


Settings Screen:
The NY Times requires an API key for get data, so there are two ways for set the api key: 
1-Is able for set the api key from Settings Screen. (Feel free to delete this screen, it is for configuration purposes only)
2-From project directory "src/main/res/values/strings.xml" there is an string called "api_key" you can set it right there. 

Installation:
Download the the project and run it in Android Studio.

Usage:
The app requires internet access to fetch articles from the New York Times API.

Customization:
The app uses Jetpack Compose for UI rendering, providing a modern and responsive user interface.

Notes:
API Key: Ensure your API key for the New York Times API is correctly configured in the app for fetching articles.

Developer Notes:
This app was developed using Kotlin and Jetpack Compose, leveraging modern Android development practices.
API calls are handled asynchronously to ensure smooth performance and responsiveness.
Codebase adheres to best practices for Kotlin and Jetpack Compose, promoting maintainability and scalability.



