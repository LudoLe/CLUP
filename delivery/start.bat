cd frontend
start cmd /k .\liveserver.bat

cd ..

cd backend
start cmd /k .\clup.bat

cd ..

timeout 10

cd backend
start cmd /k .\swagger.bat

start http://localhost:5000/