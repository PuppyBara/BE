#!/bin/bash

cd /home/ubuntu/app

DOCKER_APP_NAME=spring

current_date=$(date +%Y-%m-%d)
current_time=$(date +%H:%M:%S)

# 실행중인 blue가 있는지
EXIST_BLUE=$(docker compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep " Up ")

# green이 실행중이면 blue up
if [ -z "$EXIST_BLUE" ]; then
	echo "$current_date $current_time :: blue up" >> /home/ubuntu/deploy.log
	docker compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build

	sleep 30

	docker compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
	docker image prune -af # 사용하지 않는 이미지 삭제

# blue가 실행중이면 green up
else
	echo "$current_date $current_time :: green up" >> /home/ubuntu/deploy.log
	docker compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build

	sleep 30

	docker compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
	docker image prune -af
fi