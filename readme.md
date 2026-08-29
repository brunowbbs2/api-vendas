Pod => É basicamente o container que ja conhecemos do docker.

produto-service-123A
produto-service-123B
produto-service-123C

Deployment => Responsavel por gerenciar a reciclagem (troca) dos pods

Servico => Nome para acessar o determinado serivico.

<!-- Namespace => É uma pasta lógica dentro de algo. -->

ConfigMap => Local onde guarda as informacoes para as coisas funcionarem.


CLUSTER =>

brew install kubectl kind

kubectl => é o programa que comunica com o kubernets (ele q executa todos os comandos).


kubectl <ação> <tipo de objeto> <nome, se precisar> [flags]

kubectl get pods -n produtos -w