# novel-ghostwriter

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/klemmy129/novel-ghostwriter?display_name=tag&sort=semver)
![GitHub top language](https://img.shields.io/github/languages/top/klemmy129/novel-ghostwriter)
![GitHub](https://img.shields.io/github/license/klemmy129/novel-ghostwriter)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/klemmy129/novel-ghostwriter)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/klemmy129/novel-ghostwriter/CodeQL)
![Snyk Vulnerabilities for GitHub Repo (Specific Manifest)](https://img.shields.io/snyk/vulnerabilities/github/klemmy129/novel-ghostwriter/pom.xml)
![GitHub issues](https://img.shields.io/github/issues/klemmy129/novel-ghostwriter)

## Description
This is a companion demo app to [novel-ideas](https://github.com/klemmy129/novel-ideas)
and is meant to be run together.

This is also a Java Spring Boot demonstrator for a Rest application, but uses a client library form 
[novel-ideas](https://github.com/klemmy129/novel-ideas) to communicate with it.

## Table of Contents
<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Technology Used](#technology-used)
- [Building](#building)
- [Startup](#startup)
  - [Active Profiles](#active-profiles)
  - [Environment Variables](#environment-variables)
    - [application.yml](#applicationyml)
  - [Message bus](#message-bus)
    - [ActiveMQ](#activemq)
      - [Setup](#setup)
    - [RabbitMQ](#rabbitmq)
    - [Kafka](#kafka)
- [Coding Demo Explained](#coding-demo-explained)
  - [The client](#the-client)
  - [Message bus](#message-bus-1)
    - [ActiveMQ](#activemq-1)
    - [RabbitMQ](#rabbitmq-1)
    - [Kafka](#kafka-1)
  - [Banner](#banner)
- [Next](#next)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Technology Used
- Java 17
- Maven
- Spring Boot 3.0.1
- Spring Docs
- ActiveMQ Artemis
- Lombok
- [Certificates](https://github.com/klemmy129/novel-ideas/blob/main/CERTS.md)

## Building

```
mvn clean package
```
## Startup
### Active Profiles
* ActiveMQ `activemq`

### Environment Variables
#### application.yml
- PORT _default 11443_
- TRUSTSTORE _default /home/${user}/certs/truststore.p12_
- TRUSTSTORE-PASSWORD
- KEYSTORE _default /home/${user}/certs/keystore.p12_
- KEYSTORE-PASSWORD
- NOVEL-IDEAS-URL _default https://servername.devstuff.org:10443_ (Novel-Ideas base URL)

### Message bus
#### ActiveMQ
##### Setup
1. Download ActiveMQ binary from https://activemq.apache.org/download.html
2. Uncompress the file
3. For Linux Open a terminal and goto ActiveMQ -> bin
4. To start ActiveMQ run `./activemq start`

[novel-ideas](https://github.com/klemmy129/novel-ideas) is the producer, triggered by calling the Rest endpoint: Get `/book/{id}`
The result will display a console message eg `c.k.n.g.listener.ActiveMQReceiver : Someone is looking at a book titled: 'Some Book title'`

#### RabbitMQ
_COMING SOON_
#### Kafka
_COMING SOON_

## Coding Demo Explained
### The client
The GhostWriteService uses the NovelIdeasClient bean to call Novel Ideas Rest interface. 
To do this there were some things that needed to be setup.
* In [application.yml](novel-ghostwriter-rest/src/main/resources/application.yml) add the URL to the running version of Novel Ideas eg 
 ```
novel-ideas:
   url: https://servername.devstuff.org:10443
  ``` 
* Add the `novel-ideas-client-starter` to the POM file. This brings in the API DTOs, the client, the property classes/records, Configurations and the AutoConfiguration.
  * There are two properties records: 
    * One for SSL (for RestTemplate) 
    * One for the URL to Novel Ideas.
  * There are two Configuration classes: 
    * One for creating a RestTemplate. I created two that achieve the same thing using two different libraries 
    * One uses the RestTemple Bean and URL for Novel Ideas to instantiate Novel Ideas Client.

### Message bus
#### ActiveMQ
By default, I have set `message-bus:type: none` which won't load or setup ActiveMQ.
I made it an auto-configurable to be able to be a "consumer" (`ActiveMQReceiver`), this listens for a topic on the message bus,
by adding `activemq` to the profile. This sets `message-bus:type: activemq`. It also has the broker URL and the topic name.

I used a property and configure classes to load and setup a Connection Factory and  Listener Factory.

[novel-ideas](https://github.com/klemmy129/novel-ideas) is the producer, triggered by calling the Rest endpoint: Get `/book/{id}`

The result will display a console message eg `c.k.n.g.listener.ActiveMQReceiver : Someone is looking at a book titled: 'Some Book title'`

#### RabbitMQ
_COMING SOON_
#### Kafka
_COMING SOON_

### Banner

I create a custom banner file, named `banner.txt` in the `src/main/resources` with the yaml files.

Note banner.txt is the default expected banner file name, which Spring Boot uses.
However, if we want to choose any other location or another name for the banner,
we need to set the spring.banner.location property in the `application.yml` file:
```
banner:
  location: classpath:/path/mybanner.txt
```

There are many ASCII Art Generator's out there.

I used (first one I clicked): http://patorjk.com/software/taag/#p=display&f=Big%20Money-ne&t=Type%20Something%20

Others:
- https://devops.datenkollektiv.de/banner.txt/index.html
- http://www.network-science.de/ascii/
- https://textkool.com/en/ascii-art-generator?hl=default&vl=default&font=Mike&text=stacktraceguru

You can also use an image as the banner eg `banner.gif` Search web for more info on this.

## Next

More message bus
