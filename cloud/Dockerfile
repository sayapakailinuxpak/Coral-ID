FROM python:3.9

# Python output is unbuffered
# its mean doesn't buffer in STD input output
# just send output straight out to the terminal
# (it makes easier where interect with docker container)
ENV PYTHONUNBUFFERED 1
ENV PROD_SERVER True
ENV GCP_PRODUCTION True

RUN mkdir /workspace
WORKDIR /workspace

# RUN apt-get update && apt-get upgrade -y && apt-get install --no-install-recommends -y gcc g++ libc-dev python3-dev default-libmysqlclient-dev
COPY ./api /workspace/
COPY ./requirements-cloud.txt /workspace/requirements-cloud.txt
RUN pip install -r requirements-cloud.txt

CMD ["python3", "manage.py", "runserver", "0.0.0.0:80"]
