# cashi
A simple FinTech app using Kotlin Multiplatform that allows users to send a payment to a recipient.

Setup Steps

Clone the repository
bash git clone https://github.com/Brenda-Okoro/cashi.git
cd cashi

## Setup Firebase

## Create a new Firebase project
Enable Firestore
Add your google-services.json to androidApp/


## Install dependencies
bash ./gradlew build

## Start the mock API server
bash cd backend
npm install
npm start

## Run the Android app
bash ./gradlew :androidApp:installDebug


# Running Tests
## Unit Tests
bash ./gradlew testDebugUnitTest --tests "*.PaymentAppUITest" or run directly from the class
## BDD Tests
bash ./gradlew testDebugUnitTest --tests "*.PaymentTestSuite" or run directly from the class
## UI Tests (Appium)
## Start Appium server
bash appium
## Run tests
./gradlew :androidApp:connectedAndroidTest

# KMP Architecture Benefits

## Cross-Platform Potential

- Shared Business Logic: Payment validation, API calls, and data models
- Platform-Specific UI: Android uses Jetpack Compose, iOS would use SwiftUI
- Code Reuse: ~60-70% code sharing between platforms
- Consistent Behavior: Same validation rules and API handling across platforms

## Architecture Layers

- Presentation Layer: Platform-specific (Android/iOS)
- Domain Layer: Shared (Use cases, validation)
- Data Layer: Shared (Repository, API client, models)
- Platform Layer: Platform-specific (Firebase, networking)

This implementation provides a complete, payment app with comprehensive testing coverage 
and demonstrates the power of Kotlin Multiplatform for cross-platform development.
