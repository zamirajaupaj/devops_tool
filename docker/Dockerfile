FROM node:latest

MAINTAINER Zamira Jaupaj <zamriajaupaj86@gmail.com>

RUN apt-get update

ADD https://releases.hashicorp.com/packer/1.1.3/packer_1.1.3_linux_amd64.zip .
ADD https://releases.hashicorp.com/terraform/0.11.1/terraform_0.11.1_linux_amd64.zip .

RUN apt-get install build-essential libssl-dev libcurl4-gnutls-dev libexpat1-dev gettext unzip git -y

RUN unzip packer_1.1.3_linux_amd64.zip -d /usr/bin
RUN unzip terraform_0.11.1_linux_amd64.zip -d /usr/bin
RUN rm -rf *.zip 



