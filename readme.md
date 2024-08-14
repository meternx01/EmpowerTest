# Empower Custom Views App


This Android application is designed to read and display beneficiary information from a JSON file. The app parses the JSON data using Android's built-in `JSONObject` and `JSONArray` classes and presents it in a user-friendly interface using custom views.

## Requirements
- Display a list of beneficiaries using data from the [Beneficiaries.json](app%2Fsrc%2Fmain%2Fassets%2FBeneficiaries.json) file.
  - On the main display, show the First and Last Name of the beneficiary, and show their type and designation.
- When selecting a beneficiary, display more information,
  - SSN
  - Date of Birth
  - Phone Number
  - Address

- Avoid using the following
  - Third-Party Libraries
- Use Custom Views
  - No Layout XML
  - No Jetpack Compose

## Solution
This is a Kotlin based Android Native app that reads in [Beneficiaries.json](app%2Fsrc%2Fmain%2Fassets%2FBeneficiaries.json) and displays the information in a Custom Recycler View.

Use of the following:
- InputStream to read from the .json file
- JSONObject / JSONArray to parse the result from the InputStream \
  ` val jsonObject = JSONArray(json).getJSONObject(i)`
- Custom Recycler View w/ Adapter
- Alert Dialog to display more information

## Limitations and Possible Improvements

Due to the consolidated time window to produce this app, I have identified a few improvements

- **Better Separation of Concerns**: This app separates the `RecyclerView.Adapter` into its own *.kt* file. If developing further, I would make a Repository so a ViewModel could be used to further separate out the reading / parsing of the JSON out of the [MainActivity.kt](app%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fempowertest%2FMainActivity.kt).
- **Better "More Details"**: Currently the app displays more information in an `AlertDialog`. This was due to simplicity based on time. Further enhancement would be either to use one or more fragments to display the information. Alternatively, using an expandable `RecyclerView` to keep the focus on the list, while displaying information in a non-modal way.
- **Implement Unit Testing**: Add unit tests to ensure the JSON parsing and data handling logic is robust and error-free.



        