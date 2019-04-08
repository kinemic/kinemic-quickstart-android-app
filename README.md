# Kinemic SDK Quickstart Android Application

This repository contains the sample application from [Kinemic's quickstart guide](https://developer.kinemic.com/docs/android/latest/quickstart.html), explaining the integration of the Kinemic SDK for gesture control into an android application.
For more information about the SDK and our solutions for gesture interaction visit us at: https://kinemic.com.

## Application

This sample project instanciates the de.kinemic.sdk.Engine inside its MainActivity.

Afterwards it uses the engine to connect the closest Kinemic Band and registers
a gesture listener.

The Application disconnects connected sensors and releases ressources when the user leaves the app.
