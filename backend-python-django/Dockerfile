# syntax=docker/dockerfile:1
FROM python:3-alpine
RUN apk add --no-cache sqlite
RUN addgroup -S django && adduser -S django -G django
USER django:django

WORKDIR /home/django
COPY . .
ENV PATH="/home/django/.local/bin:${PATH}"
RUN pip install -r requirements.txt
RUN python -c 'from django.core.management.utils import get_random_secret_key; print(get_random_secret_key())' > .django-secret-key
RUN export DJANGO_SECRET_KEY=`cat .django-secret-key` && export DJANGO_DEBUG=False && python manage.py migrate
RUN export DJANGO_SECRET_KEY=`cat .django-secret-key` && export DJANGO_DEBUG=False && python manage.py loaddata app_moods/fixtures/geometry_shapes.yaml
EXPOSE 8000
ENV PYTHONUNBUFFERED=1
CMD export DJANGO_SECRET_KEY=`cat .django-secret-key` && export DJANGO_DEBUG=True && python manage.py runserver 0.0.0.0:8000
