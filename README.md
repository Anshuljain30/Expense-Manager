# Expense Manager

A modern Android expense tracking application built with Material Design 3 and following Android best practices.

## Features

- **Transaction Management**
  - Add income and expense transactions
  - Categorize transactions (Food, Utilities, Transportation, etc.)
  - Add descriptions and dates to transactions
  - Edit or delete existing transactions

- **Financial Overview**
  - View total income and expenses
  - Track spending by category
  - See transaction history in a clean, organized list

- **Modern UI/UX**
  - Material Design 3 components
  - Dark theme support
  - Smooth animations and transitions
  - Intuitive navigation

## Tech Stack

- **Architecture**
  - MVVM (Model-View-ViewModel)
  - Repository pattern
  - Single Activity with multiple fragments
  - ViewBinding for view access

- **Libraries & Components**
  - Room Database for local storage
  - Kotlin Coroutines for asynchronous operations
  - Flow for reactive data streams
  - Material Design 3 components
  - AndroidX libraries
  - Navigation Component

## Screenshots

[Add screenshots here]

## Setup & Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/expense-manager.git
```

2. Open the project in Android Studio

3. Build and run the app:
   - Minimum SDK: 24 (Android 7.0)
   - Target SDK: 34 (Android 14)

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/expensemanager/
│   │   ├── data/                    # Data layer
│   │   │   ├── dao/                 # Database access
│   │   │   ├── database/            # Room database
│   │   │   ├── model/               # Data models
│   │   │   └── repository/          # Data repositories
│   │   ├── ui/                      # UI layer
│   │   │   ├── adapter/             # RecyclerView adapters
│   │   │   └── fragments/           # UI fragments
│   │   └── viewmodel/               # ViewModels
│   └── res/                         # Resources
│       ├── layout/                  # Layout files
│       ├── drawable/                # Images and shapes
│       ├── values/                  # Resource values
│       └── navigation/              # Navigation graphs
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Material Design 3 for design guidelines
- Android Jetpack libraries
- Open-source community 