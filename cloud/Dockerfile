FROM python:3.9-alpine

# Python output is unbuffered
# its mean doesn't buffer in STD input output
# just send output straight out to the terminal
# (it makes easier where interect with docker container)
ENV PYTHONUNBUFFERED 1

RUN mkdir /workspace
WORKDIR /workspace

COPY ./api /workspace/
COPY ./model /workspace/
COPY ./requirements-cloud.txt /workspace/requirements-cloud.txt
RUN pip install -r requirements-cloud.txt

CMD ["python3", "manage.py", "runserver", "0.0.0.0:8000"]
