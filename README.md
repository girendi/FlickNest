# FlickNest - Discover Movies
MovieMania is a native mobile application that leverages the [The Movie Database (TMDb) API](https://developer.themoviedb.org/docs/getting-started) to bring the fascinating world of movies to your fingertips. Explore a wide range of genres, discover movies, dive into detailed movie information, read user reviews, and watch trailers—all within a single app.

## Features
<ul>
  <li>Genre Listing: Browse through the official list of movie genres.</li>
  <li>Discover Movies by Genre: Explore movies categorized by genre.</li>
  <li>Movie Details: View primary information about movies, such as synopsis, cast, and ratings.
</li>
  <li>User Reviews: Read what others have to say about a movie.</li>
  <li>Movie Trailers: Watch movie trailers directly in the app via YouTube.</li>
  <li>Endless Scrolling: Enjoy endless scrolling both in the list of movies and user reviews.</li>
  <li>Comprehensive Error Handling: The app covers both positive and negative cases to ensure a smooth user experience.</li>
</ul>

## Getting Started
### Prerequisites
<ul>
  <li>Android Studio</li>
  <li>A valid API key from TMDb.
  </li>
</ul>

### Installation
<ul>
  <li>Clone this repository.</li>
  <li>Open the project in your IDE (Android Studio).</li>
  <li>Insert your TMDb API key into the designated spot in the Config file.</li>
  <li>Build and run the app on your device or emulator.</li>
</ul>

## Built With
<ul>
  <li>[Kotlin] - Primary programming language.</li>
  <li>[Retrofit] - For network requests.</li>
  <li>[Room] - For local data storage.</li>
  <li>[Clean Architecture] - This project is structured using Clean Architecture principles to ensure separation of concerns, scalability, and easier maintenance.</li>
  <li>[Coroutine Flow] - Utilized Coroutine Flow for managing asynchronous data streams with lifecycle-aware components, enhancing the app's responsiveness and performance.</li>
  <li>[Koin] - A lightweight dependency injection framework, Koin is used for managing component dependencies, making the code more modular and testable.</li>
</ul>

## Contributing
We welcome contributions. If you would like to contribute, please fork the repository and submit a pull request.

## Authors
Gideon Panjaitan - *Initial Work* - [girendi](https://github.com/girendi)

## Acknowledgments
<ul>
  <li>Thanks to TMDb for providing the API used in this project.</li>
  <li>This project was inspired by the endless possibilities in movie exploration and the desire to provide a seamless user experience.</li>
</ul>

