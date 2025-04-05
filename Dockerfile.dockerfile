FROM python:3.9-slim

WORKDIR /app

COPY /flaskProject/project-code /app

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 5000

# Define the environment variable for Flask (or your app)
ENV FLASK_APP=app.py

CMD ["python", "app.py"]
