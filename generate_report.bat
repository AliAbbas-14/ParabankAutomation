@echo off
echo ============================================
echo   GENERATING ALLURE REPORT - PARABANK
echo ============================================

echo Step 1: Downloading Allure Commandline...
if not exist "allure-2.24.0" (
    echo Downloading Allure 2.24.0...
    powershell -Command "Invoke-WebRequest -Uri 'https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip' -OutFile 'allure.zip'"
    powershell -Command "Expand-Archive -Path 'allure.zip' -DestinationPath '.' -Force"
    del allure.zip
    echo Allure downloaded successfully!
) else (
    echo Allure already exists.
)

echo.
echo Step 2: Running tests from Eclipse output...
echo (Using existing test results from test-output folder)

echo.
echo Step 3: Generating Allure report...
if exist "test-output" (
    allure-2.24.0\bin\allure.bat generate test-output -o allure-report --clean
    echo Report generated: allure-report\index.html
) else (
    echo ERROR: No test results found!
    echo Please run tests in Eclipse first.
    pause
    exit /b 1
)

echo.
echo Step 4: Opening report in browser...
start allure-report\index.html

echo.
echo ============================================
echo   REPORT GENERATED SUCCESSFULLY!
echo   Browser should open automatically.
echo ============================================
echo.
pause