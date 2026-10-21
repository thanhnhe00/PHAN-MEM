@echo off
title Push Ktgk to GitHub
echo ==============================================
echo Dang push du an FoodManager len branch Ktgk...
echo ==============================================
git push -u origin Ktgk --force
echo.
echo ==============================================
if %ERRORLEVEL% EQU 0 (
    echo [THANH CONG] Da push thanh cong len branch Ktgk!
) else (
    echo [THAT BAI] Co loi xay ra khi push.
)
echo ==============================================
pause
