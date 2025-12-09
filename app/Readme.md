# 📱 MVI Profile App

A modern Android application demonstrating **MVI (Model-View-Intent)** architecture pattern with **Clean Architecture** principles using Jetpack Compose.

![Screenshot_20251209_135820.png](../../../Desktop/Screenshot_20251209_135820.png)
![Screenshot_20251209_135849.png](../../../Desktop/Screenshot_20251209_135849.png)
![Screenshot_20251209_135859.png](../../../Desktop/Screenshot_20251209_135859.png)
---

## 📖 Table of Contents

- [About the Project](#about-the-project)
- [What is MVI?](#what-is-mvi)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Features](#features)
- [MVI Implementation Details](#mvi-implementation-details)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 About the Project

This project is a demonstration of building a user profile screen using the **MVI (Model-View-Intent)** architecture pattern combined with **Clean Architecture** principles. It showcases best practices for modern Android development with Jetpack Compose, focusing on:

- **Unidirectional Data Flow**: Single source of truth with predictable state management
- **Separation of Concerns**: Clear boundaries between layers
- **Testability**: Easy to test business logic and UI independently
- **Scalability**: Modular structure for easy feature additions
- **Type Safety**: Kotlin's type system to prevent runtime errors

### Key Highlights

✅ Pure MVI implementation with Intent processing  
✅ Clean Architecture with 3 modules (app, data, domain)  
✅ Jetpack Compose for modern declarative UI  
✅ State management with Kotlin Flow  
✅ Mock data source for demonstration  
✅ Error handling and loading states  
✅ Material Design 3 components

---

## 🧩 What is MVI?

**MVI (Model-View-Intent)** is an architectural pattern that provides a unidirectional and cyclical data flow, making state management predictable and testable.

### MVI Components

```
┌─────────────────────────────────────────────────────────┐
│                                                         │
│    USER INTERACTION (Intent)                            │
│            │                                            │
│            ▼                                            │
│    ┌──────────────┐                                    │
│    │   INTENT     │  User actions (LoadProfile,        │
│    │  PROCESSOR   │  FollowUser, UnfollowUser)         │
│    └──────────────┘                                    │
│            │                                            │
│            ▼                                            │
│    ┌──────────────┐                                    │
│    │    MODEL     │  Business logic processes          │
│    │  (ViewModel) │  intent and updates state          │
│    └──────────────┘                                    │
│            │                                            │
│            ▼                                            │
│    ┌──────────────┐                                    │
│    │    STATE     │  Single immutable state object     │
│    │  (UiState)   │  (isLoading, user, error)          │
│    └──────────────┘                                    │
│            │                                            │
│            ▼                                            │
│    ┌──────────────┐                                    │
│    │     VIEW     │  UI observes state and renders     │
│    │  (Composable)│  UI accordingly                     │
│    └──────────────┘                                    │
│            │                                            │
│            └────────────────────────────────────────────┘
│                     (User interacts, cycle repeats)
```

### MVI Principles

#### 1. **Intent (User Actions)**
Represents user intentions or actions. Every user interaction is converted to an Intent.

```kotlin
sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    object FollowUser : ProfileIntent()
    object UnfollowUser : ProfileIntent()
    object Retry : ProfileIntent()
}
```

#### 2. **Model (State Management)**
The ViewModel processes intents and produces new states. State is immutable and represents the UI at any point in time.

```kotlin
data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isFollowing: Boolean = false
)
```

#### 3. **View (UI Rendering)**
The View observes the state and renders UI accordingly. It's a pure function of state.

```kotlin
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    when {
        uiState.isLoading -> LoadingIndicator()
        uiState.error != null -> ErrorScreen()
        uiState.user != null -> ProfileContent()
    }
}
```

### Why MVI?

| Advantage | Description |
|-----------|-------------|
| **🎯 Predictable State** | Single source of truth makes debugging easier |
| **🔄 Unidirectional Flow** | Data flows in one direction, reducing bugs |
| **🧪 Testable** | Pure functions are easy to unit test |
| **📝 Time Travel Debugging** | Can replay state changes for debugging |
| **🔐 Thread Safe** | Immutable state prevents race conditions |
| **🎨 Declarative UI** | UI is a pure function of state |

### MVI vs Other Patterns

| Pattern | State Management | Complexity | Learning Curve |
|---------|-----------------|------------|----------------|
| **MVI** | Single UiState | Medium | Medium |
| **MVVM** | Multiple LiveData/Flow | Low | Low |
| **MVP** | View interface | Medium | Medium |
| **Redux** | Single Store | High | High |

---

## 📸 Screenshots

<!-- Add your screenshots here -->

### Loading State
![Loading State](screenshots/loading_state.png)
*Profile screen showing loading indicator while fetching user data*

---

### Profile Loaded
![Profile Screen](screenshots/profile_loaded.png)
*Main profile screen displaying user information and follow button*

---

### Error State
![Error State](screenshots/error_state.png)
*Error screen with retry functionality when network request fails*

---

### Follow Action
![Follow Action](screenshots/follow_action.png)
*Demonstrating the follow/unfollow functionality with updated follower count*

---

### Demo Video
![Demo](screenshots/demo.gif)
*Full application demo showing all states and interactions*

---

> **Note**: Add your actual screenshots to the `screenshots/` folder in the project root

---

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns across three modules:

```
┌─────────────────────────────────────────────────────────┐
│                     APP MODULE                          │
│                  (Presentation Layer)                   │
│  ┌─────────────────────────────────────────────────┐   │
│  │  UI (Jetpack Compose)                           │   │
│  │  - ProfileScreen                                │   │
│  │  - Components (Loading, Error, Content)         │   │
│  └─────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────┐   │
│  │  ViewModel                                      │   │
│  │  - ProfileViewModel (MVI Intent Processing)     │   │
│  │  - ProfileUiState                               │   │
│  │  - ProfileIntent                                │   │
│  └─────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│                    DATA MODULE                          │
│                   (Data Layer)                          │
│  ┌─────────────────────────────────────────────────┐   │
│  │  Data Sources                                   │   │
│  │  - UserRemoteDataSource (Mock API)              │   │
│  │  - UserLocalDataSource (Cache)                  │   │
│  └─────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────┐   │
│  │  Repository Implementation                      │   │
│  │  - UserRepositoryImpl                           │   │
│  └─────────────────────────────────────────────────┘   │
│  ┌─────────────────────────────────────────────────┐   │
│  │  Mappers                                        │   │
│  │  - DTO ↔ Domain Model conversions               │   │
│  └─────────────────────────────────────────────────