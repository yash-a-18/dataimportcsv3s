# DataImportCSV3s

## Overview
DataImportCSV3s is a Scala-based project designed for importing and processing CSV data across both JVM and JavaScript platforms. This repository leverages Scala.js and cross-platform libraries to ensure compatibility and efficiency.

## Prerequisites
- [SBT (Scala Build Tool)](https://www.scala-sbt.org/) installed on your system.
- Ensure the required dependencies are configured in your environment.

## Steps to Build and Run

1. **Start SBT**  
   Open a terminal and navigate to the project directory. Run:
   ```sh
   sbt
   ```

2. **Switch to the JavaScript project**  
   In the SBT shell, switch to the JavaScript project:
   ```sh
   project dataimportcsv3sJS
   ```

3. **Compile the JavaScript project**  
   Compile the JavaScript project:
   ```sh
   compile
   ```

4. **Switch to the JVM project**  
   In the SBT shell, switch to the JVM project:
   ```sh
   project dataimportcsv3sJVM
   ```

5. **Compile the JVM project**  
   Compile the JVM project:
   ```sh
   compile
   ```

6. **Switch to the root project**  
   In the SBT shell, switch to the root project:
   ```sh
   project root
   ```

7. **Publish locally**  
   Publish the project locally:
   ```sh
   publishLocal
   ```

## Additional Notes
Ensure that all required configuration files (e.g., application.conf) are correctly set up before running the project.
For testing, use the provided test cases in the src/test directories for both JVM and JS modules.