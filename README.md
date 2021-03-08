# ps-access-log

Prevent Senior - Roteiro do desafio técnico<br>
Seu objetivo é criar uma aplicação em Java para fazer o upload de um arquivo de logs populando o banco de dados.<br>
Para isso, será necessário uma interface para o upload do arquivo de logs e uma para inserir/editar/listar/consultar/pesquisar (CRUD).<br>

## Necessário Java 11
- Dentro da pasta target executar - java -jar ps-access-log-0.0.1-SNAPSHOT.jar
- Ou na pata principal mvn spring-boot:run (se o maven suportar java 11 checar mvn-version)
- **Página Inicial** http://localhost:8001/ps-log/api/v1/index
- **Swagger** http://localhost:8001/ps-log/api/v1/swagger-ui.html#/
- **Banco h2** http://localhost:8001/ps-log/api/v1/h2-console <br>
JDBC URL: jdbc:h2:mem:pslog <br>
Username: sa <br>
NO PASSWORD<br>
**INCLUÍDO O JSON DAS CHAMADAS APIS PARA IMPORT NO POSTMAN (Estudo.postman_collection.json) - ps-access-log/upload/main/src/main/resources/accessLog**

![image](https://user-images.githubusercontent.com/10129476/110340922-8edeec80-8008-11eb-9f3c-2bc2687c2367.png)

![image](https://user-images.githubusercontent.com/10129476/110340980-9f8f6280-8008-11eb-816c-e9158bb7d71f.png)

![image](https://user-images.githubusercontent.com/10129476/110341058-b3d35f80-8008-11eb-8c51-4f6b583a240b.png)

![image](https://user-images.githubusercontent.com/10129476/110341204-dbc2c300-8008-11eb-9235-424eb25682da.png)

![image](https://user-images.githubusercontent.com/10129476/110341298-f39a4700-8008-11eb-9e61-75962639c70b.png)

![image](https://user-images.githubusercontent.com/10129476/110341396-0d3b8e80-8009-11eb-8165-1ce01b7fe221.png)

![image](https://user-images.githubusercontent.com/10129476/110341596-4247e100-8009-11eb-8011-08960377d2ec.png)
