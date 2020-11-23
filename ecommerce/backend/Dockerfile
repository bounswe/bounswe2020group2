FROM python:3.8-slim
ENV PYTHONUNBUFFERED=1
WORKDIR /code
COPY requirements.txt /code/
RUN pip install -r requirements.txt
COPY . /code/
RUN rm -rf ./venv
EXPOSE 8000
RUN chmod +x docker_entrypoint.sh
ENTRYPOINT ["./docker_entrypoint.sh"]
