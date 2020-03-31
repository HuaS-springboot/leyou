@echo off
echo update code
@REM svn update

echo Started Install Project 
call mvn clean install -Dmaven.test.skip=true

@echo Build VSJ_COMMON Project END
pause
