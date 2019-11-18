call runcrud.bat

if "%ERRORLEVEL%" == "0" goto open
echo.
echo runcrud has errors - breaking work
goto fail

:open
start "Firefox" "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot open firefox

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.