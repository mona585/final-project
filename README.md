# 🍽️ Recipe App - ITI Final Project

<div align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-orange.svg" alt="Language">
  <img src="https://img.shields.io/badge/API-TheMealDB-blue.svg" alt="API">
  <img src="https://img.shields.io/badge/Architecture-MVVM-red.svg" alt="Architecture">
</div>

<div align="center">
  <h3>Discover, Cook, and Share Amazing Recipes</h3>
</div>

## 📱 About The Project

Recipe App is a modern Android application developed as the final project for ITI (Information Technology Institute). The app provides users with a delightful cooking experience by offering a vast collection of recipes from around the world, powered by TheMealDB API.

### ✨ Key Features

- 🔐 **User Authentication** - Secure login and registration with Firebase Authentication
- 🏠 **Dynamic Home Screen** - Daily recipe recommendations and curated meal suggestions
- 🔍 **Smart Search** - Search recipes by name, category, or country with real-time filtering
- ❤️ **Favorites Management** - Save your favorite recipes for quick access
- 📖 **Detailed Recipe View** - Step-by-step instructions with ingredients and video tutorials
- 🌙 **Dark Mode Support** - Toggle between light and dark themes
- 👤 **User Profile** - Manage account settings and preferences

## 🛠️ Built With

### Architecture & Design Patterns
- **MVVM Architecture** - Clean separation of concerns
- **Repository Pattern** - Abstraction layer for data operations
- **ViewBinding** - Type-safe view references
- **Navigation Component** - Simplified navigation between screens

### Libraries & Technologies

| Technology | Purpose |
|------------|---------|
| **Kotlin** | Primary programming language |
| **Coroutines** | Asynchronous programming |
| **LiveData** | Observable data holder |
| **ViewModel** | UI-related data management |
| **Retrofit** | REST API communication |
| **Room Database** | Local data persistence |
| **Firebase Auth** | User authentication |
| **Glide** | Image loading and caching |
| **Material Design 3** | Modern UI components |

## 📸 Screenshots

<div align="center">
<table>
  <tr>
    <td><img src="screenshots/splash.png" width="200" alt="Splash Screen"/></td>
    <td><img src="screenshots/login.png" width="200" alt="Login Screen"/></td>
    <td><img src="screenshots/home.png" width="200" alt="Home Screen"/></td>
    <td><img src="screenshots/details.png" width="200" alt="Recipe Details"/></td>
  </tr>
  <tr>
    <td align="center">Splash</td>
    <td align="center">Login</td>
    <td align="center">Home</td>
    <td align="center">Details</td>
  </tr>
</table>
</div>

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Kotlin 1.5.0 or higher
- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 34 (Android 14)

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/recipe-app.git
   ```

2. Open the project in Android Studio

3. Create a Firebase project and add your `google-services.json` file to the app directory

4. Build and run the project

## 🏗️ Project Structure

```
app/
├── data/
│   ├── local/          # Room database entities and DAOs
│   ├── models/         # Data models
│   ├── remote/         # Retrofit API services
│   └── repository/     # Repository implementations
├── view/
│   ├── auth/           # Authentication screens
│   ├── home/           # Home screen
│   ├── detail/         # Recipe details
│   ├── search/         # Search functionality
│   ├── favorites/      # Favorites screen
│   └── about/          # About/Profile screen
├── viewmodel/          # ViewModels for each feature
└── utils/              # Utility classes
```

## 🤝 Team Contributors

This project was developed collaboratively by a talented team of developers:

<table align="center">
  <tr>
    <td align="center">
      <img src="https://github.com/identicons/mark.png" width="100px;" alt="Mark Eskander"/><br />
      <sub><b>Mark Eskander Ebied</b></sub><br />
      <a href="https://github.com/markeskander" title="Code">💻</a>
    </td>
    <td align="center">
      <img src="https://github.com/identicons/alhusain.png" width="100px;" alt="Al-Husain Yaser"/><br />
      <sub><b>Al-Husain Yaser Ebrahim</b></sub><br />
      <a href="https://github.com/alhusain" title="Code">💻</a>
    </td>
    <td align="center">
      <img src="https://github.com/identicons/esraa.png" width="100px;" alt="Esraa Reda"/><br />
      <sub><b>Esraa Reda Elsayed Ali Hasan</b></sub><br />
      <a href="https://github.com/esraareda" title="Code">💻</a>
    </td>
    <td align="center">
      <img src="https://github.com/identicons/mona.png" width="100px;" alt="Mona Mohamed"/><br />
      <sub><b>Mona Mohamed Awad</b></sub><br />
      <a href="https://github.com/monamohamed" title="Code">💻</a>
    </td>
  </tr>
</table>

## 🔄 API Reference

This app uses [TheMealDB API](https://www.themealdb.com/api.php) for recipe data.

### Key Endpoints Used:
- `GET /random.php` - Get random meal
- `GET /search.php?s={query}` - Search meals by name
- `GET /lookup.php?i={id}` - Get meal details
- `GET /filter.php?c={category}` - Filter by category

## 📋 Features Implementation

### Authentication Flow
- Splash screen with auto-login check
- Email/Password authentication
- Email verification required
- Secure logout with data cleanup

### Data Management
- **Remote Data**: Fetched from TheMealDB API using Retrofit
- **Local Storage**: Favorites stored in Room database
- **State Management**: LiveData and ViewModel for reactive UI

### UI/UX Features
- Material Design 3 components
- Smooth animations and transitions
- Responsive layouts for different screen sizes
- Error handling with user-friendly messages
- Pull-to-refresh functionality
- Skeleton loading states

## 🎯 Future Enhancements

- [ ] Meal planning calendar
- [ ] Shopping list generation
- [ ] Social features (share recipes)
- [ ] Offline mode support
- [ ] Recipe rating system
- [ ] Multi-language support

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **ITI (Information Technology Institute)** - For the learning opportunity
- **TheMealDB** - For providing the free recipe API
- **Firebase** - For authentication services
- All instructors and mentors who guided us through this journey

---

<div align="center">
  Made with ❤️ by ITI Students
  <br>
  <strong>Final Project 2024</strong>
</div>
