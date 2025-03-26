# Fetch List Android App
### Fetch Rewards Coding Exercise - Software Engineering - Mobile
A simple Android app built with Jetpack Compose and Ktor that fetches a list of items from a remote API, filters them, and displays them grouped by `listId`.

## Screenshots

<img height="300" alt="Screenshot 2025-03-26 at 2 57 05 PM" src="https://github.com/user-attachments/assets/671c92cc-2ec7-4da8-bfbd-425cf0dad33d" />

<img height="300" alt="Screenshot 2025-03-26 at 3 00 50 PM" src="https://github.com/user-attachments/assets/b5e6dba9-c7b0-4c0e-8ac9-b2c44bfecc17" />

<img height="300" alt="Screenshot 2025-03-26 at 2 58 27 PM" src="https://github.com/user-attachments/assets/4ee46c18-7eb7-4c8a-8a16-f0b8e2045d9c" />

## Features 

- Simple, sleak UI with Jetpack Compose
- Remote data fetching using Ktor HTTP Client
- JSON parsing with Kotlin Serialization
- Filtering Rules
  - Items with 'null' or blank 'name' are excluded
  - Items are grouped by 'listId'
  - Each group is sorted by 'name' (lexicographic sorting)
- Search bar for users who know item ids but not which list they would be

## Tech Stack

| Layer        | Library / Tool            |
|--------------|---------------------------|
| UI           | Jetpack Compose, Material 3 |
| Networking   | Ktor Client (OkHttp)       |
| JSON Parsing | Kotlinx Serialization      |
| Architecture | ViewModel + State          |
| Testing      | JUnit, Compose UI Test     |

## Project tree

 * com.example.fetchrewards/
   * data/remote/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Data layer: API DTOs + network calls`
     * dto/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`API models (used with kotlinx.serialization)`
     * network/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; `Ktor client, endpoints, and service interfaces`
   * presentation/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`UI layer (Jetpack Compose)`
     * screen/ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Composable screens (HomeScreen, etc.)`
     * viewmodel &nbsp;&nbsp;&nbsp;&nbsp;`State + logic handling (e.g., HomeScreenViewModel)`
   * ui/theme &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Material 3 theming (colors, typography)`
   * MainActivity.kt &nbsp;&nbsp;&nbsp;&nbsp; `App entry`
  
## Why this structure?
`data.remote`
- Contains all network-related code, separated into:
  - `dto/` for raw API response models
  - `network/` for HTTP routing, service abstraction, and the Ktor client
- This separation allows easy testing, swapping APIs, or adding caching.

`presentation`
- Split into `screen/` and `viewmodel/` to follow MVVM.
- Composables in `screen/` focus on UI rendering.
- ViewModels in `viewmodel/` manage state, API calls, and business logic.

`ui.theme`
- Centralizes theme definitions for clean Material3 integration.

`MainActivity`
- App entry point, cleanly delegates UI to Composables.
- Easy to expand if navigation or lifecycle-scoped setup is needed later.

`Overall Benefits`
- Testability: Each layer can be tested in isolation
- Scalability: Easy to add features/screens without clutter
- Maintainability: Clear boundaries between concerns
- Reusability: Composables, services, and models can be reused across screens and features.
  


## How to Run 
1. Clone the repo
2. Open in Android Studio Iguana or later
3. Build & run on emulator or device

## "Future features"
- Offline caching
- Use hilt for depedency injection
- Implement more error handling and logging
- Add animations
- Improve UI design and layout
- Add integration tests
- Add lint checks
- Add client side/input validation
- Ensure secure logging
