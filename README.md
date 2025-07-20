### cashi
A simple FinTech app using Kotlin Multiplatform that allows users to send a payment to a recipient.

Setup Steps

Clone the repository
bash git clone https://github.com/Brenda-Okoro/cashi.git
cd cashi

## Setup Firebase

# Create a new Firebase project
Enable Firestore
Add your google-services.json to androidApp/


# Install dependencies
bash./gradlew build

# Start the mock API server
bashcd backend
npm install
npm start

# Run the Android app
bash./gradlew :androidApp:installDebug


## Running Tests
# Unit Tests
bash./gradlew testDebugUnitTest --tests "*.PaymentAppUITest" or run directly from the class
# BDD Tests
bash./gradlew testDebugUnitTest --tests "*.PaymentTestSuite" or run directly from the class
# UI Tests (Appium)
bash# Start Appium server
appium
# Run tests
./gradlew :androidApp:connectedAndroidTest

