# My Application

This is an Android application built using Kotlin and Jetpack Compose. The app fetches and displays movies, series, and actors from The Movie Database (TMDb) API.

## Features

- Display a list of popular movies
- Search for movies by title
- Display a list of popular series
- Search for series by title
- Display a list of popular actors
- Search for actors by name

## Technologies Used

- Kotlin
- Jetpack Compose
- Retrofit
- Coroutines
- StateFlow
- Coil

## Getting Started

### Prerequisites

- Android Studio Ladybug | 2024.2.1 Patch 2
- A TMDb API key

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/your-repo-name.git
    ```
2. Open the project in Android Studio.
3. Add your TMDb API key in the `MainViewModel.kt` file:
    ```kotlin
    private const val API_KEY = "your_api_key_here"
    ```

### Running the App

1. Connect an Android device or start an emulator.
2. Click on the "Run" button in Android Studio.

## Usage

- Use the search bar in the Movies, Series, or Actors screens to search for specific items.
- Click on an item to view more details (navigation not implemented yet).

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [The Movie Database (TMDb)](https://www.themoviedb.org/) for providing the API.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the UI framework.
- [Coil](https://coil-kt.github.io/coil/) for image loading.
