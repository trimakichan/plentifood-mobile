


https://github.com/user-attachments/assets/c2e46f03-70f1-47dd-87d5-46301649a04f



# Plentifood

Plentifood is a mobile application that helps users find nearby food resources. Users can search for food banks, meals, and other sites, and view them on a map.
This app was built as a capstone project for Ada Developers Academy.

## Disclaimer

Most site data in this app comes from the Seattle public food resources API (https://data.seattle.gov/resource/kkzf-ntnu.json).  
Business hours shown in the app are placeholder values that were added manually for testing and development purposes.

Actual hours may be different. Please check official sources before visiting a location.

## Features

*   **Search for food sites:** Find food resources based on your current location or a searched address.
*   **Filter results:** Narrow down your search by day of the week, organization type, service type, and radius in miles from the current or searched location.
*   **Map View:** See all the food sites in your area on an interactive map.
*   **Autocomplete Search:** Get search suggestions as you type.
*   **Current Location:** Use your device's GPS to find food resources near you.
*   **Get Directions:** Open directions to a selected site in Google Maps by clicking the directions icon.
*   **Admin Sign-up:** Administrators can create an account to manage food resource listings.
*   **Register Organizations and Sites:** Registered admins can add organizations and food distribution sites, and delete sites.

## Environmental Setup

To build and run this project, you will need to have Android Studio installed.

### 1. Get the Code

Clone the repository to your local machine:

```bash
git clone [https://github.com/your-username/plentifood.git](https://github.com/trimakichan/plentifood-mobile.git)
```

### 2. Set up API Keys

The app uses the Google Maps API, which requires an API key.

1.  Create a `local.properties` file in the root of the project.
2.  Add your Google Maps API key to the `local.properties` file:

```
MAPS_API_KEY=YOUR_API_KEY
```

### 3. Build and Run

Open the project in Android Studio and run the app on an emulator or a physical device.
