docker build -t front-service:0.0.1-SNAPSHOT -f ./front-service/Dockerfile .
minikube image load front-service:0.0.1-SNAPSHOT

docker build -t account-service:0.0.1-SNAPSHOT -f ./account-service/Dockerfile .
minikube image load account-service:0.0.1-SNAPSHOT

docker build -t blocker-service:0.0.1-SNAPSHOT -f ./blocker-service/Dockerfile .
minikube image load blocker-service:0.0.1-SNAPSHOT

docker build -t exchange-service:0.0.1-SNAPSHOT -f ./exchange-service/Dockerfile .
minikube image load exchange-service:0.0.1-SNAPSHOT

docker build -t notification-service:0.0.1-SNAPSHOT -f ./notification-service/Dockerfile .
minikube image load notification-service:0.0.1-SNAPSHOT

docker build -t transfer-service:0.0.1-SNAPSHOT -f ./transfer-service/Dockerfile .
minikube image load transfer-service:0.0.1-SNAPSHOT

docker build -t cash-service:0.0.1-SNAPSHOT -f ./cash-service/Dockerfile .
minikube image load cash-service:0.0.1-SNAPSHOT

docker build -t exchange-generator-service:0.0.1-SNAPSHOT -f ./exchange-generator-service/Dockerfile .
minikube image load exchange-generator-service:0.0.1-SNAPSHOT