@echo off
set host=localhost
set port=8080

call :assertEqual 0,0
EXIT /B 0
:assertEqual
set expected=%~1
set actual=%~2
if %expected%==%actual% (echo "Test Passed.") else (echo "Test failed. Expected %expected% but actual is %actual%" )
EXIT /B 0

