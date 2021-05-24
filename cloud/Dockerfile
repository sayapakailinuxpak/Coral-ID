FROM python:3

# Python output is unbuffered
# its mean doesn't buffer in STD input output
# just send output straight out to the terminal 
# (it makes easier where interect with docker container)
ENV PYTHONUNBUFFERED 1

RUN mkdir workspace
WORKDIR workspace

COPY . /workspace/

RUN pip install -r requirements-cloud.txt


