
#build docker images
docker build  -t taixingbi/docker-spring-boot-hello .

#open link
http://localhost:8080/

#push in docker hub
docker push taixingbi/docker-spring-boot-hello 

#AWS
sudo yum intall docker -y
sudo service docker start
sudo docker run taixingbi/docker-spring-boot-hello   -p 80:8080




#-----------------------------docker ECS---------------------------

#login aws
aws configure set aws_access_key_id AKIA2UKLDFURSSYI5IWQ
aws configure set aws_secret_access_key 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
aws configure set default.region us-east-2
aws configure set default.output json

aws ecr get-login 

#build docker file
docker build -t seenusdockerrig .


#-----------------------------run java-------------------
 java -jar target/bike-rent.jar
 
#issue
  
https://serverfault.com/questions/893615/how-to-pull-docker-image-on-ecr-using-cloudformation-template




