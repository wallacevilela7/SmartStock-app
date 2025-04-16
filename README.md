# SmartStock App 📦📉

Bem-vindo ao **SmartStock App**, uma aplicação backend desenvolvida em **Java** com **Spring Boot** que automatiza o processo de recompra de produtos em um e-commerce. O sistema lê arquivos CSV com dados de estoque, identifica produtos abaixo do nível mínimo e envia automaticamente pedidos de recompra para o setor de compras via integração com serviços externos usando **OpenFeign**.

---

## **Funcionalidades** 🚀

- ✅ Leitura de arquivos CSV com dados de estoque
- 📉 Identificação automática de itens abaixo do `reorderThreshold`
- 📦 Disparo automático de requisições de recompra para o setor de compras
- 🔐 Autenticação via endpoint com `client_id` e `client_secret`
- 🛠️ Integração com serviços externos usando **OpenFeign**
- 🔄 Execução de todo o fluxo com um único endpoint de gatilho

---

## **Regras de Negócio** 📌

- Cada produto é identificado por um `item_id` único
- A recompra é feita apenas se a quantidade estiver abaixo do `reorderThreshold`
- O processo depende de autenticação bem-sucedida com o setor de compras
- Os dados da requisição seguem um modelo específico e padronizado

---

## **Tecnologias Utilizadas** 🛠️

- **Java 21**
- **Spring Boot 3.4**
- **MongoDB**
- **Docker / Docker Compose**
- **OpenFeign** (para chamadas HTTP externas)
- **OpenCSV** (para leitura de arquivos CSV)
- **Maven**

---

## **Principais Endpoints da API** 🌍

### 🚀 Iniciar o Fluxo de Recompra
Inicia a leitura do CSV e envia os pedidos de recompra automaticamente.
```http
POST /api/start
```

---

### 🔐 Autenticação
Envia as credenciais para obter o token necessário para o setor de compras.
```http
POST /api/token
```
```json
{
  "grant_type": "client_credentials",
  "client_id": "ABC",
  "client_secret": "DEF"
}
```

---

### 📦 Enviar Pedido de Recompra
Envia os dados do produto para o sistema do setor de compras.
```http
POST /api/purchases
```
```json
{
  "item_id": "6e9c24e7-5d4a-4c77-9ed3",
  "item_name": "Samsung 32”",
  "supplier_name": "Samsung",
  "supplier_email": "supplier@samsung.com",
  "quantity": 20
}
```

---

## **Configuração do Banco de Dados** 🗄️

A aplicação utiliza **MongoDB**, facilmente configurado via Docker Compose:

```bash
docker-compose up -d
```

> Banco: `smartstock`  
> Porta padrão: `27017`  
> Usuário/Senha: configuráveis no `application.yml` (ou `.properties`)

---

## **Como Executar o Projeto** ▶️

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/wallacevilela7/SmartStock-app.git
cd SmartStock-app
```

### 2️⃣ Subir o MongoDB

```bash
docker-compose up -d
```

### 3️⃣ Rodar a Aplicação

```bash
./mvnw spring-boot:run
```

API disponível em: [http://localhost:8080](http://localhost:8080)

---

## **Contribuição** 🤝

Contribuições são muito bem-vindas! Se você encontrou um bug, deseja sugerir melhorias ou adicionar novos recursos, sinta-se à vontade para abrir uma **issue** ou enviar um **pull request** 🙌
