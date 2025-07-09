@echo off
echo Installing pre-commit hook...

set source=scripts\pre-commit
set target=.git\hooks\pre-commit

if not exist "%source%" (
    echo Source file "%source%" does not exist.
    exit /b 1
)

if not exist ".git\hooks" (
    echo Creating .git\hooks directory...
    mkdir ".git\hooks"
)

copy /Y "%source%" "%target%"
if %errorlevel% neq 0 (
    echo Failed to copy pre-commit script.
    exit /b 1
)

echo Pre-commit hook installed successfully.

exit /b 0
