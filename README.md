

# -----------------------------run java in ubuntu-------------------
mvn clean install     
java -jar target/bike-rent.jar       


# -----------------------------docker ECS---------------------------

#login aws
aws configure set aws_access_key_id AKIA2UKLDFURSSYI5IWQ     
aws configure set aws_secret_access_key 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      
aws configure set default.region us-east-2  
aws configure set default.output json  

aws ecr get-login     

# build docker file
docker build -t seenusdockerrig .   



 
# issue
  
https://serverfault.com/questions/893615/how-to-pull-docker-image-on-ecr-using-cloudformation-template

# --------------------------docker--------------------------
## build docker images  
docker build  -t taixingbi/docker-spring-boot-hello .

## open link
http://localhost:8085/  

## push in docker hub  
docker push taixingbi/docker-spring-boot-hello 

## AWS
sudo yum intall docker -y  
sudo service docker start
sudo docker run taixingbi/docker-spring-boot-hello   -p 80:8080


## ------------------issue-------------
# port already used     
sudo lsof -i tcp:8085  
kill -9 PID




