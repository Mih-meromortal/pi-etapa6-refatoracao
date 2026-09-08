@echo off
echo target/ > .gitignore
echo .idea/ >> .gitignore
echo *.class >> .gitignore

git init
git add .
git commit -m "feat: refatoracao de arquitetura desktop para desacoplamento web"
git branch -M main
git remote add origin https://github.com/Mih-meromortal/pi-etapa6-refatoracao.git
git push -u origin main

echo.
echo Processo concluido com sucesso!
pause