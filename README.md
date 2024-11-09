# Masters GTP
Scoring software for Masters field trials

---
## *Needed Tools*
### [*MySql*](https://dev.mysql.com/downloads/mysql/)
**MAKE SURE TO STORE ROOT PASSWORD DURING INSTALL**


### [*Docker*](https://www.docker.com/get-started/)
**Download Docker Desktop for your system**

---
### MySql Debug Info:
MySql should be set to run on startup but if it is not running the database cannot be accessed.

**Windows**: you can hit (windows button + r) or (Start -> Run). Type **services.msc** and click OK. In the list of services find **MySql** or **MySql80** and start (click MySql then the start button) it if it is not running.

**Mac**: open the System Preferences and click on **MySql**. If the status of the service is stopped, click **Start MySql Server**.

### Docker info:

You should not have to do anything with docker other than download it, I've tried to handle all the docker commands in the scripts.
