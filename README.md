# unifacef-rabbitmq-parking-lot
Projeto da Pós da Unifacef em desenvolvimento web para a disciplina de Mensageria/Streams

Objetivo é simular erro no envio de mensagens para Queue DLQ (dead letter queue) e após 3 tentativas de envio de mensagem a mesma ser direcionada pra Queue Parking-Lot.

1. Inicie o container do RabbitMQ
```
docker-compose up -d
```

2. Acesse a interface gráfica do RabbitMQ
```
localhost:15672
usr e pass: guest/guest
```

3. Inicie o projeto pelo comando abaixo
```
./mvnw spring-boot:run
```

4. Acompanhe na fila de Queues as mensagens entrando na fila  order-messages-queue.dlq e depois de três tentativas sendo direcionadas para a Queue order-messages-queue.pkl
