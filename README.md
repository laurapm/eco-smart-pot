# Introduction

This all started as a project for a subject at university, and now we are here.

## About this project

The objective of this project is to create a smart pot. Fancy words, right? 
But, what is that supposed to mean? 

It means that we take a little device we made on our own, say... something that
collects relevant data out of a plant, and makes some kind of technological 
magic in order to tell you what to do with it.

When the ecø device (that is how we call it) is placed on a pot, next to a 
known plant (it obviously has to be in the system) it monitors the state of 
that plant and tells you when to water it, when to move it away from the sun
(yes, plants are delicate being, some of the 
[like the shades](https://www.housebeautiful.com/lifestyle/gardening/g18665158/shade-loving-plants/)!), 
etc. 

About the magic mentioned before. It is obviously not magic - we are engineers. 
We personally cannot even perform a card trick. What we actually do is to look
for certain patterns in the plant (actually, its parameters: temperature, 
humidity or light it receives). Once a certain pattern is found, a treatment is
suggested to the user. So YOU, can take a better care of your plant.

So... to sum it all up, it is a Tamagotchi with cheats on it.

# Components - Full Stack

Description of the parts of this project and the decisions of which 
language/technology to use for each occasion.

## Server

A remote server is not affordable, it is too expensive. Plus, we are Computer
Scientist and Engineers, we can make it ourselves with an old computer. So, 
almost every component in this project has been deployed on a computer with
an [ArchLinux](https://www.archlinux.org/) on it - which now works as our 
server. But, 
[why ArchLinux](https://www.fossmint.com/reasons-to-use-arch-linux/) you may 
ask.

Well, customization would be the main reason for it. You know what you have 
inside. Because you installed it. If you don't, then you don't have it. There
is not more to say about it.

For more information on what it has inside, refer this
[documentation](https://github.com/laurapm/UBICUA/tree/master/server).

## Database

As a container for all the information, the winner was 
[MongoDB](https://www.mongodb.com/). At the beginning, the database of choice 
was [MySQL](https://www.mysql.com/). 

But [MySQL](https://github.com/mysql) did not provide our IoT project with all 
the desired perks. That is why the project pivoted and started using 
[MongoDB](https://github.com/mongodb). 

Both technologies are Open-Source, but Mongo seems like a better fit for this
work in progress.

For more information, look at this 
[documentation](https://github.com/laurapm/UBICUA/tree/master/database) 
we have prepared for you.

## Device

At the first moment, the team decided to use an ESP32 for the project due to it is a low-power system which integrated WiFi and dual-mode Bluetooth. Especially useful tools for the idea we wanted to develop in this project.

But for some technical problems, we had to leave the idea (the ESP are not useful anymore owning to short-circuit in the COM port). So we have to pivot and remake code to use an Arduino UNO as it was the handy device.
 
For further information, check out this [documentation](https://github.com/laurapm/UBICUA/tree/master/device).
## Front-end

As it has been mentioned before, MongoDB was a decision made out of what was
best for the project. But, this is not the case of the front-end part of the
application.

Only one of the developers of this project new how to use 
[Angular](https://angular.io/). It is also 
[open source](https://github.com/angular), as it should be. But please, do not
mistake it for [AngularJS](https://www.ebuilderz.com/angular-vs-angularjs/)

As that developer was the rest of the team's mentor, if you see anything you
don't like, we will make sure you complains reach her.

For more information go to the 
[front-end explanation](https://github.com/laurapm/UBICUA/tree/master/webpage/eco-front).

## Back-end

Remember what we said about knowing something and that is why we use it. Well,
once again, [Java](https://www.java.com/en/) is here for that reason.

We know, Java and we mentioned something about open-source before. Java being
[open't-source](https://www.itassetmanagement.net/2018/05/01/oracle-to-charge-for-java-from-jan-2019/).
But rest at ease, we used [this](https://openjdk.java.net/) instead. The 
OpenJDK is open-source, so now we are all on the same page with this, right?

So summing up, all the actual reasons we had to use Java is that we did not 
have time to change the language and it was the only option where all of the 
developers found themselves comfortable in (remember, it started as a 
university project).

To know more about its implementation go to the
[back-end documentation](https://github.com/laurapm/UBICUA/tree/master/webpage/eco-webpage).

## Other Tools

### Postman

[Postman](https://www.postman.com/) is a tool that allows to test API tools 
([download here](https://www.postman.com/downloads/)) by generating request to 
the given API. It allows making the following request:

- `GET`
- `POST`
- `PUT`
- `PATCH`
- `DELETE`
- `COPY`
- `HEAD`
- `OPTIONS`
- `LINK`
- `UNLINK`
- `PURGE`
- `LOCK`
- `UNLOCK`
- `PROPFIND`
- `VIEW`

Despite the many types of requests it allows to send and perform, only four of
them are used in this project: `GET`, `POST`, `PUT` and `DELETE`.

It has been used to test that the REST API for the `eco` MongoDB works as it 
should. The file with the tests used by the developers can be found by 
importing the file `src/main/resources/rainforest-eco-api.postman_testing.json`
into Postman.

Beware that almost any of the test is static. An adjustment of some of the 
bodies (`body` option), or some `id`s might need to be changed in order for the
test to work, as they are data dependent.

Other options can be used, although it is not recommended to do so. One of the 
previous options considered was the webpage 
[postwoman.io/](https://hoppscotch.io/). It was discarded, as it resulted quite 
troublesome during the development and testing of the API.

### Github

As you can guess (you are here, are you not?), as a version controlling tool, 
[Github](https://github.com/) was our decision. It is quite intuitive on why 
the project is kept and controlled here. But in case it was not clear enough,
the developers of this project stand up for the open-source community. 

So this might actually be useful for somebody (we said somebody, if you are a
company trying to get money from our work, this is not your repo, we kindly ask
you to leave).

Another point we would like to give a remark about, is 
[Github Projects](https://github.com/features/project-management). This tool 
allowed us to follow all the tasks that needed to be completed. It came in 
handy to evenly distribute all the development across all of us.

![](https://github.com/laurapm/UBICUA/tree/master/docs/images/git_projects.png)

_This is a screenshot at some moment of this project's development_.
