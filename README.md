# 🧾 API Clientes

Sistema web desenvolvido em **Java** utilizando **Spring Boot** para gerenciamento de **clientes, endereços, fornecedores e produtos**.
A aplicação utiliza **PostgreSQL** como banco de dados e **Bootstrap** para a interface web.

O objetivo do projeto é demonstrar a construção de uma aplicação completa com **backend, persistência de dados e interface web**, permitindo o cadastro e gerenciamento de informações utilizadas em processos de vendas.

![Image](https://github.com/user-attachments/assets/cbf2ea43-f106-42c5-b489-6e42a383a669)

![Image](https://github.com/user-attachments/assets/68ce7163-5326-41f7-b115-b540649993d3)

![Image](https://github.com/user-attachments/assets/65fc23e5-11b3-4457-87b5-bcd48f57a7b1)

![Image](https://github.com/user-attachments/assets/245583b9-e7f0-42a2-9651-635ce0cf52f8)

![Image](https://github.com/user-attachments/assets/26484be9-0c18-4757-82fd-647a463986f9)

---

# 🚀 Funcionalidades

* 👤 Cadastro de **clientes**
* 📍 Cadastro de **endereços**
* 🏢 Cadastro de **fornecedores**
* 📦 Cadastro de **produtos**
* ✏️ Atualização de registros
* ❌ Exclusão de registros
* 📄 Listagem de dados cadastrados
* 🌐 Interface web responsiva

---

# 🛠️ Tecnologias utilizadas

* ☕ Java
* 🚀 Spring Boot
* 🎨 Bootstrap
* 🗄️ PostgreSQL
* 📦 Maven
* 🔗 JPA / Hibernate

---

# 📂 Estrutura do projeto

```text
api-clientes
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── controller
│   │   │   ├── service
│   │   │   ├── repository
│   │   │   └── model
│   │   │
│   │   └── resources
│   │       ├── templates
│   │       ├── static
│   │       └── application.properties
│
├── pom.xml
└── README.md
```

---

# ⚙️ Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

* Java 8 ou superior
* Maven
* PostgreSQL

Verifique as versões:

```bash
java -version
mvn -version
psql --version
```

---

# 🗄️ Configuração do banco de dados

Crie o banco de dados no PostgreSQL:

```sql
CREATE DATABASE api_clientes;
```

Configure as credenciais no arquivo:

```
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_clientes
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# ▶️ Executando o projeto

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/deividandrade/api-clientes.git
cd api-clientes
```

---

### 2️⃣ Executar a aplicação

```bash
mvn spring-boot:run
```

ou execute a classe principal pela IDE.

---

### 3️⃣ Acessar no navegador

Após iniciar o projeto:

```
http://localhost:8080
```

---

# 📊 Funcionalidades do sistema

O sistema permite gerenciar:

### Clientes

* Cadastro
* Atualização
* Exclusão
* Listagem

### Endereços

* Cadastro vinculado a clientes

### Fornecedores

* Gerenciamento de fornecedores de produtos

### Produtos

* Cadastro de produtos disponíveis para venda

---

# 👨‍💻 Autor

Desenvolvido por **Deivid Andrade**
